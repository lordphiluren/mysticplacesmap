package com.sushchenko.mystictourismapp.services;

import com.ibm.icu.text.Transliterator;
import com.sushchenko.mystictourismapp.entities.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Transliterator transliterator;
    // Save received files to specified path and return their urls
    public List<String> saveFiles(MultipartFile[] files, String path) throws IOException{
        List<String> fileUrls = new ArrayList<>();
        for(MultipartFile file : files) {
            file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
            fileUrls.add(path + File.separator + file.getOriginalFilename());
        }
        return fileUrls;
    }
    public String createDirectory(String name) {
        // Change cyrillic name to latin one
        String engName = transliterator.transliterate(name);
        File dir = new File("upload/images/" + engName);
        if(!dir.exists())
            dir.mkdir();
        return dir.getAbsolutePath();
    }
}
