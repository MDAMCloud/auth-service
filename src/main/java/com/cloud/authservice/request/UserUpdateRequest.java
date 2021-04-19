package com.cloud.authservice.request;

import com.cloud.authservice.entity.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserUpdateRequest extends UserRequest{

    @NotNull
    private String id;

    private String username;

    private String password;

    private String email;

    private String accountType;

    public User toUser(){
        User user = super.toUser();
        user.setId(this.id);
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{" +
                "id=" + id +
                '}';
    }
}
