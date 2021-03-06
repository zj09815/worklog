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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
        private Date taskStartTime;
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
    @ApiOperation("??????id??????????????????")
    public Result getTaskStartById(@PathVariable Long id){
        if (taskStartService.existsById(id)){
            return ResultFactory.buildSuccessResult(taskStartService.getTaskStartById(id));
        }else {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
    }

    @GetMapping("/t/{taskid}")
    @ApiOperation("??????taskId??????????????????")
    public Result getTaskStartByTaskId(@PathVariable Long taskid){
            return ResultFactory.buildSuccessResult(taskStartService.getTaskStartByTaskId(taskid));
    }


    @PostMapping("/content")
    @ApiOperation("??????????????????,id?????????null,??????????????????process_id=start???????????????")
    public Result setTaskStartInfoByTaskId(@RequestBody TaskStartInfo taskStartInfo){
        if (taskStartInfo.getId()==null){
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskStartService.existsById(taskStartInfo.getId())){
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartInfo.getId()).getTaskId()).getProcessId().equals("start")){
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartInfo.getId());
        taskStartById.setTaskContent(taskStartInfo.getTaskContent());
        taskStartById.setTaskStartTime(taskStartInfo.getTaskStartTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("??????:"+taskStartInfo.getId()+" ??????");
    }



    @PostMapping("/examine")
    @ApiOperation(value = "????????????????????????",notes ="id?????????null,??????????????????process_id=start_examine???????????????" )
    public Result setTaskStartExamineInfoById(@RequestBody TaskStartExamineInfo taskStartExamineInfo){
        if (taskStartExamineInfo.getId()==null){
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskStartService.existsById(taskStartExamineInfo.getId())){
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartExamineInfo.getId()).getTaskId()).getProcessId().equals("start_examine")){
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartExamineInfo.getId());
        taskStartById.setExamineComment(taskStartExamineInfo.getExamineComment());
        taskStartById.setExaminePerson(taskStartExamineInfo.getExaminePerson());
        taskStartById.setExaminePersonNameZh(userService.get(taskStartExamineInfo.getExaminePerson()).getUserNameZh());
        taskStartById.setExamineTime(taskStartExamineInfo.getExamineTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("??????:"+taskStartExamineInfo.getId()+" ??????");

    }
    @PostMapping("/approval")
    @ApiOperation(value = "????????????????????????",notes="id?????????null,??????????????????process_id=start_approval???????????????")
    public Result setTaskStartApprovalInfoById(@RequestBody TaskStartApprovalInfo taskStartApprovalInfo){
        if (taskStartApprovalInfo.getId()==null){
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskStartService.existsById(taskStartApprovalInfo.getId())){
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartApprovalInfo.getId()).getTaskId()).getProcessId().equals("start_approval")){
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartApprovalInfo.getId());
        taskStartById.setApprovalComment(taskStartApprovalInfo.getApprovalComment());
        taskStartById.setApprovalPerson(taskStartApprovalInfo.getApprovalPerson());
        taskStartById.setApprovalPersonNameZh(userService.get(taskStartApprovalInfo.getApprovalPerson()).getUserNameZh());
        taskStartById.setApprovalTime(taskStartApprovalInfo.getApprovalTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("??????:"+taskStartApprovalInfo.getId()+" ??????");
    }
    @PostMapping("/ratify")
    @ApiOperation(value = "????????????????????????",notes = "id?????????null,??????????????????process_id=start_ratify???????????????")
    public Result setTaskStartRatifyInfoById(@RequestBody TaskStartRatifyInfo taskStartRatifyInfo){
        if (taskStartRatifyInfo.getId()==null){
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskStartService.existsById(taskStartRatifyInfo.getId())){
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!taskService.getTaskById(taskStartService.getTaskStartById(taskStartRatifyInfo.getId()).getTaskId()).getProcessId().equals("start_ratify")){
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskStart taskStartById = taskStartService.getTaskStartById(taskStartRatifyInfo.getId());
        taskStartById.setRatifyComment(taskStartRatifyInfo.getRatifyComment());
        taskStartById.setRatifyPerson(taskStartRatifyInfo.getRatifyPerson());
        taskStartById.setRatifyPersonNameZh(userService.get(taskStartRatifyInfo.getRatifyPerson()).getUserNameZh());
        taskStartById.setRatifyTime(taskStartRatifyInfo.getRatifyTime());
        taskStartService.addOrUpdate(taskStartById);
        return ResultFactory.buildSuccessResult("??????:"+taskStartRatifyInfo.getId()+" ??????");


    }
}
