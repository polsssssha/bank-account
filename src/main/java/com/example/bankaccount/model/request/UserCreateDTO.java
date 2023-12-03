package com.example.bankaccount.model.request;

import jakarta.validation.constraints.NotNull;

public class UserCreateDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
