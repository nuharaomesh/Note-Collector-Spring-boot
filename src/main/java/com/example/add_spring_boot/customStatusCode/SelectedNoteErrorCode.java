package com.example.add_spring_boot.customStatusCode;

import com.example.add_spring_boot.dto.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SelectedNoteErrorCode implements NoteStatus {
    private int statusCode;
    private String statusMessage;
}
