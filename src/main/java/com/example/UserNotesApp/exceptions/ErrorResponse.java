package com.example.UserNotesApp.exceptions;

import java.util.List;


//Entity
public class ErrorResponse {
    private String message;
    private List<String> details;

    public ErrorResponse(String message,List<String> details){
        this.message = message;
        this.details = details;
    }
    //Getters
    public String getMessage(){
        return message;
    }
    public List<String> getDetails(){
        return details;
    }
    //Setters
    public void setMessage(String message){
        this.message = message;
    }

    public void setDetails(List<String> details){
        this.details = details;
    }

}
