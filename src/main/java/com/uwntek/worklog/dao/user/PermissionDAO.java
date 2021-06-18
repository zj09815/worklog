package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDAO extends JpaRepository<Permission, Integer> {
}
