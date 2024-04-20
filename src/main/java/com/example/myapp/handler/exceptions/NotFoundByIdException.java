package com.example.myapp.handler.exceptions;

public class NotFoundByIdException extends Throwable {

    public NotFoundByIdException(String message) {
        super(message);
    }
}
