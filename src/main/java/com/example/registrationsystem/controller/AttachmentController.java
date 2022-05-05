package com.example.registrationsystem.controller;

import com.example.registrationsystem.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RequestMapping("/api/v1/attachment")
@RestController
@RequiredArgsConstructor
public class AttachmentController {

    @Value("${upload.server.folder}")
    private String serverFolderPath;
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile){
        return attachmentService.save(multipartFile);
    }

    @GetMapping("/file-preview/{hashid}")
    public ResponseEntity<?> preview(@PathVariable String hashid) throws MalformedURLException {
        return attachmentService.findByHashId(hashid);
    }

    @GetMapping("/download/{hashid}")
    public ResponseEntity<?> download(@PathVariable String hashid) throws MalformedURLException {
        return attachmentService.downloadByHashId(hashid);
    }

    @DeleteMapping("/delete/{hashid}")
    public ResponseEntity<?> delete(@PathVariable String hashid){
        return attachmentService.deleteByHashId(hashid);
    }
}


