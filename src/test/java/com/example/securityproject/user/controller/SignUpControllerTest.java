package com.example.securityproject.user.controller;

import com.example.securityproject.user.dto.UserRegisterDto;
import com.example.securityproject.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("권한이 없는 사용자가 회원가입 페이지에 접근해도 오류가 발생하지 않는다.")
    public void getSignupPageTest() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }


    @Test
    @DisplayName("유저가 ID와 PW를 입력하고 회원가입하면 로그인 페이지로 리다이렉트된다.")
    public void postSignupTest() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(post("/signup").with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "username")
                .param("password", "password")
        )
                .andExpect(redirectedUrl("login"))
                .andExpect(status().is3xxRedirection());
    }
}