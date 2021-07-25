package com.example.cloudawaretest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.awssdk.services.s3.model.ObjectVersion;

import java.util.Date;

@Document(collection = "object_version_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectVersionInfo {

    @Id
    private String versionId;

    @Indexed
    private String key;

    private String eTag;

    private Date lastModified;

    private Long size;

    private boolean isLatest;

    private String storageClass;

    private String ownerId;


    public ObjectVersionInfo(ObjectVersion version) {
        this.versionId = version.versionId();
        this.key = version.key();
        this.eTag = version.eTag();
        this.lastModified = version.lastModified() == null ? null : Date.from(version.lastModified());
        this.size = version.size();
        this.isLatest = version.isLatest();
        this.storageClass = version.storageClassAsString();
        this.ownerId = version.owner().id();
    }

}
