package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.util.List;

public class LoginResponse {
    private String email;
    private String accessToken; // Sesuaikan nama field dengan JSON response
    private List<String> roles; // Jika kamu memerlukan informasi roles

    // Getter dan setter untuk semua field
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
