package com.dreamcar.exceptions;

public class IncorrectLoginDataException extends RuntimeException {
    public IncorrectLoginDataException(String message) {
        super(message);
    }
}
