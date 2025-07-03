package com.scholarship.scholarship.model;

import com.scholarship.scholarship.valueObject.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "predefined_option_sets")
public class PredefinedOptionSet {

    @Id
    private String id;

    @Indexed(unique = true)
    private String key;

    private String defaultQuestionText;

    private String defaultDescription;

    private List<Option> options;

    @CreatedDate
    private Instant createdAt;

}