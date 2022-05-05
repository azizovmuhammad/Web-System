package com.example.registrationsystem.entity;

import com.example.registrationsystem.enums.AttachmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Attachment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String extension;

    private Long fileSize;

    private String contentType;

    private String hashId;

    private String uploadFolder;

    private AttachmentStatus fileStorageStatus;
}
