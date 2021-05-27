package com.perdijimen.bethabank.config;

public class AuthenticationResponse {

    private String jwt;

    private String name;

    private String lastName;

    private Long id;

    public AuthenticationResponse(String jwt, String name, String lastName, Long id) {
        this.jwt = jwt;
        this.name = name;
        this.lastName = lastName;
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
