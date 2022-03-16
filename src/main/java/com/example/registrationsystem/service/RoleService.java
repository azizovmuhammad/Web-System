package com.example.registrationsystem.service;

import com.example.registrationsystem.dto.RoleDto;
import com.example.registrationsystem.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

public interface RoleService {

    HttpEntity<?> createRole(RoleDto roleDto);

    HttpEntity<?> updateRole(RoleDto roleDto, Long id);

    Role findById(Long id);

    HttpEntity<?> findAll(Pageable pageable);

    HttpEntity<?> deleteById(Long id);
}
