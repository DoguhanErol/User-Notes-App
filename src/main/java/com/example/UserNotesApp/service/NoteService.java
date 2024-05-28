package com.example.UserNotesApp.service;

import com.example.UserNotesApp.dto.NoteDto;
import com.example.UserNotesApp.model.Note;
import com.example.UserNotesApp.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    private final ModelMapper modelMapper;

    public NoteService(NoteRepository noteRepository, ModelMapper modelMapper){
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    //Get all notes | User, Admin, Unknown
    public List<NoteDto> getAllNotes(){
        List<Note> notes = noteRepository.findAll();
        List<NoteDto> allNoteDtos = notes.stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
        return allNoteDtos;
    }
    //Get notes by user id | User, Admin
    public List<NoteDto> getNotesById(UUID id){
        List<Note> userNotes = noteRepository.findAllByUserId(id);
        List<NoteDto> userNotesDtos = userNotes.stream()
                .map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
        return userNotesDtos;
    }
    //Create a note | User
    public NoteDto postNote(NoteDto noteDto){
          Note note = modelMapper.map(noteDto, Note.class);
          Note savedNote = noteRepository.save(note);
          return modelMapper.map(savedNote, NoteDto.class);
    }
    //Update the note | User
    public NoteDto updateNote(Long id,NoteDto updatedNoteDto){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()){
            Note existingNote = optionalNote.get();
            existingNote.setTitle(updatedNoteDto.getTitle());
            existingNote.setContent(updatedNoteDto.getContent());
            Note updatedNote = noteRepository.save(existingNote);
            return modelMapper.map(updatedNote, NoteDto.class);
        }else {
            throw new ObjectNotFoundException(optionalNote,"Note not found!!!");
        }
    }

    //Delete the note | User, Admin
    public Boolean deleteNote(Long id){
        try {
            noteRepository.deleteById(id);
        }catch (Exception ex){
            return false;
        }
        return true;
    }


}
