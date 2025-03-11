package com.example.projet.controller;

import com.example.projet.model.NotDataBase.ModelToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody String userJson, HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {
        String token = AdminToken(request);
        System.out.println(token);

        String url = "http://localhost:8080/admin/realms/security-reaml/users";
        logger.info("Received request to create user: " + userJson);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> request1 = new HttpEntity<>(userJson, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request1, String.class);
            logger.info("User created successfully: " + response.getBody());
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch (HttpClientErrorException e) {
            logger.error("Error creating user: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            logger.error("Unexpected error creating user", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final String CLIENT_ID = "security-client";
    private static final String USERNAME = "Zosefa";
    private static final String PASSWORD = "123456";
    private static final String GRANT_TYPE = "password";
    private static final String URL_STRING = "http://localhost:8080/realms/security-realm/protocol/openid-connect/token";

    private String AdminToken(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "client_id=" + CLIENT_ID + "&username=" + USERNAME + "&password=" + PASSWORD
                + "&grant_type=" + GRANT_TYPE;

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_STRING, HttpMethod.POST, requestEntity,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            ObjectMapper objectMapper = new ObjectMapper();
            ModelToken modeltokken = objectMapper.readValue(responseEntity.getBody(), ModelToken.class);

            return modeltokken.getAccessToken();
        } else {
            return "Error: " + responseEntity.getStatusCode();
        }
    }

    @GetMapping("/getUserId")
    public ResponseEntity<String> getUserId(@RequestParam String username, HttpServletRequest request) {
        try {
            String token = AdminToken(request);
            return fetchUserId(username, token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    private ResponseEntity<String> fetchUserId(String username, String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String userInfoUrl = "http://localhost:8080/admin/realms/security-realm/users?username=" + username;

        ResponseEntity<String> responseEntity = restTemplate.exchange(userInfoUrl, HttpMethod.GET, requestEntity,
                String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode())
                    .body("Error: " + responseEntity.getStatusCode());
        }
    }

    @GetMapping("/getGroupId")
    public ResponseEntity<String> getGroupId(@RequestParam String groupName, HttpServletRequest request) {
        try {
            String token = AdminToken(request);
            return fetchGroupId(groupName, token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    private ResponseEntity<String> fetchGroupId(String groupName, String token) {
        return fetchData("http://localhost:8080/admin/realms/security-realm/groups?groupName=" + groupName,
                token);
    }

    private ResponseEntity<String> fetchData(String url, String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode())
                    .body("Error: " + responseEntity.getStatusCode());
        }
    }

    private static final String keycloakUrl = "http://localhost:8080/auth/admin/realms/security-realm/users/{userId}/groups/{groupId}";

    @PutMapping("/addUsersToGroup")
    public ResponseEntity<String> addUsersToGroup(@RequestParam String groupId, @RequestParam String[] userIds,
                                                  HttpServletRequest request) {
        try {
            String token = AdminToken(request);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            for (String userId : userIds) {
                String url = UriComponentsBuilder.fromHttpUrl(keycloakUrl)
                        .pathSegment("auth", "admin", "realms", "Transactions", "users", userId, "groups", groupId)
                        .toUriString();

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Utilisateur ajouté au groupe avec succès !");
                } else {
                    System.err.println("Erreur lors de l'ajout de l'utilisateur au groupe: " + response.getBody());
                }
            }

            return ResponseEntity.ok("Utilisateurs ajoutés au groupe avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/addGroupToUser")
    public ResponseEntity<String> addGroupToUser(@RequestParam String userID, @RequestParam String groupID,
                                                 HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        String token = AdminToken(request);
        System.out.println(token);

        String url = "http://localhost:8080/admin/realms/security-realm/users/" + userID + "/groups/" + groupID;
        logger.info("Received request to add group to user: userID=" + userID + ", groupID=" + groupID);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);

        // Pas de corps de requête nécessaire pour un PUT

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            logger.info("Group added to user successfully: " + response.getBody());
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        } catch (HttpClientErrorException e) {
            logger.error("Error adding group to user: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        } catch (Exception e) {
            logger.error("Unexpected error adding group to user", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
