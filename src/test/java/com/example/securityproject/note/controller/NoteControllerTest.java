package com.example.securityproject.note.controller;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.note.repository.NoteRepository;
import com.example.securityproject.note.service.NoteService;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@Transactional
class NoteControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    private MockMvc mockMvc;
    private User user;
    private User admin;

    @MockBean
    NoteService noteService;

    @BeforeEach
    public void setUp(@Autowired WebApplicationContext applicationContext) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                        .apply(springSecurity())
                        .alwaysDo(print())
                        .build();

        this.user = userRepository.save(new User("user123", "user", "ROLE_USER"));
        this.admin = userRepository.save(new User("admin123", "admin", "ROLE_ADMIN"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("인증_없이_Note_페이지에_접근하면_Login_페이지로_리다이렉트된다.")
    void getNotePageWithNoAuth() throws Exception {
        mockMvc.perform(get("/note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            setupBefore = TestExecutionEvent.TEST_EXECUTION,
            userDetailsServiceBeanName = "userDetailsService",
            value = "user123"
    )
    @DisplayName("인증_후_Note_페이지에_접근하면_Note_페이지로_이동한다.")
    void getNotePageWithAuth() throws Exception {
        mockMvc.perform(get("/note"))
                .andExpect(status().isOk())
                .andExpect(view().name("note/index"));
    }

    @Test
    @DisplayName("인증_없이_Note_를_등록하면_Login_페이지로_리다이렉트된다.")
    void saveNoteWithNoAuth() throws Exception {
        mockMvc.perform(post("/note").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subject", "제목")
                        .param("content", "내용")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    @WithUserDetails(
            setupBefore = TestExecutionEvent.TEST_EXECUTION,
            userDetailsServiceBeanName = "userDetailsService",
            value = "user123"
    )
    @DisplayName("User_권한을_가진_사용자가_Note_를_등록하면_Note_가_등록된다.")
    void saveNoteWithUserAuth() throws Exception {
        mockMvc.perform(post("/note").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("subject", "제목")
                        .param("content", "내용")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/note"));


    }

    // TODO: 추후 어드민 feature 개발 후 saveNoteWithAdminAuth() 테스트 추가

    @Test
    @DisplayName("인증_없이_Note_를_삭제하면_Login_페이지로_리다이렉트된다.")
    void deleteNoteWithNoAuth() throws Exception {
        Note note = noteRepository.save(new Note("제목", "내용", user));
        mockMvc.perform(delete("/note").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(note.getId()))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            setupBefore = TestExecutionEvent.TEST_EXECUTION,
            userDetailsServiceBeanName = "userDetailsService",
            value = "user123"
    )
    @DisplayName("User_권한을_가진_사용자가_Note_를_삭제하면_Note_가_삭제된다.")
    void deleteNoteWithUserAuth() throws Exception {
        Note note = noteRepository.save(new Note("제목", "내용", user));
        mockMvc.perform(delete("/note").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(note.getId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/note"));
    }

    // TODO: 추후 어드민 feature 개발 후 deleteNoteWithAdminAuth() 테스트 추가
}