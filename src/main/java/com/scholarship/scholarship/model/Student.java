package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.Country;
import com.scholarship.scholarship.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String id;
    private String userId; // Reference to User ID

    private String firstName;
    private String middleName;
    private String lastName;

    private Country addressCountry;
    private String addressState;
    private String addressCity;
    private String addressElse;
    private Country[] citizenshipCountry;
    private String profilePictureUrl;
    private String phoneNumber;
    private String dateOfBirth;
    private Gender gender;
}
