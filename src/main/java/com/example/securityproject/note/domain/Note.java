package com.example.securityproject.note.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.securityproject.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Note {

    @Id @GeneratedValue
    private Long id;

    // 제목
    private String subject;

    // 내용
    @Lob
    private String content;

    // User 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 생성일자
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수정일자
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Note(String subject, String content, User user) {
        this.subject = subject;
        this.content = content;
        this.user = user;
    }

    public void updateContent(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

}
































