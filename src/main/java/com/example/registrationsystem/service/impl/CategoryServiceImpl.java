package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.dto.CategoryDto;
import com.example.registrationsystem.dto.response.Response;
import com.example.registrationsystem.entity.Category;
import com.example.registrationsystem.repository.CategoryRepository;
import com.example.registrationsystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public static Response response;

    @Override
    public HttpEntity<?> create(CategoryDto categoryDto) {
        Category category = new Category();

        category.setName(categoryDto.getName());

        if (categoryDto.getParentId() != null){
            Category byId = findById(categoryDto.getParentId());
            if (byId != null){
                category.setParent(byId);
            }
        }
        categoryRepository.save(category);
        response = new Response(true, "Successfully created", category);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> update(Long id, CategoryDto categoryDto) {

        Category category = findById(id);
        if (category != null){
            category.setName((categoryDto.getName()));
            Category parent = findById(categoryDto.getParentId());
            if (parent != null){
                category.setParent(parent);
            }
            category = categoryRepository.save(category);
            response = new Response(true, "Updated Category", category);
        }

        else {
            response = new Response(false,"Category Not Found With Id [" + id + " ]");
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> getOne(Long id) {

        Category category = findById(id);
        if (category != null){
            response = new Response(true, "Category", category);
        }
        else {
            response = new Response(false, "Category", "Category was not found with id {" + id + "}");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> deleteOne(Long id) {

        Category category = findById(id);

        if (category != null){
            categoryRepository.delete(category);
            response = new Response(true, "Category {" + category.getName()+"} was successfully deleted");
        }
        else {
            response = new Response(false, "Category Not Found with id: [" + id + "]");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> getPageable(Pageable pageable) {
        Page<Category> all = categoryRepository.findAll(pageable);
        List<Category> categoryList = all.getContent();
        response = new Response(true, "Category Pageable List", categoryList);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> getAll() {
        List<Category> categories = categoryRepository.findAll();
        response = new Response(true, "Category List", categories);
        return ResponseEntity.ok(response);
    }


    private Category findById(Long parentId) {
        Optional<Category> optionalCategory = categoryRepository.findById(parentId);
        return optionalCategory.orElse(null);
    }
}
