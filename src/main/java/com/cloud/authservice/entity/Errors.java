package com.cloud.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
public class Errors extends BaseEntity{
    @Id
    @Field("_id")
    private String id;

    private String userId;

    private String username;

    private String errorMessage;

    public static Errors newError(String userId, String username, String errorMessage) {
        Errors error = new Errors();
        error.setUserId(userId);
        error.setUsername(username);
        error.setErrorMessage(errorMessage);
        return error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Errors{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
