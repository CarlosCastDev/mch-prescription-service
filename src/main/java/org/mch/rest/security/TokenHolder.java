package org.mch.rest.security;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TokenHolder {
    private String token;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
