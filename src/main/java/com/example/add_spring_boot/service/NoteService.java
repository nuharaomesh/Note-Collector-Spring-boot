package com.example.add_spring_boot.service;

import com.example.add_spring_boot.dto.NoteStatus;
import com.example.add_spring_boot.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    NoteStatus getNote(String ID);
    List<NoteStatus> getAllNotes();
    void saveNote(NoteDTO dto);
    void updateNote(String ID, NoteDTO dto);
    void deleteNote(String ID);
}
