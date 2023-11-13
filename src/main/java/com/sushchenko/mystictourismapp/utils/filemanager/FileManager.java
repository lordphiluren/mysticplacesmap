package com.sushchenko.mystictourismapp.utils.filemanager;

import com.ibm.icu.text.Transliterator;
import com.sushchenko.mystictourismapp.utils.exceptions.FilesNotSavedException;
import com.sushchenko.mystictourismapp.utils.exceptions.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileManager {
    private final PathProperties paths;
    private final Transliterator transliterator;
    // Save received files to specified path and return their urls
    public List<String> saveFiles(MultipartFile[] files, String path) {
        List<String> fileUrls = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
                fileUrls.add(path + File.separator + file.getOriginalFilename());
            }

        } catch (IOException e) {
            throw new FilesNotSavedException(e.getMessage());
        }
        return fileUrls;
    }
    public String saveFile(MultipartFile file, String path) {
        String fileUrl;
        try {
                file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
                fileUrl = path + File.separator + file.getOriginalFilename();
        } catch (IOException e) {
            throw new FilesNotSavedException(e.getMessage());
        }
        return fileUrl;
    }
    public String createPlacesDirectory(String name) {
        String engName = transliterator.transliterate(name);
        File dir = new File(paths.getPlaceAttachmentsPath() + engName);
        if(!dir.exists())
            dir.mkdirs();
        return dir.getAbsolutePath();
    }
    public String createCommentsDirectory(String placeId) {
        File dir = new File(paths.getCommentAttachmentsPath() + placeId);
        if(!dir.exists())
            dir.mkdirs();
        return dir.getAbsolutePath();
    }
    public String createUsersDirectory(String id) {
        File dir = new File(paths.getUserAttachmentsPath() + id);
        if(!dir.exists())
            dir.mkdirs();
        return dir.getAbsolutePath();
    }
}
