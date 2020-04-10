package com.breakwater.task.department.controller;

import com.breakwater.task.department.dto.DepartmentDTO;
import com.breakwater.task.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class DepartmentController implements DepartmentApi {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @Override
    public Mono<ResponseEntity<DepartmentDTO>> readDepartmentWithChildren(@PathVariable String departmentId){
        return departmentService.readDepartmentWithChildren(UUID.fromString(departmentId))
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<DepartmentDTO>> readDepartmentWithParent(@PathVariable String departmentId){
        return departmentService.readDepartmentWithParents(UUID.fromString(departmentId))
                .map(dto -> ResponseEntity.ok(dto))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
