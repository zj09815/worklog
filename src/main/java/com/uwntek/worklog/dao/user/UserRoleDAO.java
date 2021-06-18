package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
    List<UserRole> findAllByUserIdAndIsEffective(Long userId, int isEffective);

    void deleteAllByUserId(Long userId);
}
