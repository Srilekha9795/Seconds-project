package com.insurance.datacollection.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, String>> buildErrorResponse(String message) {
        Map<String, String> mp = new HashMap<>();
        mp.put("Error Message", message);
        return new ResponseEntity<>(mp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppIdErrorException.class)
    public ResponseEntity<Map<String, String>> handleAppIdError(AppIdErrorException ex) {
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NoPlanFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoPlanFound(NoPlanFoundException ex) {
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(CaseNumNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCaseNumNotFound(CaseNumNotFoundException ex) {
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(DuplicateCaseNumException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateCaseNum(DuplicateCaseNumException ex) {
        return buildErrorResponse(ex.getMessage());
    }
}
