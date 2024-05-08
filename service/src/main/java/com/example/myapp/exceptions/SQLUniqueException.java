package com.example.myapp.exceptions;

public class SQLUniqueException extends Throwable {
    public SQLUniqueException(String message) {
        super(message);
    }
}
