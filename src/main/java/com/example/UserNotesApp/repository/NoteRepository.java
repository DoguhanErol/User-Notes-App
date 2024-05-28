package com.example.UserNotesApp.repository;

import com.example.UserNotesApp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findAllByUserId(UUID userId);
}
