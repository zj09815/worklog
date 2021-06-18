package com.uwntek.worklog.dao.task;

import com.uwntek.worklog.entity.task.TaskCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCheckDAO extends JpaRepository<TaskCheck,Long> {
    TaskCheck getTaskCheckByTaskIdAndIsEffective(Long taskId,int isEffective);
}
