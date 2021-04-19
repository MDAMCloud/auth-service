package com.cloud.authservice.entity;

import java.util.Objects;

public class Token {

  String token;

  String username;

  String accountType;

  public Token() {
  }

  public Token(String token, String username, String accountType) {
    this.token = token;
    this.username = username;
    this.accountType = accountType;
  }

  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "Token{" +
           "token='" + token + '\'' +
           ", username='" + username + '\'' +
           ", accountType='" + accountType + '\'' +
           '}';
  }
}
