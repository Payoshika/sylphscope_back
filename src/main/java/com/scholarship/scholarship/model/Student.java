package com.scholarship.scholarship.model;

import com.scholarship.scholarship.enums.Country;
import com.scholarship.scholarship.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Student {
    @Id
    private String id;

    @NotBlank(message = "User ID is required")
    private String userId; // Reference to User ID

    @NotBlank(message = "First name is required")
    @Size(max = 30, message = "First name cannot exceed 100 characters")
    private String firstName;

    @Size(max = 30, message = "Middle name cannot exceed 100 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 30, message = "Last name cannot exceed 100 characters")
    private String lastName;

    private Country addressCountry;

    @Size(max = 30, message = "State/Province cannot exceed 100 characters")
    private String addressState;

    @Size(max = 30, message = "City cannot exceed 100 characters")
    private String addressCity;

    @Size(max = 100, message = "Additional address information cannot exceed 100 characters")
    private String addressElse;

    private Country[] citizenshipCountry;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-])+\\.([\\w\\-\\.]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?$",
            message = "Profile picture URL must be in a valid format")
    private String profilePictureUrl;

    private String phoneNumber;

    private Date dateOfBirth;

    private Gender gender;
}