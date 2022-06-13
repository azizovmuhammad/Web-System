package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.CategoryDto;
import com.example.registrationsystem.dto.ProductDto;
import com.example.registrationsystem.entity.Product;
import com.example.registrationsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody ProductDto productDto){
        return productService.create(productDto);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody ProductDto updateDto){
        return productService.update(id, updateDto);
    }

    @GetMapping("/getOne/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id){
        return productService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteOne(@PathVariable Long id){
        return productService.deleteOne(id);
    }

    @GetMapping("/getAll/pageable")
    public HttpEntity<?> getPageable(@ApiIgnore Pageable pageable){
        return productService.getPageable(pageable);
    }

    @GetMapping("/getAll/list")
    public HttpEntity<?> getAll(){
        return productService.getAll();
    }

    @PutMapping("/addFavourite/{id}")
    public HttpEntity<?> addFavourite(@PathVariable Long id, @RequestParam Boolean favourite){
        return productService.addFavourite(id, favourite);
    }

    @GetMapping("/getAll/favouriteList")
    public HttpEntity<?> getAllFavourite(){
        return productService.getAllFavourite();
    }



}
