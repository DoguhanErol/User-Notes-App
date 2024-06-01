package com.example.UserNotesApp.controller;

import com.example.UserNotesApp.dto.NoteDto;
import com.example.UserNotesApp.model.Note;
import com.example.UserNotesApp.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    public final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    //Get all notes
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes(){
        return ResponseEntity.ok(noteService.getAllNotes());
    }
    @GetMapping("/{id}")
    //Get notes by user id
    public ResponseEntity<List<NoteDto>> getNotesByUserId(@PathVariable Long id){
        return ResponseEntity.ok(noteService.getNotesById(id));
    }

    //Create a note
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto){
        return ResponseEntity.ok(noteService.postNote(noteDto));
    }
    //Update the note
    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long
 id,@RequestBody NoteDto noteDto){
        NoteDto updatedNoteDto = noteService.updateNote(id,noteDto);
        if (updatedNoteDto != null){
            return ResponseEntity.ok(updatedNoteDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Delete the note
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable Long id){
        return ResponseEntity.ok(noteService.deleteNote(id));
    }



}
