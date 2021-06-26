package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskCheckDAO;
import com.uwntek.worklog.entity.task.TaskCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCheckService {
    @Autowired
    TaskCheckDAO taskCheckDAO;

    public TaskCheck getTaskCheckById(Long id){
        return taskCheckDAO.findById(id).orElse(null);
    }

    public void addOrUpdate(TaskCheck taskCheck){
        taskCheckDAO.save(taskCheck);
    }

    public boolean existsById(Long id){
        return taskCheckDAO.existsByIdAndIsEffective(id,1);
    }


    public void deleteTaskCheckByTaskId(Long taskId){
        if (getTaskCheckByTaskId(taskId)!=null) {
            TaskCheck taskCheck = getTaskCheckByTaskId(taskId);
            taskCheck.setIsEffective(0);
            addOrUpdate(taskCheck);
        }
    }

    public TaskCheck getTaskCheckByTaskId(Long taskId){
        return taskCheckDAO.getTaskCheckByTaskIdAndIsEffective(taskId,1);
    }
}
