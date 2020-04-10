package com.breakwater.task.permission.controller;

import com.breakwater.task.department.dto.DepartmentBaseDTO;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.util.PermissionType;
import com.breakwater.task.user.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

public interface PermissionApi {

    @PostMapping(value = "/permission/save",
        produces = { "application/json" },
        consumes = { "*/*" })
    Mono<ResponseEntity<List<PermissionDTO>>> savePermission(@RequestParam("departmentId") String departmentId,
                                                       @RequestParam("userId") String userId,
                                                       @RequestParam("permissionType") PermissionType permissionType);

    @PostMapping(value = "/permission/user",
        produces = { "application/json" },
        consumes = { "application/json" })
    Mono<ResponseEntity<List<PermissionDTO>>> getUserPermission(@Valid @RequestBody UserDTO userDTO);

    @PostMapping(value = "/permission/department",
        produces = { "application/json" },
        consumes = { "application/json" })
    Mono<ResponseEntity<List<PermissionDTO>>> getDepartmentPermission(@Valid @RequestBody DepartmentBaseDTO departmentBaseDTO);
}
