package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.PlaceAttachment;
import com.sushchenko.mystictourismapp.entity.id.PlaceAttachmentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAttachmentRepo extends JpaRepository<PlaceAttachment, PlaceAttachmentKey> {
}
