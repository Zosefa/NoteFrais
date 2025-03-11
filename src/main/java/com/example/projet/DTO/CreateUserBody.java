package com.example.projet.DTO;

import java.util.List;
import java.util.Map;

public class CreateUserBody {
    private String username;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private List<Map<String, String>> credentials;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Map<String, String>> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<Map<String, String>> credentials) {
        this.credentials = credentials;
    }
}
