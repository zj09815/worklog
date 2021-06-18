package com.uwntek.worklog.controller.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uwntek.worklog.entity.task.*;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.task.*;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    TaskStartService taskStartService;
    @Autowired
    TaskMidService taskMidService;
    @Autowired
    TaskCheckService taskCheckService;
    @Autowired
    TaskUserPermissionService taskUserPermissionService;

//传入的参数
    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class  TaskIn{
        private Long id;
        private String taskName;
        private Long taskMainPerson;
        private int midTimes;
        private String taskContent;
        private Date taskStartTime;
        private Date taskEndTime;
        private Date taskCheckTime;
        private List<Long> userAdd;

    }

    //    项目状态信息
    @Getter
    @Setter
    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    private static class TaskProcess{
        private Long id;
        private String processId;
    }




//将传入的TaskIn类转换成数据库中的Task类
    private Task transTaskInToTask(TaskIn taskIn){
        Task task = new Task();
        task.setId(taskIn.getId());
        task.setTaskName(taskIn.getTaskName());
        task.setTaskMainPerson(taskIn.getTaskMainPerson());
        task.setTaskMainPersonNameZh(userService.get(taskIn.getTaskMainPerson()).getUserNameZh());
        task.setTaskDept(userService.get(taskIn.getTaskMainPerson()).getDept());
        task.setMidTimes(taskIn.getMidTimes());
        task.setProcessId("start");
        task.setIsEffective(1);
        return task;
    }

//    新增立项信息，将TaskIn和Task中的部分信息填入TaskStart中；
//    仅允许在与Task一同新增，不能单独新增
    private TaskStart addTaskStart(Task task, TaskIn taskIn){
        TaskStart taskStart = new TaskStart();
        taskStart.setId(UUID.randomUUID().getMostSignificantBits()& Long.MAX_VALUE);
        taskStart.setIsEffective(1);
        Date taskStartTime = taskIn.getTaskStartTime();
        Date taskEndTime = taskIn.getTaskEndTime();
        taskStart.setTaskId(task.getId());
        taskStart.setTaskStartTime(taskStartTime);
        taskStart.setTaskEndTime(taskEndTime);
        taskStart.setTaskName(taskIn.getTaskName());
        taskStart.setTaskPeriod((int)(Math.abs(taskEndTime.getTime()-taskStartTime.getTime())/86400000));
        taskStart.setTaskContent(taskIn.getTaskContent());
        taskStart.setTaskMainPerson(task.getTaskMainPerson());
        taskStart.setTaskMainPersonNameZh(task.getTaskMainPersonNameZh());
        return taskStart;
    }

//    新增中期信息，根据mid_times生成条数
    private TaskMid addTaskMid(Task task, int taskMidId){
        TaskMid taskMid = new TaskMid();
        taskMid.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        taskMid.setTaskId(task.getId());
        taskMid.setIsEffective(1);
        taskMid.setTaskMidId(taskMidId);
        taskMid.setTaskMidStatus("start");
        return taskMid;
    }





