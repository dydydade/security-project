package com.example.securityproject.note.controller;

import com.example.securityproject.note.domain.Note;
import com.example.securityproject.note.dto.NoteResponseDto;
import com.example.securityproject.note.service.NoteService;
import com.example.securityproject.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoteRestController {

    private final NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<Map<String, Object>> getNotePageData(Authentication authentication,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size) {
        User user = (User) authentication.getPrincipal();
        Slice<NoteResponseDto> noteSlice = noteService.findByUser(user, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("notes", noteSlice.getContent());
        response.put("hasNext", noteSlice.hasNext());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNote(
            Authentication authentication,
            @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        noteService.deleteNote(user, id);
        return ResponseEntity.ok().build();
    }
}






















