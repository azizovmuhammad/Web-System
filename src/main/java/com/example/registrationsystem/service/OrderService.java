package com.example.registrationsystem.service;

import com.example.registrationsystem.dto.OrderDto;
import org.springframework.http.HttpEntity;

public interface OrderService {

    HttpEntity<?> create(OrderDto orderDto);

    HttpEntity<?> update(Long id, OrderDto orderDto);

    HttpEntity<?> getAll();

    HttpEntity<?> findById(Long id);

    HttpEntity<?> deleteBYId(Long id);
}
