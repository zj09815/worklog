package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskMidDAO;
import com.uwntek.worklog.entity.task.TaskMid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskMidService {
    @Autowired
    TaskMidDAO taskMidDAO;

    public TaskMid getTaskMidById(Long id){
        return taskMidDAO.findById(id).orElse(null);
    }

    public void addOrUpdate(TaskMid taskMid){
        taskMidDAO.save(taskMid);
    }

    public boolean existsById(Long id){
        return taskMidDAO.existsByIdAndIsEffective(id,1);
    }

    public void  deleteTaskMidById(Long id){
        TaskMid taskMid = getTaskMidById(id);
        if (taskMid != null){
            taskMid.setIsEffective(0);
            addOrUpdate(taskMid);
        }
    }

    public void deleteTaskMidsByTaskId(Long taskId){
        List<TaskMid> taskMids = taskMidDAO.getTaskMidByTaskIdAndIsEffective(taskId,1);
        for(TaskMid taskMid:taskMids){
            taskMid.setIsEffective(0);
            addOrUpdate(taskMid);
        }
    }

    public List<TaskMid> getTaskMidByTaskId(Long taskId){
        return taskMidDAO.getTaskMidByTaskIdAndIsEffective(taskId,1);
    }

    public TaskMid getTaskMidByTaskIdAndMidId(Long taskId, int taskMidId){
        return taskMidDAO.getTaskMidByTaskIdAndTaskMidIdAndIsEffective(taskId,taskMidId,1);
    }
}
