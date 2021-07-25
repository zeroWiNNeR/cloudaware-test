package com.example.cloudawaretest.controller;

import com.example.cloudawaretest.model.entity.ObjectInfo;
import com.example.cloudawaretest.model.entity.ObjectVersionInfo;
import com.example.cloudawaretest.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/objects")
    public List<ObjectInfo> getObjects() {
        return storageService.getAllObjects();
    }

    @GetMapping("/versions/{key}")
    public List<ObjectVersionInfo> getFiles(@PathVariable String key) {
        return storageService.getObjectVersions(key);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadObject(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(storageService.uploadObject(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadObject(@PathVariable String fileName) {
        byte[] data = storageService.downloadObject(fileName);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-Type", "application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteObject(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteObject(fileName), HttpStatus.OK);
    }

}
