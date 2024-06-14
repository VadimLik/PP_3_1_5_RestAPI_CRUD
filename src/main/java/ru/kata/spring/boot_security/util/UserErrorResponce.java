package ru.kata.spring.boot_security.util;

public class UserErrorResponce {

    private String message;

    public UserErrorResponce(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
