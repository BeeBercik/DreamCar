package com.dreamcar.exceptions;

/**
 * Exception throws when there is an issue with offer data e.g. user provided wrong data during adding new offer
 * or editing already existing one.
 */
public class IncorrectOfferDataException extends RuntimeException {
    /**
     * Constructor to create exception with provided message
     *
     * @param message explaining the cause of the exception
     */
    public IncorrectOfferDataException(String message) {
        super(message);
    }
}
