package com.example.myapp.handler.exceptions;

public class EmptyUsersException extends Throwable{
    public EmptyUsersException(String message) {
        super("There are no users in database");
    }
}
