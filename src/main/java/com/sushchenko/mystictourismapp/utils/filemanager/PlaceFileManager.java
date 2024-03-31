//package com.sushchenko.mystictourismapp.utils.filemanager;
//
//import com.sushchenko.mystictourismapp.entity.Place;
//import com.sushchenko.mystictourismapp.utils.exception.FilesNotSavedException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//@Component
//@RequiredArgsConstructor
//public class PlaceFileManager implements FileManager {
//    private final PathProperties paths;
//    @Override
//    public List<String> saveFiles(MultipartFile[] files, String path) {
//        List<String> fileUrls = new ArrayList<>();
//        try {
//            for (MultipartFile file : files) {
//                file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
//                fileUrls.add(path + File.separator + file.getOriginalFilename());
//            }
//
//        } catch (IOException e) {
//            throw new FilesNotSavedException(e.getMessage());
//        }
//        return fileUrls;
//    }
//
//    @Override
//    public String saveFile(MultipartFile file, String path) {
//        String fileUrl;
//        try {
//            file.transferTo(new File(path + File.separator + file.getOriginalFilename()));
//            fileUrl = path + File.separator + file.getOriginalFilename();
//        } catch (IOException e) {
//            throw new FilesNotSavedException(e.getMessage());
//        }
//        return fileUrl;
//    }
//
//    @Override
//    public String createDirectory(String name) {
//        File dir = new File(paths.getPlaceAttachmentsPath() + name);
//        if(!dir.exists())
//            dir.mkdirs();
//        return dir.getAbsolutePath();
//    }
//
//    @Override
//    public void deleteFile(String path) {
//        File file = new File(path);
//        file.delete();
//    }
//
//    public void deletePlaceAttachments(Place place) {
//        place.getAttachments().forEach(att -> deleteFile(att.getUrl()));
//    }
//}
