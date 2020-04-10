package com.breakwater.task.department.controller;

import com.breakwater.task.department.dto.DepartmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface DepartmentApi {

    @GetMapping(value = "/department/readParent/{departmentId}",
        produces = { "application/json" },
        consumes = { "*/*" })
    Mono<ResponseEntity<DepartmentDTO>> readDepartmentWithParent(@PathVariable("departmentId") String departmentId);

    @GetMapping(value = "/department/readChildren/{departmentId}",
        produces = { "application/json" },
        consumes = { "*/*" })
    Mono<ResponseEntity<DepartmentDTO>> readDepartmentWithChildren(@PathVariable("departmentId") String departmentId);

}
