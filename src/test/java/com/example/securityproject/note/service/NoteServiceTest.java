package com.example.securityproject.note.service;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.note.repository.NoteRepository;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    private User user1;
    private User user2;
    private User admin;
    private Note note1;
    private Note note2;
    private Note note3;


    @BeforeEach
    public void setUp() {
        this.user1 = User.builder()
                .username("user1")
                .password("password1")
                .authority("ROLE_USER")
                .build();

        this.user2 = User.builder()
                .username("user2")
                .password("password2")
                .authority("ROLE_USER")
                .build();

        this.admin = User.builder()
                .username("admin")
                .password("password")
                .authority("ROLE_ADMIN")
                .build();

        this.note1 = Note.builder()
                .id(1L)
                .subject("제목1")
                .content("내용1")
                .user(user1)
                .build();

        this.note2 = Note.builder()
                .id(2L)
                .subject("제목2")
                .content("내용2")
                .user(user1)
                .build();

        this.note3 = Note.builder()
                .id(3L)
                .subject("제목3")
                .content("내용3")
                .user(user2)
                .build();
    }

    @Test
    @DisplayName("user_가_note_를_조회하면_대상_user_가_작성한_note_들이_반환된다.")
    public void findByUserTest() {
        // given
        ArrayList<Note> notes = new ArrayList<>(Arrays.asList(note1, note2));

        given(noteRepository.findByUserOrderByIdDesc(user1))
                .willReturn(notes);

        // when
        // then
        assertThat(noteService.findByUser(user1)).isEqualTo(notes);
    }

    @Test
    @DisplayName("admin_이_note_를_조회하면_모든_note_들이_반환된다.")
    public void findByUserAdminTest() {
        // given
        ArrayList<Note> notes = new ArrayList<>(Arrays.asList(note1, note2, note3));

        given(noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id")))
                .willReturn(notes);

        // when
        // then
        assertThat(noteService.findByUser(admin)).isEqualTo(notes);
    }

    @Test
    @DisplayName("note_를_조회하는_user_가_null_이면_UserNotFoundException_을_반환한다.")
    public void findByUserNullTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> noteService.findByUser(null))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("user_가_note_를_저장하면_note_가_저장된다.")
    public void saveNoteTest() {
        // given
        given(noteRepository.save(any()))
                .willReturn(note1);

        // when
        // then
        assertThat(noteService.saveNote(user1, note1.getSubject(), note1.getContent())).isEqualTo(note1);
    }

    @Test
    @DisplayName("note_를_저장하는_user_가_null_이면_UserNotFoundException_을_반환한다.")
    public void saveNoteNullTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> noteService.saveNote(null, "제목1", "내용1"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("note_가_존재할때_user_가_note_를_삭제하면_note_가_삭제된다.")
    public void userDeleteNoteWhenNoteExistTest() {
        // given
        given(noteRepository.findByIdAndUser(note1.getId(), user1))
                .willReturn(note1);

        // when
        noteService.deleteNote(user1, note1.getId());

        // then
        verify(noteRepository).delete(note1);
    }

    @Test
    @DisplayName("note_가_존재하지_않을_때_user_가_note_를_삭제하면_note_가_삭제되지_않는다.")
    public void userDeleteNoteWhenNoteDoesntExistTest() {
        // given
        given(noteRepository.findByIdAndUser(note1.getId(), user1))
                .willReturn(null);

        // when
        noteService.deleteNote(user1, note1.getId());

        // then
        verify(noteRepository, never()).delete(any(Note.class));
    }

    @Test
    @DisplayName("note_를_삭제하는_user_가_null_이면_UserNotFoundException_을_반환한다.")
    public void deleteNoteNullTest() {
        // given
        // when
        // then
        assertThatThrownBy(() -> noteService.deleteNote(null, note1.getId()))
                .isInstanceOf(UserNotFoundException.class);
    }
}









































