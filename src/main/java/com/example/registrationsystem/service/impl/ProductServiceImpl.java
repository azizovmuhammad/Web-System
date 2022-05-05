package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.dto.ProductDto;
import com.example.registrationsystem.dto.response.Response;
import com.example.registrationsystem.entity.Attachment;
import com.example.registrationsystem.entity.Category;
import com.example.registrationsystem.entity.Product;
import com.example.registrationsystem.entity.User;
import com.example.registrationsystem.repository.AttachmentRepository;
import com.example.registrationsystem.repository.CategoryRepository;
import com.example.registrationsystem.repository.ProductRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    public static Response response;

    @Override
    public HttpEntity<?> create(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            product.setAttachment(attachment);
        }
        else {
            response = new Response(false,"Attachment Not Found With Id [" + productDto.getAttachmentId() + " ]");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            product.setCategory(category);
        }
        else {
            response = new Response(false,"Category Not Found With Id [" + productDto.getCategoryId() + " ]");
        }

        Product saved = productRepository.save(product);
        response = new Response(true, "Product successfully saved", saved);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> update(Long id, ProductDto updateDto) {
        Product product = findById(id);
        product.setName(updateDto.getName());
        product.setDescription(updateDto.getDescription());
        product.setPrice(updateDto.getPrice());

        Optional<Category> optionalCategory = categoryRepository.findById(updateDto.getCategoryId());
        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();

            product.setCategory(category);

        }
        Product save = productRepository.save(product);
        response = new Response(true, "Product updated", save);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> getOne(Long id) {
        Product product = findById(id);
        if (product != null){
            response = new Response(true, "Product", product);
        }
        else {
            response = new Response(false, "Product", "Product was not found with id {" + id + "}");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> deleteOne(Long id) {
        Product product = findById(id);
        if (product != null){
            productRepository.delete(product);
            response = new Response(true, "Product {" + product.getName()+"} was successfully deleted");
        }
        else {
            response = new Response(false, "Product was not found with id {" + id + "}");
        }
        return null;
    }

    @Override
    public HttpEntity<?> getPageable(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> productList = products.getContent();

        response = new Response(true, "Product Pageable List", productList);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> getAll() {
        List<Product> productList = productRepository.findAll();

        response = new Response(true, "Product List", productList);
        return ResponseEntity.ok(response);
    }

    private Product findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }
}
