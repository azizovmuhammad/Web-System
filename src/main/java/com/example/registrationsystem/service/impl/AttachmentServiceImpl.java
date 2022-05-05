package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.entity.Attachment;
import com.example.registrationsystem.enums.AttachmentStatus;
import com.example.registrationsystem.repository.AttachmentRepository;
import com.example.registrationsystem.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Value("${upload.server.folder}")
    private String serverFolderPath;

    private final Hashids hashids;

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    @Override
    public ResponseEntity<?> save(MultipartFile multipartFile){

        Attachment attachment = new Attachment();
        attachment.setName(multipartFile.getOriginalFilename());
        attachment.setFileSize(multipartFile.getSize());
        attachment.setContentType(multipartFile.getContentType());
        attachment.setExtension(getExt(multipartFile.getOriginalFilename()));
        attachment.setFileStorageStatus(AttachmentStatus.DRAFT);

        attachment = attachmentRepository.save(attachment);

        // /serverFolderPath/upload_folder/2002/02/20/hash.pdf

        Date now = new Date();

        // 1 -----> the first way - bunday holatda ham path ni yozishimiz mumkin
       /* File uploadFolder = new File(this.serverFolderPath + "/upload_files" +
                1900 + now.getYear() + "/" +
                1 + now.getMonth() + "/" +
                now.getDate());*/

        // 2 -----> the second way - string formatter orqali ham yozishimiz mumkin
        String path = String.format("%s/upload_files/%d/%d/%d",
                this.serverFolderPath,
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate()
        );

        File uploadFolder = new File(path);
        if (!uploadFolder.exists() && uploadFolder.mkdirs()){
            System.out.println("file created");
        }

        attachment.setHashId(hashids.encode(attachment.getId()));
        String pathLocal = String.format("/upload_files/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                attachment.getHashId(),
                attachment.getExtension()
        );

        attachment.setUploadFolder(pathLocal);

        attachmentRepository.save(attachment);

        uploadFolder = uploadFolder.getAbsoluteFile();
        File uploadFile = new File(uploadFolder, String.format("%s.%s", attachment.getHashId(), attachment.getExtension()));

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(attachment);
    }

    @Override
    public ResponseEntity<?> findByHashId(String hashid) throws MalformedURLException {
        Attachment fileStorage = attachmentRepository.findByHashId(hashid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename+\""+ UriEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder())));
    }

    @Override
    public ResponseEntity<?> downloadByHashId(String hashid) throws MalformedURLException {
        Attachment fileStorage = attachmentRepository.findByHashId(hashid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename+\""+ UriEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder())));
    }

    @Override
    public ResponseEntity<?> deleteByHashId(String hashid) {
        Attachment fileStorage = attachmentRepository.findByHashId(hashid);
        File file = new File(String.format("%s/%s", this.serverFolderPath, fileStorage.getUploadFolder()));
        if (file.delete()){
            attachmentRepository.delete(fileStorage);
        }
        return ResponseEntity.ok("Successfully deleted");
    }

    private String getExt(String fileName){
        // photo.jpg
        String ext = null;

        if (fileName != null && !fileName.isEmpty()){
            int dot = fileName.lastIndexOf(".");
            if (dot>0 && dot <= fileName.length()-2){
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
