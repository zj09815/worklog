package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.RoleDAO;
import com.uwntek.worklog.entity.user.Permission;
import com.uwntek.worklog.entity.user.Role;
import com.uwntek.worklog.entity.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RolePermissionService rolePermissionService;

    public List<Role> listWithPermissions() {
        List<Role> roles = roleDAO.findAll();
        List<Permission> permissions;
        for (Role role : roles) {
            permissions = permissionService.listPermissionsByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        return roles;
    }

    public List<Role> findAll() {
        return roleDAO.findAll();
    }

    public void addOrUpdate(Role role) {
        roleDAO.save(role);
    }

    public List<Role> listRolesByUser(Long userId) {
        List<Integer> roleIds = userRoleService.listAllByUserId(userId).stream().map(UserRole::getRoleId)
                .collect(Collectors.toList());
        return roleDAO.findAllById(roleIds);
    }

    public Role updateRoleStatus(Role role) {
        Role roleInDB = roleDAO.findById(role.getId());
        roleInDB.setIsEffective(role.getIsEffective());
        return roleDAO.save(roleInDB);
    }

    public void editRole(@RequestBody Role role) {
        roleDAO.save(role);
        rolePermissionService.savePermissionChanges(role.getId(), role.getPermissions());
    }
}
