package com.tasks.Tasks.exception;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(String message){
        super(message);
    }
}
