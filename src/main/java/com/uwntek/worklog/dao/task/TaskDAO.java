package com.uwntek.worklog.dao.task;

import com.uwntek.worklog.entity.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDAO extends JpaRepository<Task,Long> {
    Page<Task> findAllByIsEffective(int isEffective, Pageable pageable);
    Page<Task> findAllByTaskMainPersonAndIsEffective(Long personId, int isEffective, Pageable pageable);
    Page<Task> findAllByTaskDeptAndIsEffective(int deptId, int isEffective, Pageable pageable);
    Task getTaskByIdAndIsEffective(Long id, int isEffective);

    boolean existsByIdAndIsEffective(Long id,int isEffective);

    List<Task> findAllByIdIn(List<Long> ids);

    Page<Task> findAll(Specification<Task> taskSpecification, Pageable pageable);
}
