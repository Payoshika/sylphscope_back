package com.scholarship.scholarship.service;
import com.scholarship.scholarship.enums.StaffRole;

import com.scholarship.scholarship.dto.ProviderStaffDto;
import com.scholarship.scholarship.model.ProviderStaff;
import com.scholarship.scholarship.repository.ProviderStaffRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderStaffService {

    @Autowired
    private ProviderStaffRepository providerStaffRepository;

    public ProviderStaffDto createProviderStaff(ProviderStaffDto providerStaffDto) {
        ProviderStaff providerStaff = new ProviderStaff();
        BeanUtils.copyProperties(providerStaffDto, providerStaff);
        ProviderStaff savedStaff = providerStaffRepository.save(providerStaff);
        BeanUtils.copyProperties(savedStaff, providerStaffDto);
        return providerStaffDto;
    }

    public Optional<ProviderStaffDto> getProviderStaffById(String id) {
        return providerStaffRepository.findById(id)
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                });
    }

    public List<ProviderStaffDto> getStaffByProviderId(String providerId) {
        return providerStaffRepository.findByProviderId(providerId).stream()
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Optional<ProviderStaffDto> getStaffByUserIdAndProviderId(String userId, String providerId) {
        return providerStaffRepository.findByUserIdAndProviderId(userId, providerId)
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                });
    }

    public List<ProviderStaffDto> getStaffByUserId(String userId) {
        return providerStaffRepository.findByUserId(userId).stream()
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ProviderStaffDto> getStaffByRole(StaffRole role) {
        return providerStaffRepository.findByRole(role).stream()
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ProviderStaffDto> getAllProviderStaff() {
        return providerStaffRepository.findAll().stream()
                .map(staff -> {
                    ProviderStaffDto dto = new ProviderStaffDto();
                    BeanUtils.copyProperties(staff, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ProviderStaffDto updateProviderStaff(String id, ProviderStaffDto providerStaffDto) {
        Optional<ProviderStaff> staffOpt = providerStaffRepository.findById(id);
        if (staffOpt.isPresent()) {
            ProviderStaff staff = staffOpt.get();
            BeanUtils.copyProperties(providerStaffDto, staff);
            ProviderStaff updatedStaff = providerStaffRepository.save(staff);
            BeanUtils.copyProperties(updatedStaff, providerStaffDto);
            return providerStaffDto;
        }
        return null;
    }

    public void deleteProviderStaff(String id) {
        providerStaffRepository.deleteById(id);
    }
}
