package com.dreamcar.exceptions;

/**
 * Exception throws when there is an issue with registering user such as user provided wrong data format etc.
 */
public class IncorrectRegisterDataException extends RuntimeException {
    /**
     * Constructor to create exception with provided message
     *
     * @param message explaining the cause of the exception
     */
    public IncorrectRegisterDataException(String message) {
        super(message);
    }
}
