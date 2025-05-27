package com.scholarship.scholarship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScholarshipController {

    @GetMapping("/scholarships")
     public ResponseEntity<List<Scholarship>> getAllScholarships() {
        return null;
     }
}
