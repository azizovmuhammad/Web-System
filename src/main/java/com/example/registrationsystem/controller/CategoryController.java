package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.CategoryDto;
import com.example.registrationsystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/api/v1/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody CategoryDto categoryDto){
        return categoryService.create(categoryDto);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        return categoryService.update(id, categoryDto);
    }

    @GetMapping("/getOne/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id){
        return categoryService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteOne(@PathVariable Long id){
        return categoryService.deleteOne(id);
    }

    @GetMapping("/getAll/pageable")
    public HttpEntity<?> getPageable(@ApiIgnore Pageable pageable){
        return categoryService.getPageable(pageable);
    }

    @GetMapping("/getAll/list")
    public HttpEntity<?> getAll(){
        return categoryService.getAll();
    }
}
