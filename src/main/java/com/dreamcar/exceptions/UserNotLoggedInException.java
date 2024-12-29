package com.dreamcar.exceptions;

/**
 * Exception throws when user tried to access resource without permission
 */
public class UserNotLoggedInException extends RuntimeException {
    /**
     * Constructor to create exception with provided message
     *
     * @param message explaining the cause of the exception
     */
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
