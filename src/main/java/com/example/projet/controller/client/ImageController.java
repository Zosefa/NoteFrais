package com.example.projet.controller.client;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ImageController {
    private final ResourceLoader resourceLoader;

    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/Uploads/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Resource resource = resourceLoader.getResource("file:Uploads/" + fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(getContentType(resource))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private MediaType getContentType(Resource resource) {
        String fileName = resource.getFilename();
        if (fileName != null) {
            if (fileName.endsWith(".png")) {
                return MediaType.IMAGE_PNG;
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                return MediaType.IMAGE_JPEG;
            } else if (fileName.endsWith(".gif")) {
                return MediaType.IMAGE_GIF;
            } else if (fileName.endsWith(".mp4")) {
                return MediaType.parseMediaType("video/mp4");
            } else if (fileName.endsWith(".pdf")) {
                return MediaType.APPLICATION_PDF;
            } else if (fileName.endsWith(".txt")) {
                return MediaType.TEXT_PLAIN;
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
