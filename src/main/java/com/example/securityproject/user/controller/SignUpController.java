package com.example.securityproject.user.controller;

import com.example.securityproject.user.domain.UserRole;
import com.example.securityproject.user.dto.UserRegisterDto;
import com.example.securityproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final UserService userService;

    /**
     * @return 회원가입 페이지 리소스
     */
    @GetMapping
    public String signup() {
        return "signup";
    }


    @PostMapping
    public String signup(@ModelAttribute UserRegisterDto userRegisterDto) {
        userService.signUp(
                userRegisterDto.getUsername(),
                userRegisterDto.getPassword(),
                UserRole.ROLE_USER
        );
        return "redirect:login";
    }
}
