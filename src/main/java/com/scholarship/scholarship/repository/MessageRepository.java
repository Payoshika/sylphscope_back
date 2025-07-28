package com.scholarship.scholarship.repository;

import com.scholarship.scholarship.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByStudentId(String studentId);
    List<Message> findByProviderStaffId(String providerStaffId);
}
