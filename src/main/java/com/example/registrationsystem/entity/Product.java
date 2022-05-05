package com.example.registrationsystem.entity;

import com.example.registrationsystem.entity.template.AbsEntity;
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
@Table(name = "product")
public class Product extends AbsEntity {

    private String name;

    private String description;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    private Attachment attachment;
}
