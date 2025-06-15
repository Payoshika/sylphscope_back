package com.scholarship.scholarship.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document(collection = "scholarships")
@NoArgsConstructor
@AllArgsConstructor
public class Scholarship {
    private String id;
    private String name;
    private String description;
    private String eligibilityCriteria;
    private String applicationDeadline;
    private String amount;
    private String status;
    private String createdAt;
    private String updatedAt;
    @DocumentReference
    private List<Student> potentialOffer;
}
