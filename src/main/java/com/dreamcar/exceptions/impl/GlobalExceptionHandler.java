package com.dreamcar.exceptions.impl;

import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectRegisterDataException.class)
    public ResponseEntity<?> handleIncorrectRegisterDataException(IncorrectRegisterDataException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IncorrectLoginDataException.class)
    public ResponseEntity<?> handleIncorrectLoginDataException(IncorrectLoginDataException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<?> handleUserNotLoggedInException(UserNotLoggedInException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(IncorrectOfferDataException.class)
    public ResponseEntity<?> handleIncorrectOfferDataException(IncorrectOfferDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
