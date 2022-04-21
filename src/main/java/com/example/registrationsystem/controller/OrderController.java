package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.OrderDto;
import com.example.registrationsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody OrderDto orderDto){
        return orderService.create(orderDto);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody OrderDto orderDto){
        return orderService.update(id, orderDto);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public HttpEntity<?> findById(@PathVariable Long id){
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id){
        return orderService.deleteBYId(id);
    }
}
