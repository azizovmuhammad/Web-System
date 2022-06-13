package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.dto.OrderDto;
import com.example.registrationsystem.dto.response.Response;
import com.example.registrationsystem.entity.Order;
import com.example.registrationsystem.entity.Product;
import com.example.registrationsystem.repository.OrderRepository;
import com.example.registrationsystem.repository.ProductRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    public static Response response;

    @Override
    public HttpEntity<?> create(OrderDto orderDto) {
        Product product = new Product();
        Order order = new Order();
        Optional<Product> optionalProduct = productRepository.findById(orderDto.getProductId());
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
            order.setProduct(product);
        }
        else {
            response = new Response(false,"Product Not Found With Id [" + orderDto.getProductId() + " ]");
        }


        order.setQuantity(orderDto.getQuantity());
        order.setTotalPrice(product.getPrice()* order.getQuantity());

        Order saved = orderRepository.save(order);

        response = new Response(true, "Order successfully saved", saved);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> update(Long id, OrderDto orderDto) {
        Product product = new Product();
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setQuantity(order.getQuantity());
            order.setTotalPrice(product.getPrice()* order.getQuantity());
            Optional<Product> optionalProduct = productRepository.findById(orderDto.getProductId());
            if (optionalProduct.isPresent()){
                Product product1 = optionalProduct.get();
                order.setProduct(product1);
            }

            Order save = orderRepository.save(order);
            response = new Response(true, "Product updated", save);
        }
        else {
            response = new Response(false,"Order Not Found With Id [" + id + " ]");
        }
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @Override
    public HttpEntity<?> getAll() {
        List<Order> orderList = orderRepository.findAll();

        response = new Response(true, "Product List", orderList);
        return ResponseEntity.ok(response);
    }

    @Override
    public HttpEntity<?> findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            response = new Response(true, "Product List", order);
        }
        else {
            response = new Response(false, "Order Not Found With Id [" + id + " ]");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Override
    public HttpEntity<?> deleteBYId(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            orderRepository.delete(order);
            response = new Response(true, "Order {" + id + "} was successfully deleted");
        }
        else {
            response = new Response(false, "Order was not found with id {" + id + "}");
        }
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
