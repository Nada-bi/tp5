package com.isima.easynotes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isima.easynotes.note.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleContaining(String title); // Case insensitive search

    @Query("SELECT n FROM Note n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :mot, '%')) OR LOWER(n.content) LIKE LOWER(CONCAT('%', :mot, '%'))")
    List<Note> searchByMotInTitreOrContenu(@Param("mot") String mot);

    List<Note> findAllByOrderByCreatedAtDesc(); // Changed to use createdAt

    List<Note> findByCreatedAtBefore(LocalDateTime date); // Changed to use createdAt

    @Query("SELECT n FROM Note n WHERE n.updatedAt >= :date")
    List<Note> findNotesUpdatedInLastDays(@Param("date") LocalDateTime date);
}
