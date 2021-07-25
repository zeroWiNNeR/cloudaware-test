package com.example.cloudawaretest.repo;

import com.example.cloudawaretest.model.entity.ObjectVersionInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectVersionRepo extends MongoRepository<ObjectVersionInfo, String> {

    List<ObjectVersionInfo> findAllByKey(String key);

}
