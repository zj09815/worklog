package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMenuDAO extends JpaRepository<RoleMenu, Integer> {
    List<RoleMenu> findAllByRoleId(int roleId);
    List<RoleMenu> findAllByRoleIdIn(List<Integer> roleIds);
    void deleteAllByRoleId(int roleId);
}
