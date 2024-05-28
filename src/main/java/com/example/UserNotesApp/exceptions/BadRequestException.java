package com.example.UserNotesApp.exceptions;


// Kullanıcı hataları için
//For user exceptions
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
