package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.enums.Country;
import com.scholarship.scholarship.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String id;
    private String userId;
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
    private Gender gender;
    private String dateOfBirth;
}
