package com.uwntek.worklog.service.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.dao.task.TaskDAO;
import com.uwntek.worklog.entity.task.Task;
import com.uwntek.worklog.entity.task.TaskInfo;
import com.uwntek.worklog.entity.task.TaskUserPermission;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    TaskUserPermissionService taskUserPermissionService;
    @Autowired
    UserService userService;

    @Autowired
    TaskDAO taskDAO;


    public Page<Task> getAllTask(int pagenum){
        Pageable pageable = PageRequest.of(pagenum,20, Sort.by("taskMainPerson","taskName"));
        return taskDAO.findAllByIsEffective(1,pageable);
    }

    public Page<Task> getAllTaskByUser(Long userid, int pagenum){
        Pageable pageable = PageRequest.of(pagenum,20,Sort.by("taskName"));
        return taskDAO.findAllByTaskMainPersonAndIsEffective(userid,1,pageable);
    }

    public boolean existsById(Long id){
        return taskDAO.existsByIdAndIsEffective(id,1);
    }

    public TaskInfo getTaskInfoById(Long id){
        Task task = taskDAO.getTaskByIdAndIsEffective(id,1);
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(task.getId());
        taskInfo.setTaskDept(task.getTaskDept());
        taskInfo.setTaskName(task.getTaskName());
        taskInfo.setTaskMainPerson(task.getTaskMainPerson());
        taskInfo.setIsEffective(task.getIsEffective());
        taskInfo.setProcessId(task.getProcessId());
        taskInfo.setTaskMainPersonNameZh(task.getTaskMainPersonNameZh());
        taskInfo.setMidTimes(task.getMidTimes());
        ArrayList<String> users = new ArrayList<>();
        for (TaskUserPermission taskUserPermission:taskUserPermissionService.findAllByTaskId(task.getId())) {
            if (taskUserPermission.getUserId() != task.getTaskMainPerson()) {
                users.add(userService.get(taskUserPermission.getUserId()).getUserNameZh());
            }
        }
        taskInfo.setUserAdd(users);
        return taskInfo;
    }


    public Task getTaskById(Long id){
        return taskDAO.getTaskByIdAndIsEffective(id,1);
    }

    public Page<Task> getAllTaskByDept(int deptid, int pagenum){
        Pageable pageable = PageRequest.of(pagenum,20, Sort.by("taskMainPerson","taskName"));
        return taskDAO.findAllByTaskDeptAndIsEffective(deptid,1,pageable);
    }

    public void addOrUpdate(Task task){
        taskDAO.save(task);
    }

    public void  deleteTaskById(Long id){
        Task task = getTaskById(id);
        if (task != null){
            task.setIsEffective(0);
            addOrUpdate(task);
        }

    }



}
