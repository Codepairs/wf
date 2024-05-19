package com.example.myapp.handler.exceptions;

public class NoUserFoundException extends Throwable{

    public NoUserFoundException(String message) {
        super(message);
    }
}
