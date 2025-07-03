package com.scholarship.scholarship.controller;

import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        ProviderDto createdProvider = providerService.createProvider(providerDto);
        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable String id) {
        return providerService.getProviderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        List<ProviderDto> providers = providerService.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @GetMapping("/organisation/{name}")
    public ResponseEntity<ProviderDto> getProviderByOrganisationName(@PathVariable String name) {
        return providerService.getProviderByOrganisationName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDto> updateProvider(@PathVariable String id, @RequestBody ProviderDto providerDto) {
        ProviderDto updatedProvider = providerService.updateProvider(id, providerDto);
        if (updatedProvider != null) {
            return ResponseEntity.ok(updatedProvider);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable String id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}