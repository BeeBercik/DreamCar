package com.dreamcar.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
