package com.sushchenko.mystictourismapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final ObjectStorageService objectStorageService;
    public Set<String> uploadAttachments(List<MultipartFile> attachments) {
        List<byte[]> attachmentsBytes = new ArrayList<>();
        for(MultipartFile att : attachments) {
            try {
                attachmentsBytes.add(att.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return objectStorageService.uploadAttachments(attachmentsBytes);
    }
    public String uploadAttachment(MultipartFile attachment) {
        byte[] attachmentsBytes;
        try {
            attachmentsBytes = attachment.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objectStorageService.uploadAttachment(attachmentsBytes);
    }
}
