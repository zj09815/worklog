package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionDAO extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findAllByRoleIdAndIsEffective(int roleId, int isEffective);

    List<RolePermission> findAllByRoleIdAndIsEffective(List<Integer> roleIds, int isEffective);

    void deleteAllByRoleId(int roleId);
}
