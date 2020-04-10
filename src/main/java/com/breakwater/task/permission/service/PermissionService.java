package com.breakwater.task.permission.service;

import com.breakwater.task.department.dto.DepartmentDTO;
import com.breakwater.task.department.model.Department;
import com.breakwater.task.department.repository.DepartmentRepository;
import com.breakwater.task.department.service.DepartmentService;
import com.breakwater.task.permission.dto.PermissionDTO;
import com.breakwater.task.permission.model.Permission;
import com.breakwater.task.permission.repository.PermissionRepository;
import com.breakwater.task.permission.util.AllocationType;
import com.breakwater.task.permission.util.ConvertUtil;
import com.breakwater.task.user.dto.UserDTO;
import com.breakwater.task.user.model.User;
import com.breakwater.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PermissionService {

    @Autowired
    private final PermissionRepository permissionRepository;

    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final UserRepository userRepository;

    public Mono<List<PermissionDTO>> save(Permission permissionNew) {
        return checkExistsAndUpdate(permissionNew)
                .flatMap(permissionRepository::save)
                .flatMapMany(permissionDb -> findChildAndConvertPermission(permissionDb,permissionNew))
                .flatMap(this::save)
                .flatMap(this::convertToUser)
                .flatMap(this::getUserPermissions)
                .collectList();
    }

    public Flux<PermissionDTO> getUserPermissions(User user) {
        return userRepository.findOne(Example.of(user))
                .flatMapMany(userDB -> permissionRepository.findByUserId(userDB.getId()))
                .flatMap(this::getUser)
                .flatMap(permissionDTO -> getDepartment(permissionDTO.getDepartment().getId(),permissionDTO));
    }

    public Mono<List<PermissionDTO>> getDepartmentPermissions(Department department) {
        return departmentRepository.findOne(Example.of(department))
                .flatMapMany(departmentDTO -> permissionRepository.findByDepartmentId(departmentDTO.getId()))
                .flatMap(this::getUser)
                .flatMap(permissionDTO -> getDepartment(permissionDTO.getDepartment().getId(),permissionDTO))
                .collectList();
    }

    private Flux<Permission> findChildAndConvertPermission(Permission permissionDb, Permission permissionNew) {
        return departmentRepository.findByParentId(permissionDb.getDepartmentId())
                .map(department -> new Permission(null,department.getId(),permissionNew.getUserId(), AllocationType.INHERITED, permissionNew.getPermissionType()));
    }

    private Flux<User> convertToUser(List<PermissionDTO> permissionDTOS) {
        return Flux.fromStream(permissionDTOS.stream()
                .map(p -> new User(p.getUser().getId(),p.getUser().getNickname())));
    }

    private Mono<PermissionDTO> getUser(Permission permission) {
        return Mono.justOrEmpty(permission.getUserId())
                .flatMap(userRepository::findById)
                .map(ConvertUtil::convertToUserDTO)
                .map(userDTO -> createPermissionDTOWithDepartmentDTO(permission, userDTO))
                .switchIfEmpty(Mono.just(createSimplePermissionDTO(permission)));
    }

    private Mono<PermissionDTO> getDepartment(UUID departmentId, PermissionDTO permissionDTO) {
        return Mono.justOrEmpty(departmentId)
                .flatMap(departmentService::readDepartmentWithChildren)
                .map(departmentDTO -> addDepartment(permissionDTO,departmentDTO))
                .switchIfEmpty(Mono.just(permissionDTO));
    }

    private Mono<Permission> checkExistsAndUpdate(Permission permission) {
        return permissionRepository.findByUserIdAndDepartmentId(permission.getUserId(),permission.getDepartmentId())
                .map(permissionDB -> new Permission(permissionDB.getId(),permissionDB.getDepartmentId(),permissionDB.getUserId(),permission.getAllocationType(),permission.getPermissionType()))
                .switchIfEmpty(Mono.just(permission));
    }

    private PermissionDTO addDepartment(PermissionDTO permissionDTO, DepartmentDTO departmentDTO) {
        return new PermissionDTO(departmentDTO,permissionDTO.getUser(),permissionDTO.getAllocationType(), permissionDTO.getPermissionType());
    }

    private PermissionDTO createSimplePermissionDTO(Permission permission) {
        return new PermissionDTO(null,null,permission.getAllocationType(),permission.getPermissionType());
    }

    private PermissionDTO createPermissionDTOWithDepartmentDTO(Permission permission, UserDTO userDTO) {
        return new PermissionDTO(new DepartmentDTO(permission.getDepartmentId(), null,null,null),userDTO,permission.getAllocationType(),permission.getPermissionType());
    }
}
