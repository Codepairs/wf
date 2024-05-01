package com.example.myapp.handler;

import com.example.myapp.handler.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice()
public class Advice {
    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<Response> handleException(NotFoundByIdException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCategoriesException.class)
    public ResponseEntity<Response> handleEmptyCategoriesException(EmptyCategoriesException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyExpenseException.class)
    public ResponseEntity<Response> handleEmptyExpenseException(EmptyExpenseException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyIncomesException.class)
    public ResponseEntity<Response> handleEmptyIncomeException(EmptyIncomesException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyUsersException.class)
    public ResponseEntity<Response> handleEmptyUsersException(EmptyUsersException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "Field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
                .toList();
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errors, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLUniqueException.class)
    public ResponseEntity<Response> handleException(SQLUniqueException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleException(HttpMessageNotReadableException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleException(MethodArgumentTypeMismatchException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleException(ConstraintViolationException e) {
        LocalDateTime time = LocalDateTime.now();
        List<String> errorMessage = List.of(e.getMessage());
        String exceptionName = e.getClass().getSimpleName();
        Response response = new Response(exceptionName, errorMessage, time);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
