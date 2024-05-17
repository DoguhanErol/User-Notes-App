package com.example.UserNotesApp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id; //@Id ve @GeneratedValue ile işaretlenmiş, bu  nesnenin veritabanında otomatik olarak oluşturulmasını ve bir kimlik  değeri atanmasını sağlar.

    @Column(length = 40)
    private String userName;

    @Column(length = 40)
    private String password;


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Note> notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    //Getters
    public UUID getId(){
        return id;
    }
    public String  getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    //Setters
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
