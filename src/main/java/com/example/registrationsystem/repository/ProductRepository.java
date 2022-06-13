package com.example.registrationsystem.repository;

import com.example.registrationsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from Product where favourite = true ", nativeQuery = true )
    List<Product> findAllByFavourite();
}
