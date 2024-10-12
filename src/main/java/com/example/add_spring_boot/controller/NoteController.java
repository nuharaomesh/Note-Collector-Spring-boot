package com.example.add_spring_boot.controller;

import com.example.add_spring_boot.customStatusCode.SelectedNoteErrorCode;
import com.example.add_spring_boot.dto.NoteStatus;
import com.example.add_spring_boot.dto.impl.NoteDTO;
import com.example.add_spring_boot.exception.DataPersistException;
import com.example.add_spring_boot.exception.UserNotFoundException;
import com.example.add_spring_boot.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping(value = "/{noteID}")
    public NoteStatus getNote(@PathVariable("noteID") String noteID) {
        if (noteID.isEmpty() || noteID == null)
            return new SelectedNoteErrorCode(1, "Note ID is Not Valid!");
        return noteService.getNote(noteID);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteStatus> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO dto) {

        try {
            noteService.saveNote(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = {"/{noteID}"})
    public ResponseEntity<Void> updateNote(@PathVariable("noteID") String noteID, @RequestBody NoteDTO dto) {
        try {
            noteService.updateNote(noteID, dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{noteID}")
    public ResponseEntity<Void> deleteNote(@PathVariable("noteID") String noteID) {
        try {
            noteService.deleteNote(noteID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
