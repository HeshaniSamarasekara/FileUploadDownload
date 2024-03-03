package com.example.fileprocessing.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingService {
    List<String> fileList();
    String uploadFile(String fileName, MultipartFile file);
    Resource downloadFile(String fileName);
}
