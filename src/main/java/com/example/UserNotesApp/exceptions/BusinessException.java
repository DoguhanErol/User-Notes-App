package com.example.UserNotesApp.exceptions;


// İş mantığı hataları için
//For business logic exceptions
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
