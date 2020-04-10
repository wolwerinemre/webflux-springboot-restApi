package com.breakwater.task.permission.dto;

import com.breakwater.task.department.dto.DepartmentDTO;
import com.breakwater.task.permission.util.AllocationType;
import com.breakwater.task.permission.util.PermissionType;
import com.breakwater.task.user.dto.UserDTO;
import lombok.Value;

@Value
public class PermissionDTO {

    DepartmentDTO department;

    UserDTO user;

    AllocationType allocationType;

    PermissionType permissionType;
}
