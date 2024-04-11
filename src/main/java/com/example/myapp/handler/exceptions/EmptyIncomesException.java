package com.example.myapp.handler.exceptions;

public class EmptyIncomesException extends Throwable{
    public EmptyIncomesException(String message) {
        super("There are no incomes in database");
    }
}
