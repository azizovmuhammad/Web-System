package com.example.registrationsystem.repository;

import com.example.registrationsystem.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findByHashId(String hashid);
}
