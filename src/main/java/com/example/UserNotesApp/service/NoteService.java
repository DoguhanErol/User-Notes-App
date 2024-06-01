package com.example.UserNotesApp.service;

import com.example.UserNotesApp.dto.NoteDto;
import com.example.UserNotesApp.exceptions.BadRequestException;
import com.example.UserNotesApp.exceptions.SystemException;
import com.example.UserNotesApp.model.Note;
import com.example.UserNotesApp.model.User;
import com.example.UserNotesApp.repository.NoteRepository;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    private final ModelMapper modelMapper;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, ModelMapper modelMapper,UserService userService){
        this.userService = userService;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    //Get all notes | User, Admin, Unknown
    public List<NoteDto> getAllNotes(){
        try {
            List<Note> notes = noteRepository.findAll();
            List<NoteDto> allNoteDtos = notes.stream()
                    .map(note -> modelMapper.map(note, NoteDto.class))
                    .collect(Collectors.toList());
            return allNoteDtos;
        }catch (Exception ex){
            throw new SystemException("System error occurred while fetching all notes!");
        }


    }
    //Get notes by user id | User, Admin
    public List<NoteDto> getNotesById(Long id){
        if (!userService.isUserExist(id)){
            throw new BadRequestException("There is no user using this id:" + id);
        }
        try{
            List<Note> userNotes = noteRepository.findAllByUserId(id);
            List<NoteDto> userNotesDtos = userNotes.stream()
                    .map(note -> modelMapper.map(note, NoteDto.class))
                    .collect(Collectors.toList());
            return userNotesDtos;
        }catch (Exception ex){
            throw new SystemException("An unexpected error occurred while fetching notes by user id!");
        }


    }
    //Create a note | User
    public NoteDto postNote(NoteDto noteDto){
        if (!userService.isUserExist(noteDto.getUserId())){
            throw new BadRequestException("There is no user using this userId:" + noteDto.getUserId());
        }
            try{
            Note note = modelMapper.map(noteDto, Note.class);
            Note savedNote = noteRepository.save(note);
            return modelMapper.map(savedNote, NoteDto.class);
        }catch (Exception ex){
            throw new SystemException("An unexpected error occurred while creating note!");
        }
    }
    //Update the note | User
    public NoteDto updateNote(Long id,NoteDto updatedNoteDto){
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (!optionalNote.isPresent()){
            throw new BadRequestException("There is no user with that id:" + id);
        }
        if (updatedNoteDto.getTitle().isEmpty() || updatedNoteDto.getContent().isEmpty() || updatedNoteDto.getTitle().isBlank()){
            throw new BadRequestException("New note can`t be empty!");
        }
        try{
            Note existingNote = optionalNote.get();
            existingNote.setTitle(updatedNoteDto.getTitle());
            existingNote.setContent(updatedNoteDto.getContent());
            Note updatedNote = noteRepository.save(existingNote);
            return modelMapper.map(updatedNote, NoteDto.class);
        }catch (Exception ex){
            throw new SystemException("An unexpected error occurred while updating note!");
        }
    }

    //Delete the note | User, Admin
    public Boolean deleteNote(Long id){
        if (!noteRepository.existsById(id)){
            throw new BadRequestException("There is no note using this id:" + id);
        }
        try {
            noteRepository.deleteById(id);
        }catch (Exception ex){
            throw new SystemException("An unexpected error occurred while updating note!");
        }
        return true;
    }


}
