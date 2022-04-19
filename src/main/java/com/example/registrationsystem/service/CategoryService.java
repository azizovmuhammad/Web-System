package com.example.registrationsystem.service;

import com.example.registrationsystem.dto.CategoryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

public interface CategoryService {

    HttpEntity<?> create(CategoryDto categoryDto);

    HttpEntity<?> update(Long id, CategoryDto categoryDto);

    HttpEntity<?> getOne(Long id);

    HttpEntity<?> deleteOne(Long id);

    HttpEntity<?> getPageable(Pageable pageable);

    HttpEntity<?> getAll();
}
