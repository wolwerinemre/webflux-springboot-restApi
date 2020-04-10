package com.breakwater.task.department.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper=true)
public class DepartmentDTO extends DepartmentBaseDTO{

    DepartmentDTO       parent;
    List<DepartmentDTO> children;

    public DepartmentDTO(UUID id, String name, DepartmentDTO parent, List<DepartmentDTO> children) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.children = children;
    }
}
