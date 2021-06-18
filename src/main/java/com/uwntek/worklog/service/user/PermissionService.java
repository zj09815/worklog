package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.PermissionDAO;
import com.uwntek.worklog.dao.user.RolePermissionDAO;
import com.uwntek.worklog.entity.user.Permission;
import com.uwntek.worklog.entity.user.Role;
import com.uwntek.worklog.entity.user.RolePermission;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    @Autowired
    PermissionDAO permissionDAO;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    RolePermissionDAO rolePermissionDAO;

    public List<Permission> list() {
        return permissionDAO.findAll();
    }

    public boolean needFilter(String requestAPI) {
        List<Permission> permissions = permissionDAO.findAll();
        for (Permission permission : permissions) {
            if (requestAPI.startsWith(permission.getUrl())) {
                return true;
            }
        }
        return false;
    }

    public List<Permission> listPermissionsByRoleId(int roleId) {
        List<Integer> permissionIds = rolePermissionService.findAllByRoleId(roleId)
                .stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        return permissionDAO.findAllById(permissionIds);
    }

    public Set<String> listPermissionURLsByUser(String username) {
        User user = userService.findByUserName(username);
        Long userId = user.getId();
        List<Integer> roleIds = roleService.listRolesByUser(userId)
                .stream().map(Role::getId).collect(Collectors.toList());
        List<Integer> permissionIds = rolePermissionDAO.findAllByRoleIdAndIsEffective(roleIds, 1)
                .stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Permission> permissions = permissionDAO.findAllById(permissionIds);

        return permissions.stream().map(Permission::getUrl).collect(Collectors.toSet());
    }


}
