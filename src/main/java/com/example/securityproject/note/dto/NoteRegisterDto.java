package com.example.securityproject.note.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class NoteRegisterDto {
    private String subject;
    private String content;
}
