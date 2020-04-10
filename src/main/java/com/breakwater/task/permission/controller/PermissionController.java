package com.breakwater.task.permission.controller;

import com.breakwater.task.department.dto.DepartmentBaseDTO;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.service.PermissionService;
import com.breakwater.task.permission.util.ConvertUtil;
import com.breakwater.task.permission.util.PermissionType;
import com.breakwater.task.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class PermissionController implements PermissionApi{

    private final PermissionService permissionService;

    @Autowired
    public PermissionController (PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public Mono<ResponseEntity<List<PermissionDTO>>> savePermission(@RequestParam String departmentId, @RequestParam String userId, @RequestParam PermissionType permissionType) {
        return permissionService.save(ConvertUtil.covertToPermission(departmentId,userId,permissionType))
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<List<PermissionDTO>>> getUserPermission(@RequestBody UserDTO userDTO) {
        return permissionService.getUserPermissions(ConvertUtil.covertToUser(userDTO))
                .collectList()
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<List<PermissionDTO>>> getDepartmentPermission(@RequestBody DepartmentBaseDTO departmentBaseDTO) {
        return permissionService.getDepartmentPermissions(ConvertUtil.converToDepartment(departmentBaseDTO))
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
