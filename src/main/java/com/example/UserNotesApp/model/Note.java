package com.example.UserNotesApp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;


@Entity
public class Note {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id; //@Id ve @GeneratedValue ile işaretlenmiş, bu  nesnenin veritabanında otomatik olarak oluşturulmasını ve bir kimlik  değeri atanmasını sağlar.
    @Column(length = 70)
    private String title;

    @Column(length = 300)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt;

    //Getters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    //Setters

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
