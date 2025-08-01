package com.scholarship.scholarship.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    private String id;

    @NotBlank(message = "Organisation name is required")
    @Size(max = 200, message = "Organisation name cannot exceed 200 characters")
    private String organisationName;

    @NotBlank(message = "Contact email is required")
    @Email(message = "Invalid email format")
    private String contactEmail;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
            message = "Phone number must be in a valid format")
    private String contactPhone;

    @NotBlank(message = "Contact person is required")
    private ProviderStaff contactPerson;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-])+\\.([\\w\\-\\.]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?$",
            message = "Website URL must be in a valid format")
    private String websiteUrl;

    @Size(max = 1000, message = "Organisation description cannot exceed 1000 characters")
    private String organisationDescription;

    @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
    private String logoUrl;

    @CreatedDate
    private Instant createdAt;
}