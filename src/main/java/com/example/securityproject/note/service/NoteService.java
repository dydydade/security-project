package com.example.securityproject.note.service;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.note.repository.NoteRepository;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * 노트 조회
     * 유저는 본인의 노트만 조회할 수 있다.
     * 어드민은 모든 노트를 조회할 수 있다.
     *
     * @param user 노트를 찾을 유저
     * @return 유저가 조회할 수 있는 모든 노트 List
     */
    @Transactional(readOnly = true)
    public List<Note> findByUser(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.isAdmin()) {
            return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return noteRepository.findByUserOrderByIdDesc(user);
    }

    /**
     * 노트 저장
     *
     * @param user    노트 저장하는 유저
     * @param subject   제목
     * @param content 내용
     * @return 저장된 노트
     */
    @Transactional
    public Note saveNote(User user, String subject, String content) {
        if (user == null) {
            throw new UserNotFoundException();
        }
        return noteRepository.save(new Note(subject, content, user));
    }

    /**
     * 노트 삭제
     *
     * @param user   삭제하려는 노트의 유저
     * @param noteId 노트 ID
     */
    @Transactional
    public void deleteNote(User user, Long noteId) {
        if (user == null) {
            throw new UserNotFoundException();
        }
        Note note = noteRepository.findByIdAndUser(noteId, user);
        if (note != null) {
            noteRepository.delete(note);
        }
    }
}






