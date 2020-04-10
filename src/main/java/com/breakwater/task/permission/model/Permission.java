package com.breakwater.task.permission.model;

import com.breakwater.task.permission.util.AllocationType;
import com.breakwater.task.permission.util.PermissionType;
import lombok.Value;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Value
@Document
public class Permission {

    @Id
    ObjectId id;

    UUID departmentId;

    UUID userId;

    AllocationType allocationType;

    PermissionType permissionType;
}
