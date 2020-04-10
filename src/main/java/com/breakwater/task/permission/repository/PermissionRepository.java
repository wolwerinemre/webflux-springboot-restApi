package com.breakwater.task.permission.repository;

import com.breakwater.task.permission.model.Permission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PermissionRepository extends ReactiveMongoRepository<Permission, ObjectId> {

    Flux<Permission> findByUserId(UUID userId);

    Flux<Permission> findByDepartmentId(UUID deparmentId);

    Mono<Permission> findByUserIdAndDepartmentId (UUID userId, UUID departmentId);
}
