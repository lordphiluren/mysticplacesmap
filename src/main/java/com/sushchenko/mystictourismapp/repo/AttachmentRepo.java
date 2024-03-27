package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}
