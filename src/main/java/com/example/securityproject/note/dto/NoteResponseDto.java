package com.example.securityproject.note.dto;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoteResponseDto {

    private Long id;

    // 제목
    private String subject;

    // 내용
    private String content;

    // 생성일자
    @CreatedDate
    private LocalDateTime createdAt;

    // 수정일자
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public NoteResponseDto(Note note) {
        this.id = note.getId();
        this.subject = note.getSubject();
        this.content = note.getContent();
        this.createdAt = note.getCreatedAt();
        this.updatedAt = note.getUpdatedAt();
    }
}






