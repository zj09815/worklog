package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskCheckDAO;
import com.uwntek.worklog.dto.task.TaskCheckDTO;
import com.uwntek.worklog.entity.task.TaskCheck;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class TaskCheckService {
    @Autowired
    TaskCheckDAO taskCheckDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TaskStartService taskStartService;
    @Autowired
    TaskService taskService;
    @Autowired
    DeptService deptService;

    public TaskCheckDTO getTaskCheckDTOById(Long id){
        TaskCheck taskCheck = taskCheckDAO.findById(id).orElse(null);
        TaskCheckDTO taskCheckDTO = modelMapper.map(taskCheck,TaskCheckDTO.class);
        taskCheckDTO.setTaskNameInfo(taskStartService.getTaskStartByTaskId(taskCheck.getTaskId()).getTaskName());
        taskCheckDTO.setTaskPeriod(taskService.getTaskById(taskCheck.getTaskId()).getTaskPeriod());
        taskCheckDTO.setTaskDept(deptService.get(taskService.getTaskById(taskCheck.getTaskId()).getTaskDept()).getDeptName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String taskStartTime = sdf.format(taskStartService.getTaskStartByTaskId(taskCheck.getTaskId()).getTaskStartTime());
        String taskEndTime = sdf.format(taskCheck.getTaskCheckTime());
        taskCheckDTO.setTaskAllTime(taskStartTime + " ~ " +taskEndTime);
        return taskCheckDTO;
    }

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
