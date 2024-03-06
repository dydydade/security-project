package com.example.securityproject.config;

import com.example.securityproject.note.service.NoteService;
import com.example.securityproject.notice.service.NoticeService;
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
    private final NoticeService noticeService;

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


    /**
     * 어드민등록, 공지사항 2개 등록
     */
    @Bean
    public void initializeDefaultAdmin() {
        User admin = userService.signUp("admin", "admin", "ROLE_ADMIN");
        noticeService.saveNotice(admin, "환영합니다.", "환영합니다 여러분");
        noticeService.saveNotice(admin, "노트 작성 방법 공지", "1. 회원가입\n2. 로그인\n3. 노트 작성\n4. 저장\n* 본인 외에는 게시글을 볼 수 없습니다.");
    }
}
































