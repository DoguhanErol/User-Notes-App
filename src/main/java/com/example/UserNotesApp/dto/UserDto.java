package com.example.UserNotesApp.dto;

import java.util.Date;
import java.util.List;

public class UserDto {

    private Long id;
    private String userName;
    private String password;
    private List<NoteDto> notes;





    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDto> notes) {
        this.notes = notes;
    }

}
