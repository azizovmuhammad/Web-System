package com.example.registrationsystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

public interface AttachmentService {

    ResponseEntity<?> save(MultipartFile multipartFile);

    ResponseEntity<?> findByHashId(String hashid) throws MalformedURLException;

    ResponseEntity<?> downloadByHashId(String hashid) throws MalformedURLException;

    ResponseEntity<?> deleteByHashId(String hashid);
}
