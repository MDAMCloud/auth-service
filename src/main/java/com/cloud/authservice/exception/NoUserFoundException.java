package com.cloud.authservice.exception;

public class NoUserFoundException extends Exception {
    private String message = "Could not find a user with the given information.";

    @Override
    public String getMessage() {
        return message;
    }
}
