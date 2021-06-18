package com.uwntek.worklog.dao.task;

import com.uwntek.worklog.entity.task.TaskStart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStartDAO extends JpaRepository<TaskStart, Long> {
    boolean existsByTaskIdAndIsEffective(Long taskId, int isEffective);
    TaskStart getTaskStartByTaskIdAndIsEffective(Long taskId, int isEffective);
}
