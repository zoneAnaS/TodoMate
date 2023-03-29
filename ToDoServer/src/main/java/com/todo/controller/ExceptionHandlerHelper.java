package com.todo.controller;



import com.todo.exception.ErrorDetails;
import com.todo.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerHelper {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> handler(UserException ex){
        ErrorDetails err=new ErrorDetails();
        err.setMessage(ex.getMessage());
        err.setDescription(ex.getStackTrace().toString());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> handler(NoHandlerFoundException ex){
        ErrorDetails err=new ErrorDetails();
        err.setMessage(ex.getMessage());
        err.setDescription(ex.getStackTrace().toString());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handler(IllegalArgumentException ex){
        ErrorDetails err=new ErrorDetails();
        err.setMessage(ex.getMessage());
        err.setDescription(ex.getStackTrace().toString());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handler(Exception ex){
        ErrorDetails err=new ErrorDetails();
        err.setMessage(ex.getMessage());
        err.setDescription(ex.getStackTrace().toString());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }

}
