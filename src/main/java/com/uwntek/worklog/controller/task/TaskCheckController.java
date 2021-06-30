package com.uwntek.worklog.controller.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.entity.task.TaskCheck;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.service.task.TaskCheckService;
import com.uwntek.worklog.service.task.TaskService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/admin/task/check")
public class TaskCheckController {
    @Autowired
    TaskCheckService taskCheckService;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskCheckInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskCheckTime;
        private String taskCheckContent;
        private String taskCheckConclusion;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskCheckExamineInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private String taskCheckExamineSummary;
        private String taskCheckExamineConclusion;
        private String taskCheckExamineVerify;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskCheckExamineTime;
        private Long taskCheckExaminePerson;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskCheckApprovalInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long taskCheckApprovalPerson;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskCheckApprovalTime;
        private String taskCheckApprovalComment;

    }



    @GetMapping("/{id}")
    @ApiOperation("根据id获取项目验收信息")
    public Result getTaskCheckById(@PathVariable Long id){
        if (!taskCheckService.existsById(id)){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        return ResultFactory.buildSuccessResult(taskCheckService.getTaskCheckById(id));
    }


    @GetMapping("/t/{taskid}")
    @ApiOperation("根据项目id获取项目验收信息")
    public Result getTaskCheckByTaskId(@PathVariable Long taskid){
        return ResultFactory.buildSuccessResult(taskCheckService.getTaskCheckByTaskId(taskid));
    }

    @PostMapping("/content")
    @ApiOperation("填写项目验收信息,状态为check_start可编辑")
    public Result setTaskCheckInfo(@RequestBody TaskCheckInfo taskCheckInfo){
        if (taskCheckInfo.getId()== null){
            return ResultFactory.buildFailResult("id不能为空，请重试");
        }
        if (!taskCheckService.existsById(taskCheckInfo.getId())){
            return ResultFactory.buildFailResult("id不存在，请重试");
        }
        if (!taskService.getTaskById(taskCheckService.
                getTaskCheckById(taskCheckInfo.getId())
                .getTaskId())
                .getProcessId().equals("check_start")){
            return ResultFactory.buildFailResult("当前状态禁止编辑");
        }
        TaskCheck taskCheck = taskCheckService.getTaskCheckById(taskCheckInfo.getId());
        taskCheck.setTaskCheckTime(taskCheckInfo.getTaskCheckTime());
        taskCheck.setTaskCheckContent(taskCheckInfo.getTaskCheckContent());
        taskCheck.setTaskCheckConclusion(taskCheckInfo.getTaskCheckConclusion());
        taskCheckService.addOrUpdate(taskCheck);
        return ResultFactory.buildSuccessResult("编辑："+taskCheckInfo.getId()+" 成功");
    }

    @PostMapping("/examine")
    @ApiOperation("验收人员填写信息，状态为check_examine可编辑")
    public Result setTaskCheckExamineInfo(@RequestBody TaskCheckExamineInfo taskCheckExamineInfo){
        if (taskCheckExamineInfo.getId() == null){
            return ResultFactory.buildSuccessResult("id不能为空，请重试");
        }
        if (!taskCheckService.existsById(taskCheckExamineInfo.getId())){
            return ResultFactory.buildFailResult("id不存在，请重试");
        }
        if (!taskService.getTaskById(taskCheckService.getTaskCheckById(taskCheckExamineInfo.getId()).getTaskId()).getProcessId().equals("check_examine")){
            return ResultFactory.buildFailResult("当前状态禁止编辑");
        }
        TaskCheck taskCheck = taskCheckService.getTaskCheckById(taskCheckExamineInfo.getId());
        taskCheck.setTaskCheckExamineSummary(taskCheckExamineInfo.getTaskCheckExamineSummary());
        taskCheck.setTaskCheckExamineConclusion(taskCheckExamineInfo.getTaskCheckExamineConclusion());
        taskCheck.setTaskCheckExamineVerify(taskCheckExamineInfo.getTaskCheckExamineVerify());
        taskCheck.setTaskCheckExamineTime(taskCheckExamineInfo.getTaskCheckExamineTime());
        taskCheck.setTaskCheckExaminePerson(taskCheckExamineInfo.getTaskCheckExaminePerson());
        taskCheck.setTaskCheckExaminePersonNameZh(userService.get(taskCheckExamineInfo.getTaskCheckExaminePerson()).getUserNameZh());
        taskCheckService.addOrUpdate(taskCheck);
        return ResultFactory.buildSuccessResult("编辑："+taskCheckExamineInfo.getId()+" 成功");
    }



    @PostMapping("/approval")
    @ApiOperation("验收成功检查信息，状态为check_approval可编辑")
    public Result setTaskCheckApprovalInfo(@RequestBody TaskCheckApprovalInfo taskCheckApprovalInfo){
        if (taskCheckApprovalInfo.getId()== null){
            return ResultFactory.buildFailResult("id不能为空，请重试");
        }
        if (!taskCheckService.existsById(taskCheckApprovalInfo.getId())){
            return ResultFactory.buildFailResult("id不存在，请重试");
        }
        if (!taskService.getTaskById(taskCheckService.
                getTaskCheckById(taskCheckApprovalInfo.getId())
                .getTaskId())
                .getProcessId().equals("check_approval")){
            return ResultFactory.buildFailResult("当前状态禁止编辑");
        }
        TaskCheck taskCheck = taskCheckService.getTaskCheckById(taskCheckApprovalInfo.getId());
        taskCheck.setTaskCheckApprovalComment(taskCheckApprovalInfo.getTaskCheckApprovalComment());
        taskCheck.setTaskCheckApprovalTime(taskCheckApprovalInfo.getTaskCheckApprovalTime());
        taskCheck.setTaskCheckApprovalPerson(taskCheckApprovalInfo.getTaskCheckApprovalPerson());
        taskCheck.setTaskCheckApprovalPersonNameZh(userService.get(taskCheckApprovalInfo.getTaskCheckApprovalPerson()).getUserNameZh());
        taskCheckService.addOrUpdate(taskCheck);
        return ResultFactory.buildSuccessResult("编辑："+taskCheckApprovalInfo.getId()+" 成功");

    }




}
