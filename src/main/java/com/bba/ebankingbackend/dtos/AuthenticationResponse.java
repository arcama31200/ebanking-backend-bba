package com.bba.ebankingbackend.dtos;

public class AuthenticationResponse {

    private final String jwt;

    // Constructeur
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getter
    public String getJwt() {
        return jwt;
    }
}
