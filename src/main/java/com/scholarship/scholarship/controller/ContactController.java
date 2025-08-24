package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ContactRequestDto;
import com.scholarship.scholarship.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<?> submitContact(@RequestBody ContactRequestDto contactRequestDto) {
        contactService.handleContact(contactRequestDto);
        return ResponseEntity.ok(new SuccessResponse(true));
    }

    public static class SuccessResponse {
        public boolean success;
        public SuccessResponse(boolean success) { this.success = success; }
        public boolean isSuccess() { return success; }
    }
}
