package com.isima.easynotes.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isima.easynotes.note.Note;
import com.isima.easynotes.repository.NoteRepository;

@RestController

public class NoteController {
   
	 private final NoteRepository noteRepository;

	    @Autowired // Ensures that the repository is injected
	    public NoteController(NoteRepository noteRepository) {
	        this.noteRepository = noteRepository;
	    }
    
    

    // Consulter toutes les notes
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Chercher une note par son ID
    @GetMapping("/{id}")
    public Optional<Note> getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id);
    }

    // Ajouter une nouvelle note
    @PostMapping
    public void addNote(@RequestBody Note note) {
    	noteRepository.save(note);
    	
    }

    // Mettre à jour une note existante
    @PutMapping
    public void updateNote(@RequestBody Note note) {
    	noteRepository.save(note);
    	
    }

    // Supprimer une note par ID
    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }

    // Chercher une note par son titre
    @GetMapping("/search/{titre}")
    public List<Note> searchByTitre(@PathVariable String titre) {
        return noteRepository.findByTitleContaining(titre);
    }

    // Chercher les notes contenant un mot dans le titre ou le contenu
    @GetMapping("/searchByMot/{mot}")
    public List<Note> searchByMotInTitreOrContenu(@PathVariable String mot) {
        return noteRepository.searchByMotInTitreOrContenu(mot);
    }

    // Afficher toutes les notes ordonnées par date de création
    @GetMapping("/sortedByDateCreation")
    public List<Note> getAllNotesSortedByDateCreation() {
        return noteRepository.findAllByOrderByCreatedAtDesc();
    }

    // Chercher les notes créées avant une date donnée
    /* @GetMapping("/beforeDate")
    public List<Note> getNotesBeforeDate(@RequestParam String date) {
        LocalDateTime localDate = LocalDateTime.parse(date);
        return noteRepository.findByDateCreationBefore(localDate);
    }*/

    // Afficher les notes mises à jour les trois derniers jours
    @GetMapping("/recentlyUpdated")
    public List<Note> getRecentlyUpdatedNotes() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minus(3, ChronoUnit.DAYS);
        return noteRepository.findNotesUpdatedInLastDays(threeDaysAgo);
    }

}
