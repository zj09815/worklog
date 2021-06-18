package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskUserPermissionDAO;
import com.uwntek.worklog.entity.task.Task;
import com.uwntek.worklog.entity.task.TaskInfo;
import com.uwntek.worklog.entity.task.TaskUserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskUserPermissionService {
    @Autowired
    TaskUserPermissionDAO taskUserPermissionDAO;
    @Autowired
    TaskService taskService;

    //    根据tbl_task_user_permission表按用户查看项目
    private Task getTaskByUser(TaskUserPermission taskUserPermission){
        if (taskService.existsById(taskUserPermission.getTaskId())) {
            return taskService.getTaskById(taskUserPermission.getTaskId());
        }else {
            return new Task();
        }
    }



    public Page<Task> findAllByUserId(Long userId, int pagenum) {
        Pageable pageable = PageRequest.of(pagenum, 10);
        Page<TaskUserPermission> taskUserPermissions = taskUserPermissionDAO.findAllByUserId(userId, pageable);
        List<Task> taskList = new ArrayList<>();
        for(TaskUserPermission taskUserPermission:taskUserPermissions.getContent()){
            taskList.add(getTaskByUser(taskUserPermission));
        }
        return new PageImpl<Task>(taskList,pageable,taskUserPermissions.getTotalElements());
    }

    public boolean existByTaskIdAndUserId(Long taskId, Long userId){
        return taskUserPermissionDAO.existsByTaskIdAndUserId(taskId,userId);
    }
    @Transactional
    public Long delete(Long taskId,Long userId){
        return taskUserPermissionDAO.deleteByTaskIdAndUserId(taskId,userId);
    }

    public void save(TaskUserPermission taskUserPermission){
        taskUserPermissionDAO.save(taskUserPermission);
    }

    public TaskUserPermission getByTaskIdAndUserId(Long taskId, Long userId){
        return taskUserPermissionDAO.getTaskUserPermissionByTaskIdAndUserId(taskId,userId);
    }

    public List<TaskUserPermission> findAllByTaskId(Long taskId){
        return taskUserPermissionDAO.findAllByTaskId(taskId);
    }

}
