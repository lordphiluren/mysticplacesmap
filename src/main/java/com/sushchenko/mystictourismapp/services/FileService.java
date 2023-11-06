package com.sushchenko.mystictourismapp.services;

import com.ibm.icu.text.Transliterator;
import jakarta.el.ELException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        File dir = new File("src/main/resources/images/" + engName);
        if(!dir.exists())
            dir.mkdir();
        return dir.getAbsolutePath();
    }
}
