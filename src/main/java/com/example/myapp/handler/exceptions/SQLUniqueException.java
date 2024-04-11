package com.example.myapp.handler.exceptions;

public class SQLUniqueException extends Throwable {
    public SQLUniqueException(String message) {
        super(message);
    }
}
