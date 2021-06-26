package com.uwntek.worklog.controller.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.entity.task.Task;
import com.uwntek.worklog.entity.task.TaskMid;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.service.task.TaskMidService;
import com.uwntek.worklog.service.task.TaskService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("api/admin/task/mid")
public class TaskMidController {
    @Autowired
    TaskMidService taskMidService;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
        private Date taskMidTime;
        private String taskMidContent;
        private String taskMidConclusion;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidApprovalInfo {
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long taskMidApprovalPerson;
        @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
        private Date taskMidApprovalTime;
        private String taskMidApprovalComment;
    }
    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidStatus{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private String taskMidStatus;
    }



    @GetMapping("/{id}")
    @ApiOperation("根据id获取中期信息")
    public Result getTaskMidById(@PathVariable Long id){
        if (taskMidService.existsById(id)){
            return ResultFactory.buildSuccessResult(taskMidService.getTaskMidById(id));
        }else {
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
    }

    @GetMapping("/t/{taskid}")
    @ApiOperation("根据项目id获取所有中期信息")
    public Result getTaskMidsByTaskId(@PathVariable Long taskid){
        return ResultFactory.buildSuccessResult(taskMidService.getTaskMidByTaskId(taskid));
    }

    @PostMapping("/content")
    @ApiOperation(value = "很据id填写中期信息",notes = "只有状态为mid_i,并且task_mid_status=start时才能编辑")
    public Result setTaskMidInfoById(@RequestBody TaskMidInfo taskMidInfo){
        if (taskMidInfo.getId() == null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskMidService.existsById(taskMidInfo.getId())) {
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!(taskService.getTaskById(taskMidService.getTaskMidById(taskMidInfo.getId())
                .getTaskId())
                .getProcessId()
                .equals("mid_"+taskMidService.getTaskMidById(taskMidInfo.getId()).getTaskMidId())
                && taskMidService.getTaskMidById(taskMidInfo.getId()).getTaskMidStatus().equals("start")))
        {
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidInfo.getId());
        taskMid.setTaskMidTime(taskMidInfo.getTaskMidTime());
        taskMid.setTaskMidContent(taskMidInfo.getTaskMidContent());
        taskMid.setTaskMidConclusion(taskMidInfo.getTaskMidConclusion());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("编辑：" + taskMidInfo.getId() + " 成功");
    }

    @PostMapping("/approval")
    @ApiOperation("根据id填写核准信息")
    public Result setTaskMidApprovalInfoById(@RequestBody TaskMidApprovalInfo taskMidApprovalInfo){
        if (taskMidApprovalInfo.getId() == null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskMidService.existsById(taskMidApprovalInfo.getId())){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!(taskService.getTaskById(taskMidService.getTaskMidById(taskMidApprovalInfo.getId())
                .getTaskId())
                .getProcessId()
                .equals("mid_"+taskMidService.getTaskMidById(taskMidApprovalInfo.getId()).getTaskMidId())
                && taskMidService.getTaskMidById(taskMidApprovalInfo.getId()).getTaskMidStatus().equals("approval")))
        {
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidApprovalInfo.getId());
        taskMid.setTaskMidApprovalTime(taskMidApprovalInfo.getTaskMidApprovalTime());
        taskMid.setTaskMidApprovalPerson(taskMidApprovalInfo.getTaskMidApprovalPerson());
        taskMid.setTaskMidApprovalPersonNameZh(userService.get(taskMidApprovalInfo.getTaskMidApprovalPerson()).getUserNameZh());
        taskMid.setTaskMidApprovalComment(taskMidApprovalInfo.getTaskMidApprovalComment());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("编辑："+taskMidApprovalInfo.getId()+" 成功");

    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除一个中期验收",notes = "只能删除最后一个中期节点，依次删除")
    public Result deleteTaskMid(@RequestParam Long id){
        if (!taskMidService.existsById(id)){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!(taskMidService.getTaskMidById(id).getTaskMidId() == taskService.getTaskById(taskMidService.getTaskMidById(id).getTaskId()).getMidTimes()-1)){
            return ResultFactory.buildFailResult("请先删除最后的中期节点");
        }
        taskMidService.deleteTaskMidById(id);
        Task task = taskService.getTaskById(taskMidService.getTaskMidById(id).getTaskId());
        if (task.getMidTimes() > 0) {
            task.setMidTimes(task.getMidTimes() - 1);
        }
        taskService.addOrUpdate(task);
        return ResultFactory.buildSuccessResult("删除成功");
    }
    @PostMapping("/add")
    @ApiOperation("新增一个中期验收")
    public Result addTaskMid(@RequestParam Long taskid){
        if (!taskService.existsById(taskid)){
            return ResultFactory.buildFailResult("项目id输入错误，请重试");
        }
        TaskMid taskMid = new TaskMid();
        taskMid.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        taskMid.setTaskId(taskid);
        taskMid.setIsEffective(1);
        taskMid.setTaskMidStatus("start");
        Task task = taskService.getTaskById(taskid);
        task.setMidTimes(task.getMidTimes()+1);
        taskService.addOrUpdate(task);
        taskMid.setTaskMidId(task.getMidTimes()-1);
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("新增成功");
    }

    @PostMapping("/status")
    @ApiOperation("修改中期状态")
    public Result setTaskMidStatus(@RequestBody TaskMidStatus taskMidStatus){
        if (!taskMidService.existsById(taskMidStatus.getId())){
            return ResultFactory.buildFailResult("id输入错误，请重试");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidStatus.getId());
        taskMid.setTaskMidStatus(taskMidStatus.getTaskMidStatus());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("修改："+taskMidStatus.getId()+" 成功");
    }



}
