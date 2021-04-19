package com.cloud.authservice.repository;

import com.cloud.authservice.entity.Errors;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ErrorsRepository extends MongoRepository<Errors, String> {
    List<Errors> findAllByUserId(String userId);
}
