package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.RolePermissionDAO;
import com.uwntek.worklog.entity.user.Permission;
import com.uwntek.worklog.entity.user.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionService {
    @Autowired
    RolePermissionDAO rolePermissionDAO;

    List<RolePermission> findAllByRoleId(int roleId) {
        return rolePermissionDAO.findAllByRoleIdAndIsEffective(roleId, 1);
    }

    @Transactional
    public void savePermissionChanges(int roleId, List<Permission> permissions) {
        rolePermissionDAO.deleteAllByRoleId(roleId);
        List<RolePermission> rolePermissions = new ArrayList<>();
        permissions.forEach(permission -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permission.getId());
            rolePermissions.add(rolePermission);
        });
        rolePermissionDAO.saveAll(rolePermissions);
    }
}
