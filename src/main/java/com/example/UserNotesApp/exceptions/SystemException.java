package com.example.UserNotesApp.exceptions;

// Sistem hataları için
//For system exceptions
public class SystemException extends RuntimeException{
    public SystemException(String message){
        super(message);
    }
}
