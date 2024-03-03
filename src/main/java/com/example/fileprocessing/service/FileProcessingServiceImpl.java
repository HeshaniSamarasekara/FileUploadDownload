package com.example.fileprocessing.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    @Value("${filePath}")
    private String basePath;

    @Override
    public List<String> fileList() {
        File dir = new File(basePath);
        File[] files = dir.listFiles();
    
        return files != null ? Arrays.stream(files).map(i -> i.getName()).collect(Collectors.toList()) : null;
    }

    @Override
    public String uploadFile(String fileName, MultipartFile multipartFile) {
        File dir = new File(basePath+fileName);

        if(dir.exists()){
            return "EXIST";
        }

        Path path = Path.of(basePath+fileName);

        try{
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "CREATED";
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "FAILED";
    }   

    @Override
    public Resource downloadFile(String fileName) {
        File dir = new File(basePath+fileName);
        try{
        if(dir.exists()){
            Resource resource = new UrlResource(dir.toURI());
            return resource;
        } 
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
    
}