//    新增验收信息
    private TaskCheck addTaskCheck(TaskIn taskIn){
        TaskCheck taskCheck = new TaskCheck();
        taskCheck.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        taskCheck.setTaskId(taskIn.getId());
        taskCheck.setTaskCheckTime(taskIn.getTaskCheckTime());
        taskCheck.setIsEffective(1);
        return taskCheck;
    }

    private void addTaskUserPermission(Long taskId,Long userId){
        if (!taskUserPermissionService.existByTaskIdAndUserId(taskId,userId)){
            TaskUserPermission taskUserPermission = new TaskUserPermission();
            taskUserPermission.setTaskId(taskId);
            taskUserPermission.setUserId(userId);
            taskUserPermissionService.save(taskUserPermission);
        }
    }

    private void deleteTaskUserPermission(Long taskId, Long userId){
        if (taskUserPermissionService.existByTaskIdAndUserId(taskId,userId)){
            taskUserPermissionService.delete(taskId, userId);
        }
    }





    @GetMapping("/all")
    @ApiOperation("获取所有项目列表")
    public Result getAllTask(@RequestParam int pagenum){
        return ResultFactory.buildSuccessResult(taskService.getAllTask(pagenum));
    }

    @GetMapping("/u/{userid}")
    @ApiOperation("根据用户id获取项目列表")
    public Result getTasksByUserId(@PathVariable Long userid, @RequestParam int pagenum){
        if (userService.idIsExist(userid)){
            return ResultFactory.buildSuccessResult(taskService.getAllTaskByUser(userid,pagenum));
        }else {
            return ResultFactory.buildFailResult("用户不存在");
        }
    }


    @GetMapping("/u/{userid}/v2")
    @ApiOperation("根据用户id获取项目列表v2.0")
    public Result getTasksByUser(@PathVariable Long userid, @RequestParam int pagenum){
        if (userService.idIsExist(userid)){
            return ResultFactory.buildSuccessResult(taskUserPermissionService.findAllByUserId(userid,pagenum));
        }else {
            return ResultFactory.buildFailResult("用户不存在");
        }
    }




        @GetMapping("/d/{deptid}")
    @ApiOperation("根据部门id获取项目列表")
    public Result getTasksByDeptId(@PathVariable int deptid,@RequestParam int pagenum){
        if(deptService.idIsExist(deptid)){
            return ResultFactory.buildSuccessResult(taskService.getAllTaskByDept(deptid,pagenum));
        }else {
            return ResultFactory.buildFailResult("部门不存在");
        }
    }





    @PostMapping("")
    @ApiOperation("增加或编辑项目信息")
    public Result addOrUpdateTask(@RequestBody TaskIn taskIn) {
        if (taskIn.getTaskName() == null || taskIn.getTaskContent() == null || taskIn.getTaskStartTime() == null || taskIn.getTaskEndTime()==null) {
            return ResultFactory.buildFailResult("信息未填充完整，请检查");
        }
        if (taskIn.getTaskEndTime().getTime() < taskIn.getTaskStartTime().getTime()){
            return ResultFactory.buildFailResult("结束时间需要大于起始时间，请检查");
        }
        if (!userService.idIsExist(taskIn.getTaskMainPerson())){
            return ResultFactory.buildFailResult("用户不存在");
        }
        if (taskIn.getId()==null){
//            新增一个Task
            //ID为UUID
            Long uuid = UUID.randomUUID().getMostSignificantBits()& Long.MAX_VALUE;
            taskIn.setId(uuid);
            Task task = transTaskInToTask(taskIn);

            //添加TaskCheck
            TaskCheck taskCheck = addTaskCheck(taskIn);
            taskCheckService.addOrUpdate(taskCheck);

            //添加TaskMid
            for (int i=0; i<task.getMidTimes(); i++){
                TaskMid taskMid = addTaskMid(task, i);
                taskMidService.addOrUpdate(taskMid);
            }

            //添加TaskStart
            TaskStart taskStart = addTaskStart(task,taskIn);
            taskStartService.addOrUpdate(taskStart);
            taskService.addOrUpdate(task);

            //添加用户与Task的关联
            addTaskUserPermission(taskIn.getId(), taskIn.getTaskMainPerson());
            for (Long user: taskIn.getUserAdd()){
                addTaskUserPermission(taskIn.getId(), user);
            }

            return ResultFactory.buildSuccessResult("新增成功，id为："+task.getId());
        }else {

//            编辑项目信息，仅涉及项目信息的修改，其他内容的修改在对应的Controller中
            if (taskService.existsById(taskIn.getId())){
                deleteTaskUserPermission(taskService.getTaskById(taskIn.getId()).getId(), taskService.getTaskById(taskIn.getId()).getTaskMainPerson());

                Task task = transTaskInToTask(taskIn);
                taskService.addOrUpdate(task);
                addTaskUserPermission(task.getId(), task.getTaskMainPerson());
                TaskStart taskStart = taskStartService.getTaskStartByTaskId(taskIn.getId());
                taskStart.setTaskName(taskIn.getTaskName());
                taskStart.setTaskMainPerson(taskIn.getTaskMainPerson());
                taskStart.setTaskMainPersonNameZh(userService.get(taskIn.getTaskMainPerson()).getUserNameZh());
                return ResultFactory.buildSuccessResult("编辑"+task.getId()+"成功");
            }else {
                return ResultFactory.buildFailResult("id不正确，请检查");
            }
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除项目")
    public Result deleteTask(@RequestParam Long id){
        if (taskService.existsById(id)) {
            taskService.deleteTaskById(id);
            taskStartService.deleteTaskStartByTaskId(id);
            taskMidService.deleteTaskMidsByTaskId(id);
            taskCheckService.deleteTaskCheckByTaskId(id);
            return ResultFactory.buildSuccessResult("删除成功");
        }else {
            return ResultFactory.buildFailResult("项目id不存在");
        }
    }

    @GetMapping("/{taskid}")
    @ApiOperation("查看项目信息")
    public Result getOneTask(@PathVariable Long taskid){
        if (taskService.existsById(taskid)){
            return ResultFactory.buildSuccessResult(taskService.getTaskInfoById(taskid));
        }else {
            return ResultFactory.buildFailResult("项目id不存在");
        }
    }


    @PostMapping("/process")
    @ApiOperation("修改项目状态")
    public Result setTaskProcess(@RequestBody TaskProcess taskProcess){
        if (taskProcess.getId() == null || taskProcess.getProcessId() == null){
            return ResultFactory.buildFailResult("信息不能为空");
        }
        if (!taskService.existsById(taskProcess.getId())){
            return ResultFactory.buildFailResult("项目不存在");
        }
        Task task = taskService.getTaskById(taskProcess.getId());
        task.setProcessId(taskProcess.getProcessId());
        taskService.addOrUpdate(task);
        return ResultFactory.buildSuccessResult("修改项目："+taskProcess.getProcessId()+" 成功");
    }



}