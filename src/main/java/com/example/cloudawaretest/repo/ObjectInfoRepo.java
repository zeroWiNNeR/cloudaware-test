package com.example.cloudawaretest.repo;

import com.example.cloudawaretest.model.entity.ObjectInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectInfoRepo extends MongoRepository<ObjectInfo, String> {
}
