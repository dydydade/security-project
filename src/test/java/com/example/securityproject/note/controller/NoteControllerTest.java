package com.example.securityproject.note.controller;

import com.example.securityproject.note.repository.NoteRepository;
import com.example.securityproject.note.service.NoteService;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    MockMvc mockMvc;

    @MockBean
    NoteService noteService;

    @BeforeEach
    public void setUp(@Autowired WebApplicationContext applicationContext) {

        MockMvcBuilders.webAppContextSetup(applicationContext)
                        .apply(springSecurity())
                        .alwaysDo(print())
                        .build();

        user = userRepository.save(new User("user123", "user", "ROLE_USER"));
        admin = userRepository.save(new User("admin123", "admin", "ROLE_ADMIN"));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("인증_없이_Note_페이지에_접근하면_Login_페이지로_리다이렉트된다.")
    void getNotePageTest() throws Exception {
        mockMvc.perform(get("/note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithUserDetails(
            userDetailsServiceBeanName = "userDetailsService",
            value = "user123"
    )
    @DisplayName("인증_후_Note_페이지에_접근하면_Note_페이지로_이동한다.")
    void getNotePageWithAuthTest() throws Exception {
        mockMvc.perform(get("/note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("")
    void saveNote() {
    }

    @Test
    void deleteNote() {
    }
}