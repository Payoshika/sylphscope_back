package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.repository.ProviderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public ProviderDto createProvider(ProviderDto providerDto) {
        Provider provider = new Provider();
        BeanUtils.copyProperties(providerDto, provider);
        Provider savedProvider = providerRepository.save(provider);
        BeanUtils.copyProperties(savedProvider, providerDto);
        return providerDto;
    }

    public Optional<ProviderDto> getProviderById(String id) {
        return providerRepository.findById(id)
                .map(provider -> {
                    ProviderDto dto = new ProviderDto();
                    BeanUtils.copyProperties(provider, dto);
                    return dto;
                });
    }

    public Optional<ProviderDto> getProviderByOrganisationName(String organisationName) {
        return providerRepository.findByOrganisationName(organisationName)
                .map(provider -> {
                    ProviderDto dto = new ProviderDto();
                    BeanUtils.copyProperties(provider, dto);
                    return dto;
                });
    }

    public List<ProviderDto> getAllProviders() {
        return providerRepository.findAll().stream()
                .map(provider -> {
                    ProviderDto dto = new ProviderDto();
                    BeanUtils.copyProperties(provider, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ProviderDto updateProvider(String id, ProviderDto providerDto) {
        Optional<Provider> providerOpt = providerRepository.findById(id);
        if (providerOpt.isPresent()) {
            Provider provider = providerOpt.get();
            BeanUtils.copyProperties(providerDto, provider);
            Provider updatedProvider = providerRepository.save(provider);
            BeanUtils.copyProperties(updatedProvider, providerDto);
            return providerDto;
        }
        return null;
    }

    public void deleteProvider(String id) {
        providerRepository.deleteById(id);
    }
}