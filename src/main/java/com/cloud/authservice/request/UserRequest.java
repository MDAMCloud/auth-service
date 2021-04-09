package com.cloud.authservice.request;

import com.cloud.authservice.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String accountType;

    public User toUser(){
        return new User(username, password, email, accountType);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
