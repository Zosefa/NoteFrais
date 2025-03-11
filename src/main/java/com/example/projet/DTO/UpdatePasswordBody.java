package com.example.projet.DTO;

public class UpdatePasswordBody {
    private String type = "password";
    private String value;
    private boolean temporary = false;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
