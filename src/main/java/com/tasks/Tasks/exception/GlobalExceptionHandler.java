package com.tasks.Tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObj> handleUserNotFoundException(UserNotFoundException ex){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(ex.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }

    @ExceptionHandler(TasksFoundException.class)
    public ResponseEntity<ErrorObj> handleTaskFoundException(TasksFoundException ex){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(ex.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorObj> handleTaskNotFoundException(TaskNotFoundException exception){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(exception.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ErrorObj> handleUserExist(UserFoundException exception){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(exception.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorObj> handlePassword(PasswordNotMatchException ex){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(ex.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorObj> handleInvalidPasswordException(InvalidPasswordException ex){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(ex.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorObj> handleInvalid(InvalidException ex){
        ErrorObj errorObj = new ErrorObj();
        errorObj.setMessage(ex.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(new Date());
        errorObj.setTimeDate(formattedDate);

        return ResponseEntity.ok(errorObj);
    }
}

