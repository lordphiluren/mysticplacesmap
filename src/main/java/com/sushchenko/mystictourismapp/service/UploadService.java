package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final ObjectStorageService objectStorageService;
    public Set<Attachment> uploadAttachments(List<MultipartFile> attachments) {
        List<byte[]> attachmentsBytes = new ArrayList<>();
        for(MultipartFile att : attachments) {
            try {
                attachmentsBytes.add(att.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Set<String> urls = objectStorageService.uploadAttachments(attachmentsBytes);
        return urls.stream().map(Attachment::new).collect(Collectors.toSet());
    }
}
