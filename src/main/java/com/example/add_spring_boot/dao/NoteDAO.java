package com.example.add_spring_boot.dao;

import com.example.add_spring_boot.entity.impl.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDAO extends JpaRepository<Note, String> {}
