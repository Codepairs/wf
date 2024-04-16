package com.example.myapp.exceptions;

public class EmptyExpenseException extends Throwable{

    public EmptyExpenseException(String message) {
        super(message);
    }
}
