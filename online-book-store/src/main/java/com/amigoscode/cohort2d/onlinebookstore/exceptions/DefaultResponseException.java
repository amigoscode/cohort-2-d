package com.amigoscode.cohort2d.onlinebookstore.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class DefaultResponseException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new String[]{e.getMessage()},
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleException(DuplicateResourceException e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new String[]{e.getMessage()},
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ApiError> handleException(RequestValidationException e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new String[]{e.getMessage()},
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiError> handleException(TransactionSystemException e, HttpServletRequest request) {

        if (e.getRootCause() instanceof ConstraintViolationException constraintViolationException) {

            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            List<String> errors = new ArrayList<>();
            constraintViolations.forEach(c -> {
                String fieldName = c.getPropertyPath().toString();
                String message = c.getMessage();
                errors.add(fieldName + ": " + message);
            });
            ApiError apiValidationError = new ApiError(
                    request.getRequestURI(),
                    errors.stream().toList().toArray(new String[0]),
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(apiValidationError, HttpStatus.BAD_REQUEST);
        }

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new String[]{e.getMessage()},
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e, HttpServletRequest request){

        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(fieldName + ": " + message);
        });

        ApiError apiError = new ApiError(
                request.getMethod()+": "+request.getRequestURI(),
                errors.stream().toList().toArray(new String[0]),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request){

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                new String[]{e.getMessage()},
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
