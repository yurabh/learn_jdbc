package com.domain;

import java.util.Objects;

public class User {

    private String login;

    private String password;

    private String email;

    private UserState status;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public UserState getStatus() {
        return status;
    }

    public void setStatus(UserState status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
