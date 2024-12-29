package com.dreamcar.exceptions;

/**
 * Exception throws when there is an issue with login data e.g. user provided wrong login data.
 */
public class IncorrectLoginDataException extends RuntimeException {
    /**
     * Constructor to create exception with provided message
     *
     * @param message explaining the cause of the exception
     */
    public IncorrectLoginDataException(String message) {
        super(message);
    }
}
