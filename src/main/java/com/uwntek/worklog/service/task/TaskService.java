package com.uwntek.worklog.service.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.dao.task.TaskDAO;
import com.uwntek.worklog.dao.task.TaskUserPermissionDAO;
import com.uwntek.worklog.entity.task.Task;
import com.uwntek.worklog.entity.task.TaskInfo;
import com.uwntek.worklog.entity.task.TaskUserPermission;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.entity.user.UserRole;
import com.uwntek.worklog.service.user.UserRoleService;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    TaskUserPermissionService taskUserPermissionService;
    @Autowired
    UserService userService;

    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskUserPermissionDAO taskUserPermissionDAO;
    @Autowired
    UserRoleService userRoleService;



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
        if (taskDAO.getTaskByIdAndIsEffective(id,1) == null){
            return null;
        }
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

    public Page<Task> getTasksByCurrentUser(int pagenum) {
        Pageable pageable = PageRequest.of(pagenum,20, Sort.by("taskMainPerson","taskName"));
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUserName(username);
        Long userId = user.getId();
        int roleId = 3;
        List<UserRole> userRoles = userRoleService.listAllByUserId(userId);
        for (UserRole userRole: userRoles){
            if (userRole.getRoleId() == 1){
                roleId = 1;
                break;
            }else if (userRole.getRoleId() == 2){
                roleId = 2;
                break;
            }
        }
        switch (roleId){
            case 3:
                List<Long> taskIds = taskUserPermissionDAO.getTaskIdByUserId(userId);
                return taskDAO.findAllByIdInAndIsEffective(taskIds,1,pageable);
            case 2:
                return taskDAO.findAllByTaskDeptAndIsEffective(user.getDept(),1,pageable);
            case 1:
                return taskDAO.findAllByIsEffective(1,pageable);
            default:
                return taskDAO.findAllByTaskMainPersonAndIsEffective(userId,1,pageable);
        }

    }




}
