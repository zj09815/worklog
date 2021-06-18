package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptDAO extends JpaRepository<Dept, Integer> {
    List<Dept> findAllByIsEffective(int isEffective);

    List<Dept> findAllByParentIdAndIsEffective(int parentId, int isEffective);

    void deleteByParentId(int parentId);

    boolean existsByParentId(int parentId);
}
