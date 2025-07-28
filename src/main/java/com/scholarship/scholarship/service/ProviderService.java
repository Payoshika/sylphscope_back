package com.scholarship.scholarship.service;

import com.scholarship.scholarship.dto.ProviderDto;
import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.model.Provider;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.repository.ProviderRepository;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
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

    @Autowired
    private ProviderStaffRepository providerStaffRepository;

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

    public ProviderDto assignContactPerson(String providerId, ProviderStaffDto providerStaffDto) {
        Optional<Provider> providerOpt = providerRepository.findById(providerId);
        if (providerOpt.isPresent()) {
            Provider provider = providerOpt.get();
            ProviderStaff contactPerson = new ProviderStaff();
            org.springframework.beans.BeanUtils.copyProperties(providerStaffDto, contactPerson);
            provider.setContactPerson(contactPerson);
            Provider updatedProvider = providerRepository.save(provider);
            ProviderDto providerDto = new ProviderDto();
            org.springframework.beans.BeanUtils.copyProperties(updatedProvider, providerDto);
            return providerDto;
        }
        return null;
    }

    public List<ProviderStaffDto> getStaff(String providerId) {
        List<ProviderStaff> staffList = providerStaffRepository.findByProviderId(providerId);
        return staffList.stream().map(staff -> {
            ProviderStaffDto dto = new ProviderStaffDto();
            org.springframework.beans.BeanUtils.copyProperties(staff, dto);
            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }
}