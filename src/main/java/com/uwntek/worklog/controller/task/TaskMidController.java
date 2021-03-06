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
    private static class TaskMidInfo {
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskMidTime;
        private String taskMidContent;
        private String taskMidConclusion;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidExamineInfo {
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private String taskMidExamineSummary;
        private String taskMidExamineConclusion;
        private String taskMidExamineVerify;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskMidExamineTime;
        private Long taskMidExaminePerson;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidApprovalInfo {
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private Long taskMidApprovalPerson;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date taskMidApprovalTime;
        private String taskMidApprovalComment;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskMidStatus {
        @JsonDeserialize(using = LongJsonDeserializer.class)
        @JsonSerialize(using = LongJsonSerializer.class)
        private Long id;
        private String taskMidStatus;
    }


    @GetMapping("/{id}")
    @ApiOperation("??????id??????????????????")
    public Result getTaskMidById(@PathVariable Long id) {
        if (taskMidService.existsById(id)) {
            return ResultFactory.buildSuccessResult(taskMidService.getTaskMidDTOById(id));
        } else {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
    }

    @GetMapping("/t/{taskid}")
    @ApiOperation("????????????id????????????????????????")
    public Result getTaskMidsByTaskId(@PathVariable Long taskid) {
        return ResultFactory.buildSuccessResult(taskMidService.getTaskMidByTaskId(taskid));
    }

    @PostMapping("/content")
    @ApiOperation(value = "??????id??????????????????", notes = "???????????????mid_i,??????task_mid_status=start???????????????")
    public Result setTaskMidInfoById(@RequestBody TaskMidInfo taskMidInfo) {
        if (taskMidInfo.getId() == null) {
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskMidService.existsById(taskMidInfo.getId())) {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!(taskService.getTaskById(taskMidService.getTaskMidById(taskMidInfo.getId())
                .getTaskId())
                .getProcessId()
                .equals("mid_" + taskMidService.getTaskMidById(taskMidInfo.getId()).getTaskMidId())
                && taskMidService.getTaskMidById(taskMidInfo.getId()).getTaskMidStatus().equals("start"))) {
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidInfo.getId());
        taskMid.setTaskMidTime(taskMidInfo.getTaskMidTime());
        taskMid.setTaskMidContent(taskMidInfo.getTaskMidContent());
        taskMid.setTaskMidConclusion(taskMidInfo.getTaskMidConclusion());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("?????????" + taskMidInfo.getId() + " ??????");
    }

    @PostMapping("/examine")
    @ApiOperation("??????id??????????????????")
    public Result setTaskMidExamineInfoById(@RequestBody TaskMidExamineInfo taskMidExamineInfo) {
        if (taskMidExamineInfo.getId() == null) {
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskMidService.existsById(taskMidExamineInfo.getId())) {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!(taskService.getTaskById(taskMidService.getTaskMidById(taskMidExamineInfo.getId())
                .getTaskId())
                .getProcessId()
                .equals("mid_" + taskMidService.getTaskMidById(taskMidExamineInfo.getId()).getTaskMidId())
                && taskMidService.getTaskMidById(taskMidExamineInfo.getId()).getTaskMidStatus().equals("examine"))) {
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidExamineInfo.getId());
        taskMid.setTaskMidExamineConclusion(taskMidExamineInfo.getTaskMidExamineConclusion());
        taskMid.setTaskMidExamineSummary(taskMidExamineInfo.getTaskMidExamineSummary());
        taskMid.setTaskMidExamineTime(taskMidExamineInfo.getTaskMidExamineTime());
        taskMid.setTaskMidExaminePerson(taskMidExamineInfo.getTaskMidExaminePerson());
        taskMid.setTaskMidExaminePersonNameZh(userService.get(taskMidExamineInfo.getTaskMidExaminePerson()).getUserNameZh());
        taskMid.setTaskMidExamineVerify(taskMidExamineInfo.getTaskMidExamineVerify());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("?????????" + taskMidExamineInfo.getId() + " ??????");

    }

    @PostMapping("/approval")
    @ApiOperation("??????id??????????????????")
    public Result setTaskMidApprovalInfoById(@RequestBody TaskMidApprovalInfo taskMidApprovalInfo) {
        if (taskMidApprovalInfo.getId() == null) {
            return ResultFactory.buildFailResult("????????????");
        }
        if (!taskMidService.existsById(taskMidApprovalInfo.getId())) {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!(taskService.getTaskById(taskMidService.getTaskMidById(taskMidApprovalInfo.getId())
                .getTaskId())
                .getProcessId()
                .equals("mid_" + taskMidService.getTaskMidById(taskMidApprovalInfo.getId()).getTaskMidId())
                && taskMidService.getTaskMidById(taskMidApprovalInfo.getId()).getTaskMidStatus().equals("approval"))) {
            return ResultFactory.buildFailResult("????????????????????????");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidApprovalInfo.getId());
        taskMid.setTaskMidApprovalTime(taskMidApprovalInfo.getTaskMidApprovalTime());
        taskMid.setTaskMidApprovalPerson(taskMidApprovalInfo.getTaskMidApprovalPerson());
        taskMid.setTaskMidApprovalPersonNameZh(userService.get(taskMidApprovalInfo.getTaskMidApprovalPerson()).getUserNameZh());
        taskMid.setTaskMidApprovalComment(taskMidApprovalInfo.getTaskMidApprovalComment());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("?????????" + taskMidApprovalInfo.getId() + " ??????");

    }

    @PostMapping("/delete")
    @ApiOperation(value = "????????????????????????", notes = "???????????????????????????????????????????????????")
    public Result deleteTaskMid(@RequestParam Long id) {
        if (!taskMidService.existsById(id)) {
            return ResultFactory.buildFailResult("id?????????????????????");
        }
        if (!(taskMidService.getTaskMidById(id).getTaskMidId() == taskService.getTaskById(taskMidService.getTaskMidById(id).getTaskId()).getMidTimes() - 1)) {
            return ResultFactory.buildFailResult("?????????????????????????????????");
        }
        taskMidService.deleteTaskMidById(id);
        Task task = taskService.getTaskById(taskMidService.getTaskMidById(id).getTaskId());
        if (task.getMidTimes() > 0) {
            task.setMidTimes(task.getMidTimes() - 1);
        }
        taskService.addOrUpdate(task);
        return ResultFactory.buildSuccessResult("????????????");
    }

    @PostMapping("/add")
    @ApiOperation("????????????????????????")
    public Result addTaskMid(@RequestParam Long taskid) {
        if (!taskService.existsById(taskid)) {
            return ResultFactory.buildFailResult("??????id????????????????????????");
        }
        TaskMid taskMid = new TaskMid();
        taskMid.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        taskMid.setTaskId(taskid);
        taskMid.setIsEffective(1);
        taskMid.setTaskMidStatus("start");
        Task task = taskService.getTaskById(taskid);
        task.setMidTimes(task.getMidTimes() + 1);
        taskService.addOrUpdate(task);
        taskMid.setTaskMidId(task.getMidTimes() - 1);
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("????????????");
    }

    @PostMapping("/status")
    @ApiOperation("??????????????????")
    public Result setTaskMidStatus(@RequestBody TaskMidStatus taskMidStatus) {
        if (!taskMidService.existsById(taskMidStatus.getId())) {
            return ResultFactory.buildFailResult("id????????????????????????");
        }
        TaskMid taskMid = taskMidService.getTaskMidById(taskMidStatus.getId());
        taskMid.setTaskMidStatus(taskMidStatus.getTaskMidStatus());
        taskMidService.addOrUpdate(taskMid);
        return ResultFactory.buildSuccessResult("?????????" + taskMidStatus.getId() + " ??????");
    }


}
