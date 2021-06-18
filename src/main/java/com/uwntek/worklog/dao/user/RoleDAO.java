package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDAO extends JpaRepository<Role, Integer> {
    List<Role> findAllById(int id);

    Role findById(int id);
}
