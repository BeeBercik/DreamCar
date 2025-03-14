package com.dreamcar.exceptions.handlers;

import com.dreamcar.exceptions.IncorrectLoginDataException;
import com.dreamcar.exceptions.IncorrectOfferDataException;
import com.dreamcar.exceptions.IncorrectRegisterDataException;
import com.dreamcar.exceptions.UserNotLoggedInException;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

public interface IGlobalExceptionHandler {
    ResponseEntity<?> handleIncorrectRegisterDataException(IncorrectRegisterDataException e);
    ResponseEntity<?> handleIncorrectLoginDataException(IncorrectLoginDataException e);
    ResponseEntity<?> handleUserNotLoggedInException(UserNotLoggedInException e);
    ResponseEntity<?> handleIncorrectOfferDataException(IncorrectOfferDataException e);
    ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e);
}
