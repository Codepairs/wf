package com.example.myapp.handler.exceptions;

public class EmptyExpenseException extends Throwable {

    public EmptyExpenseException(String message) {
        super("There are no expenses in database");
    }
}
