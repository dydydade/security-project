package com.example.securityproject.notice.controller;

import com.example.securityproject.notice.domain.Notice;
import com.example.securityproject.notice.repository.NoticeRepository;
import com.example.securityproject.notice.service.NoticeService;
import com.example.securityproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String getNoticePage(Model model) {
        List<Notice> notices = noticeService.findAll();
        model.addAttribute("notices", notices);
        return "notice/index";
    }

    @PostMapping
    public String saveNotice(Authentication authentication, String subject, String content) {
        User user = (User) authentication.getPrincipal();
        noticeService.saveNotice(user, subject, content);
        return "redirect:/notice";
    }

    @DeleteMapping
    public String deleteNotice(Authentication authentication, @RequestParam Long id) {
        User user = (User) authentication.getPrincipal();
        noticeService.deleteById(user, id);
        return "redirect:/notice";
    }
}
