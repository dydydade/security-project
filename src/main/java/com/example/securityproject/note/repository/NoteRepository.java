package com.example.securityproject.note.repository;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUserOrderByIdDesc(User user);

    Note findByIdAndUser(Long noteId, User user);

    Page<Note> findAll(Pageable pageable);

    Slice<Note> findByUser(User user, Pageable pageable);
}
