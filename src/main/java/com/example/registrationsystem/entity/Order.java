package com.example.registrationsystem.entity;

import com.example.registrationsystem.entity.template.AbsEntity;
import com.example.registrationsystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AbsEntity {

    @OneToOne
    private Product product;

    private Integer quantity;

    private Double totalPrice;

    /*@Column(nullable = false)
    private OrderStatus status;*/

}
