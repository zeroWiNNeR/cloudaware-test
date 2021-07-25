package com.example.cloudawaretest.service;

import com.example.cloudawaretest.model.entity.ObjectInfo;
import com.example.cloudawaretest.model.entity.ObjectVersionInfo;
import com.example.cloudawaretest.repo.ObjectInfoRepo;
import com.example.cloudawaretest.repo.ObjectVersionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final S3Client s3Client;
    private final ObjectInfoRepo objectInfoRepo;
    private final ObjectVersionRepo objectVersionRepo;

    @Autowired
    public StorageService(S3Client s3Client, ObjectInfoRepo objectInfoRepo, ObjectVersionRepo objectVersionRepo) {
        this.s3Client = s3Client;
        this.objectInfoRepo = objectInfoRepo;
        this.objectVersionRepo = objectVersionRepo;
    }

    public List<ObjectInfo> getAllObjects() {
        return objectInfoRepo.findAll();
    }

    public List<ObjectVersionInfo> getObjectVersions(String key) {
        return objectVersionRepo.findAllByKey(key);
    }

    public String uploadObject(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File fileToUpload = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(fileToUpload)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Can't convert multipart to file", e);
        }

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromFile(fileToUpload));

        fileToUpload.delete();

        return "File was uploaded: " + fileName;
    }

    public byte[] downloadObject(String fileName) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .build();

        return s3Client.getObjectAsBytes(objectRequest).asByteArray();
    }

    public String deleteObject(String fileName) {
        DeleteObjectRequest objectRequest = DeleteObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .build();

        s3Client.deleteObject(objectRequest);

        return "File was removed: " + fileName;
    }


    private static long calKb(Long val) {
        return val / 1024;
    }

}
