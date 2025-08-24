package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ContactRequestDto;
import com.scholarship.scholarship.model.Contact;
import com.scholarship.scholarship.notification.EmailService;
import com.scholarship.scholarship.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    public void handleContact(ContactRequestDto dto) {
        Contact contact = new Contact();
        contact.setCategory(dto.getCategory());
        contact.setTitle(dto.getTitle());
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setMessage(dto.getMessage());
        contactRepository.save(contact);

        String subject = "inquiry : " + dto.getTitle();
        String body = "Category: " + dto.getCategory() + "\n" +
                      "Name: " + dto.getName() + "\n" +
                      "Email: " + dto.getEmail() + "\n" +
                      "Message: " + dto.getMessage();
        emailService.sendEmail("kohei_oishi@the-five-books.com", subject, body);
        emailService.sendEmail(dto.getEmail(), "confirmation of " + subject, "your inuqiry " + body);
    }
}
