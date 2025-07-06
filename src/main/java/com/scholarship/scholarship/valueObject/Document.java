package com.scholarship.scholarship.valueObject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @NotBlank(message = "Document URL is required")
    private String url;

    @NotBlank(message = "File type is required")
    private String fileType;

    @NotNull(message = "File size is required")
    @Min(value = 0, message = "File size must be at least 0 bytes")
    private Long sizeInBytes;

    @NotBlank(message = "File name is required")
    private String fileName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}