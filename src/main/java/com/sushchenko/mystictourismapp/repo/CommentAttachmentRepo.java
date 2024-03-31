package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.CommentAttachment;
import com.sushchenko.mystictourismapp.entity.id.CommentAttachmentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentAttachmentRepo extends JpaRepository<CommentAttachment, CommentAttachmentKey> {
}
