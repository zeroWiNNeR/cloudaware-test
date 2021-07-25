package com.example.cloudawaretest.controller;

import com.example.cloudawaretest.model.entity.ObjectInfo;
import com.example.cloudawaretest.model.entity.ObjectVersionInfo;
import com.example.cloudawaretest.repo.ObjectInfoRepo;
import com.example.cloudawaretest.repo.ObjectVersionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectInfoUpdater {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final ObjectInfoRepo objectInfoRepo;
    private final ObjectVersionRepo objectVersionRepo;
    private final S3Client s3Client;

    @Autowired
    public ObjectInfoUpdater(ObjectInfoRepo objectInfoRepo, ObjectVersionRepo objectVersionRepo, S3Client s3Client) {
        this.objectInfoRepo = objectInfoRepo;
        this.objectVersionRepo = objectVersionRepo;
        this.s3Client = s3Client;
    }

    @Scheduled(fixedDelay = 3600000)
    public void update() {
        objectInfoRepo.deleteAll();
        objectVersionRepo.deleteAll();

        List<S3Object> objects = getObjects();
        for (S3Object object : objects) {
            List<ObjectVersion> objectVersions = getVersions(object.key());
            objectInfoRepo.save(new ObjectInfo(object));
            objectVersionRepo.saveAll(getVersionsToSave(objectVersions));
        }

    }


    private List<S3Object> getObjects() {
        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();

        ListObjectsResponse res = s3Client.listObjects(listObjects);
        return res.contents();
    }

    private List<ObjectVersion> getVersions(String key) {
        ListObjectVersionsRequest request = ListObjectVersionsRequest.builder()
                .bucket(bucketName)
                .keyMarker(key)
                .build();

        ListObjectVersionsResponse res = s3Client.listObjectVersions(request);

        return res.versions();
    }

    private List<ObjectVersionInfo> getVersionsToSave(List<ObjectVersion> versions) {
        List<ObjectVersionInfo> versionsToSave = new ArrayList<>();
        for (ObjectVersion version : versions) {
            versionsToSave.add(new ObjectVersionInfo(version));
        }

        return versionsToSave;
    }

}
