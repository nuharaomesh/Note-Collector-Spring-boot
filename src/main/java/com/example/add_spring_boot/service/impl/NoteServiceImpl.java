package com.example.add_spring_boot.service.impl;

import com.example.add_spring_boot.customStatusCode.SelectedNoteErrorCode;
import com.example.add_spring_boot.dao.NoteDAO;
import com.example.add_spring_boot.dto.NoteStatus;
import com.example.add_spring_boot.dto.impl.NoteDTO;
import com.example.add_spring_boot.entity.impl.Note;
import com.example.add_spring_boot.exception.DataPersistException;
import com.example.add_spring_boot.exception.NoteNotFoundException;
import com.example.add_spring_boot.service.NoteService;
import com.example.add_spring_boot.util.AppUtil;
import com.example.add_spring_boot.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public NoteStatus getNote(String ID) {
        if (noteDAO.existsById(ID)) {
            Note selectedUser = noteDAO.getReferenceById(ID);
            return mapping.toNoteDTO(selectedUser);
        } else {
            return new SelectedNoteErrorCode(2, "Selected note not found");
        }
    }

    @Override
    public List<NoteStatus> getAllNotes() {
        return mapping.getNoteDTOList(noteDAO.findAll());
    }

    @Override
    public void saveNote(NoteDTO dto) {
        dto.setNoteID(AppUtil.generateNoteID());
        Note savedNote =
                noteDAO.save(mapping.toNoteEntity(dto));
        if (savedNote == null)
            throw new DataPersistException("Note Not Saved!");
    }

    @Override
    public void updateNote(String ID, NoteDTO dto) {
        Optional<Note> tmpNote = noteDAO.findById(ID);
        if (!tmpNote.isPresent())
            throw new NoteNotFoundException("Note Not Found!");
        else
            tmpNote.get().setNoteTitle(dto.getNoteTitle());
            tmpNote.get().setNoteDesc(dto.getNoteDesc());
            tmpNote.get().setCreateDate(dto.getCreateDate());
            tmpNote.get().setPriorityLvl(dto.getPriorityLvl());
    }

    @Override
    public void deleteNote(String ID) {
        Optional<Note> tmpNote = noteDAO.findById(ID);
        if (!tmpNote.isPresent()) {
            throw new NoteNotFoundException("Note Not Found!");
        } else {
            noteDAO.deleteById(ID);
        }
    }
}
