package com.cloud.authservice.repository;

import com.cloud.authservice.entity.Errors;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ErrorsRepository extends CrudRepository<Errors, Long> {
    List<Errors> findAllByUserId(Long userId);
}
