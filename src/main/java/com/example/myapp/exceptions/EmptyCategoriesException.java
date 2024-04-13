package com.example.myapp.exceptions;

public class EmptyCategoriesException extends Throwable{
    public EmptyCategoriesException(){
        super("There are no categories in database");
    }
}
