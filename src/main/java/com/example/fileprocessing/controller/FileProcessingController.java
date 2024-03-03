package com.example.fileprocessing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileprocessing.service.FileProcessingService;

@Controller
@RequestMapping("/file")    
public class FileProcessingController {

    @Autowired
    private FileProcessingService fileProcessingService;

    @GetMapping("/list")
    public ResponseEntity<?> getFileList(){
        return new ResponseEntity<>(fileProcessingService.fileList(), HttpStatus.OK);
    }

    @GetMapping(value = "/download/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)  
    public ResponseEntity<?> downloadFile(@PathVariable(value = "name") String fileName){
        Resource file = fileProcessingService.downloadFile(fileName);
        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
        }
        
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam String fileName, @RequestParam(name = "file") MultipartFile file){
        String status = fileProcessingService.uploadFile(fileName, file);
        return "CREATED".equals(status) ? new ResponseEntity<>(HttpStatus.CREATED) : ("EXIST".equals(status) ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED) :new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }

}
