package com.uwntek.worklog.service.task;

import com.uwntek.worklog.dao.task.TaskMidDAO;
import com.uwntek.worklog.dto.task.TaskCheckDTO;
import com.uwntek.worklog.dto.task.TaskMidDTO;
import com.uwntek.worklog.entity.task.TaskCheck;
import com.uwntek.worklog.entity.task.TaskMid;
import com.uwntek.worklog.service.user.DeptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskMidService {
    @Autowired
    TaskMidDAO taskMidDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskStartService taskStartService;
    @Autowired
    DeptService deptService;

    public TaskMidDTO getTaskMidDTOById(Long id){
        TaskMid taskMid = taskMidDAO.findById(id).orElse(null);
        TaskMidDTO taskMidDTO = modelMapper.map(taskMid,TaskMidDTO.class);
        taskMidDTO.setTaskNameInfo(taskStartService.getTaskStartByTaskId(taskMid.getTaskId()).getTaskName());
        taskMidDTO.setTaskPeriod(taskService.getTaskById(taskMid.getTaskId()).getTaskPeriod());
        taskMidDTO.setTaskDept(deptService.get(taskService.getTaskById(taskMid.getTaskId()).getTaskDept()).getDeptName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String taskStartTime = sdf.format(taskStartService.getTaskStartByTaskId(taskMid.getTaskId()).getTaskStartTime());
        String taskEndTime = sdf.format(taskService.getTaskById(taskMid.getTaskId()).getTaskEndTime());
        taskMidDTO.setTaskAllTime(taskStartTime + " ~ " +taskEndTime);
        return taskMidDTO;
    }
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
        List<TaskMid> taskMids = taskMidDAO.getTaskMidByTaskIdAndIsEffective(taskId,1, Sort.by("taskMidId"));
        for(TaskMid taskMid:taskMids){
            taskMid.setIsEffective(0);
            addOrUpdate(taskMid);
        }
    }

    public List<TaskMid> getTaskMidByTaskId(Long taskId){
        return taskMidDAO.getTaskMidByTaskIdAndIsEffective(taskId,1, Sort.by("taskMidId"));
    }

    public TaskMid getTaskMidByTaskIdAndMidId(Long taskId, int taskMidId){
        return taskMidDAO.getTaskMidByTaskIdAndTaskMidIdAndIsEffective(taskId,taskMidId,1);
    }
}
