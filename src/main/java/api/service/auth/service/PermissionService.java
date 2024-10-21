package api.service.auth.service;


import api.service.auth.entity.Permission;
import api.service.auth.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> findPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
