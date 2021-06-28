package com.uwntek.worklog.controller.task;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.entity.task.TaskStart;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.service.task.TaskService;
import com.uwntek.worklog.service.task.TaskStartService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/admin/task/start")
public class TaskStartController {
    @Autowired
    TaskStartService taskStartService;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskStartInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private String taskContent;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskStartExamineInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long examinePerson;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date examineTime;
        private String examineComment;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskStartApprovalInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long approvalPerson;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date approvalTime;
        private String approvalComment;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskStartRatifyInfo{
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long ratifyPerson;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date ratifyTime;
        private String ratifyComment;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取立项信息")
    public Result getTaskStartById(@PathVariable Long id){
        if (taskStartService.existsById(id)){
            return ResultFactory.buildSuccessResult(taskStartService.getTaskStartById(id));
        }else {
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
    }

    @GetMapping("/t/{taskid}")
    @ApiOperation("根据taskId获取立项信息")
    public Result getTaskStartByTaskId(@PathVariable Long taskid){
            return ResultFactory.buildSuccessResult(taskStartService.getTaskStartByTaskId(taskid));
    }


    @PostMapping("/content")
    @ApiOperation("编辑立项信息,id不能为null,并且只有项目process_id=start时才能编辑")
    public Result setTaskStartInfoByTaskId(@RequestBody TaskStartInfo taskStartInfo){
        if (taskStartInfo.getId()==null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskStartService.existsById(taskStartInfo.getId())){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartInfo.getId()).getTaskId()).getProcessId().equals("start")){
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartInfo.getId());
        taskStartById.setTaskContent(taskStartInfo.getTaskContent());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("编辑:"+taskStartInfo.getId()+" 成功");
    }



    @PostMapping("/examine")
    @ApiOperation(value = "编辑立项部门意见",notes ="id不能为null,并且只有项目process_id=start_examine时才能编辑" )
    public Result setTaskStartExamineInfoById(@RequestBody TaskStartExamineInfo taskStartExamineInfo){
        if (taskStartExamineInfo.getId()==null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskStartService.existsById(taskStartExamineInfo.getId())){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartExamineInfo.getId()).getTaskId()).getProcessId().equals("start_examine")){
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartExamineInfo.getId());
        taskStartById.setExamineComment(taskStartExamineInfo.getExamineComment());
        taskStartById.setExaminePerson(taskStartExamineInfo.getExaminePerson());
        taskStartById.setExaminePersonNameZh(userService.get(taskStartExamineInfo.getExaminePerson()).getUserNameZh());
        taskStartById.setExamineTime(taskStartExamineInfo.getExamineTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("编辑:"+taskStartExamineInfo.getId()+" 成功");

    }
    @PostMapping("/approval")
    @ApiOperation(value = "编辑立项核准意见",notes="id不能为null,并且只有项目process_id=start_approval时才能编辑")
    public Result setTaskStartApprovalInfoById(@RequestBody TaskStartApprovalInfo taskStartApprovalInfo){
        if (taskStartApprovalInfo.getId()==null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskStartService.existsById(taskStartApprovalInfo.getId())){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartApprovalInfo.getId()).getTaskId()).getProcessId().equals("start_examine")){
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartApprovalInfo.getId());
        taskStartById.setApprovalComment(taskStartApprovalInfo.getApprovalComment());
        taskStartById.setApprovalPerson(taskStartApprovalInfo.getApprovalPerson());
        taskStartById.setApprovalPersonNameZh(userService.get(taskStartApprovalInfo.getApprovalPerson()).getUserNameZh());
        taskStartById.setApprovalTime(taskStartApprovalInfo.getApprovalTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("编辑:"+taskStartApprovalInfo.getId()+" 成功");
    }
    @PostMapping("/ratify")
    @ApiOperation(value = "编辑立项批准意见",notes = "id不能为null,并且只有项目process_id=start_ratify时才能编辑")
    public Result setTaskStartRatifyInfoById(@RequestBody TaskStartRatifyInfo taskStartRatifyInfo){
        if (taskStartRatifyInfo.getId()==null){
            return ResultFactory.buildFailResult("禁止新增");
        }
        if (!taskStartService.existsById(taskStartRatifyInfo.getId())){
            return ResultFactory.buildFailResult("id不正确，请检查");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartRatifyInfo.getId()).getTaskId()).getProcessId().equals("start_examine")){
            return ResultFactory.buildFailResult("当前状态不可编辑");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartRatifyInfo.getId());
        taskStartById.setRatifyComment(taskStartRatifyInfo.getRatifyComment());
        taskStartById.setRatifyPerson(taskStartRatifyInfo.getRatifyPerson());
        taskStartById.setRatifyPersonNameZh(userService.get(taskStartRatifyInfo.getRatifyPerson()).getUserNameZh());
        taskStartById.setRatifyTime(taskStartRatifyInfo.getRatifyTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("编辑:"+taskStartRatifyInfo.getId()+" 成功");


    }
}
