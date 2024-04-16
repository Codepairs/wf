package com.example.myapp.exceptions;

public class NotFoundByIdException extends Throwable{

    public NotFoundByIdException(String message) {
        super(message);
    }
}
