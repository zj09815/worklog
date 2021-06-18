package com.uwntek.worklog.dao.task;

import com.uwntek.worklog.entity.task.TaskMid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskMidDAO extends JpaRepository<TaskMid, Long> {
    List<TaskMid> getTaskMidByTaskIdAndIsEffective(Long taskId, int isEffective);
    TaskMid getTaskMidByTaskIdAndTaskMidIdAndIsEffective(Long taskId, int taskMidId, int isEffective);

}