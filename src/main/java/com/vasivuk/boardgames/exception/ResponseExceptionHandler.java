package com.vasivuk.boardgames.exception;

import com.vasivuk.boardgames.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseStatus
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, "Validation error", details.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, "Entity not found", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> entityAlreadyExistsException(EntityAlreadyExistsException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, "Entity already exists", exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(ForbiddenResourceException.class)
    public ResponseEntity<ErrorMessage> forbiddenResourceException(ForbiddenResourceException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "Forbidden resource", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorMessage> invalidOrderException(InvalidOrderException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "Invalid order: ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(ImageStorageException.class)
    public ResponseEntity<ErrorMessage> imageStorageException(ImageStorageException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "Invalid image: ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<ErrorMessage> myFileNotFoundException(MyFileNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN, "File not found: ", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
}
