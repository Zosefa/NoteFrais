package com.example.projet.DTO;

import javax.validation.constraints.NotEmpty;

public class ProfilDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirm;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}

