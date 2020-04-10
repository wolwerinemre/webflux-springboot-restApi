package com.breakwater.task.permission.util;

import com.breakwater.task.department.dto.DepartmentBaseDTO;
import com.breakwater.task.department.model.Department;
import com.breakwater.task.permission.model.Permission;
import com.breakwater.task.user.dto.UserDTO;
import com.breakwater.task.user.model.User;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;

import java.util.UUID;

@UtilityClass
public class ConvertUtil {

    public static UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getNickname());
    }

    public static Permission covertToPermission(String departmentId, String userId, PermissionType permissionType){
        return new Permission(ObjectId.get(), UUID.fromString(departmentId), UUID.fromString(userId), AllocationType.ASSIGNED, permissionType);
    }

    public static User covertToUser(UserDTO userDTO){
        return new User(userDTO.getId(),userDTO.getNickname());
    }

    public static Department converToDepartment (DepartmentBaseDTO departmentBaseDTO) {
        return new Department(departmentBaseDTO.getId(),departmentBaseDTO.getName(),null);
    }
}
