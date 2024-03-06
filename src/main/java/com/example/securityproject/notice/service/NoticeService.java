package com.example.securityproject.notice.service;

import com.example.securityproject.notice.domain.Notice;
import com.example.securityproject.notice.repository.NoticeRepository;
import com.example.securityproject.user.domain.User;
import com.example.securityproject.user.exception.UnauthorizedAccessAttemptException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Notice saveNotice(User user, String subject, String content) {
        if (!user.isAdmin()) {
            throw new UnauthorizedAccessAttemptException("ADMIN 권한을 보유한 사용자만 공지사항 등록이 가능합니다.");
        }
        return noticeRepository.save(
                Notice.builder()
                        .subject(subject)
                        .content(content)
                        .build());
    }

    @Transactional
    public void deleteById(User user, Long id) {
        if (!user.isAdmin()) {
            throw new UnauthorizedAccessAttemptException("ADMIN 권한을 보유한 사용자만 공지사항 삭제가 가능합니다.");
        }
        noticeRepository.deleteById(id);
    }
}
