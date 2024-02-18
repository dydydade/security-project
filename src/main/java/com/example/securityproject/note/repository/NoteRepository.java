package com.example.securityproject.note.repository;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByIdDesc(User user);

    Note findByIdAndUser(Long noteId, User user);


}
