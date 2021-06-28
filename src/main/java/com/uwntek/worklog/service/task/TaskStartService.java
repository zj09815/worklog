package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskStartDAO;
import com.uwntek.worklog.entity.task.TaskStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStartService {
    @Autowired
    TaskStartDAO taskStartDAO;


    public TaskStart getTaskStartById(Long id){
        return taskStartDAO.findById(id).orElse(null);
    }

    public void addOrUpdate(TaskStart taskStart){
        if (existsByTaskId(taskStart.getTaskId())){
            taskStart.setId(getTaskStartByTaskId(taskStart.getTaskId()).getId());
        }
        taskStartDAO.save(taskStart);
    }

    public boolean existsById(Long id){
        return taskStartDAO.existsByIdAndIsEffective(id,1);
    }

    public void  deleteTaskStartByTaskId(Long taskId){
        if (getTaskStartByTaskId(taskId) != null){
            TaskStart taskStart = getTaskStartByTaskId(taskId);
            taskStart.setIsEffective(0);
            addOrUpdate(taskStart);
        }
    }

    public TaskStart getTaskStartByTaskId(Long taskId){
        return taskStartDAO.getTaskStartByTaskIdAndIsEffective(taskId,1);
    }

    public boolean existsByTaskId(Long taskId) {
        return taskStartDAO.existsByTaskIdAndIsEffective(taskId,1);
    }
}
