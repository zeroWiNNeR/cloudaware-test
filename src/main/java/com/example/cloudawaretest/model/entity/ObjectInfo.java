package com.example.cloudawaretest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.Date;

@Document(collection = "object_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectInfo {

    @Id
    @Field("_id")
    private String id;

    private String key;

    private String eTag;

    private Date lastModified;

    private Long size;

    private String storageClass;

    private String ownerId;


    public ObjectInfo(S3Object s3Object) {
        this.key = s3Object.key();
        this.eTag = s3Object.eTag();
        this.lastModified = s3Object.lastModified() == null ? null : Date.from(s3Object.lastModified());
        this.size = s3Object.size();
        this.storageClass = s3Object.storageClassAsString();
        this.ownerId = s3Object.owner().id();
    }

}
