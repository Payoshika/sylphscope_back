package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.model.ContactCategory;

public class ContactRequestDto {
    private ContactCategory category;
    private String title;
    private String name;
    private String email;
    private String message;

    // Getters and setters
    public ContactCategory getCategory() { return category; }
    public void setCategory(ContactCategory category) { this.category = category; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

