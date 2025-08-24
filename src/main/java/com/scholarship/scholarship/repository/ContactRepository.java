package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
}

