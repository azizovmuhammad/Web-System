package com.example.registrationsystem.service;

import com.example.registrationsystem.dto.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;

public interface ProductService {

    HttpEntity<?> create(ProductDto productDto);

    HttpEntity<?> update(Long id, ProductDto updateDto);

    HttpEntity<?> getOne(Long id);

    HttpEntity<?> deleteOne(Long id);

    HttpEntity<?> getPageable(Pageable pageable);

    HttpEntity<?> getAll();

    HttpEntity<?> addFavourite(Long id, Boolean favourite);

    HttpEntity<?> getAllFavourite();
}
