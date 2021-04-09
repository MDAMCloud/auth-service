package com.cloud.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
public class User extends BaseEntity {

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private String accountType;

    public User() {
    }

    public User(String username, String password, String email, String accountType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
    }

    public User mergeUser(User userToMerge) {
        userToMerge.setUsername(Optional.ofNullable(userToMerge.getUsername()).orElse(this.username));
        userToMerge.setPassword(Optional.ofNullable(userToMerge.getPassword()).orElse(this.password));
        userToMerge.setEmail(Optional.ofNullable(userToMerge.getEmail()).orElse(this.email));
        userToMerge.setAccountType(Optional.ofNullable(userToMerge.getAccountType()).orElse(this.accountType));
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", accountType='" + accountType + '\'' +
               '}';
    }
}
