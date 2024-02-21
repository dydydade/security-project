package com.example.securityproject.config;

import com.example.securityproject.note.service.NoteService;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile(value = "!test") // test profile 에서는 제외
public class InitializeDefaultConfig {

    private final UserService userService;
    private final NoteService noteService;

    /**
     * 유저 등록, Note 4개 등록
     */
    @Bean
    public void initializeDefaultUser() {
        User user = userService.signUp("user", "user", "ROLE_USER");

        noteService.saveNote(user, "테스트제목1", "테스트내용1");
        noteService.saveNote(user, "테스트제목2", "테스트내용2");
        noteService.saveNote(user, "테스트제목3", "테스트내용3");
        noteService.saveNote(user, "테스트제목4", "테스트내용4");
    }
}
































