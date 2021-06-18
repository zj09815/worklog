package com.uwntek.worklog.controller.worklog;

import com.uwntek.worklog.entity.worklog.Worklog;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.service.worklog.WorklogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
@Api(value = "工作日志")
public class WorklogController {
    @Autowired
    WorklogService worklogService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    @GetMapping("/api/admin/worklogList/user/{userid}")
    @ApiOperation(notes = "用户按时间获取", value = "用户按时间获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "startTime", readOnly = true, dataType = "date"),
            @ApiImplicitParam(name = "endTime", value = "endTime", readOnly = true, dataType = "date")
    })
    public Result listByDateAndUser(@PathVariable("userid") Long userid, Date startTime, Date endTime) throws Exception {
        if (userService.idIsExist(userid)) {
            List<Worklog> worklogList = worklogService.getWorklogByDate(startTime, endTime, userid);
            return ResultFactory.buildSuccessResult(worklogList);
        } else {
            return ResultFactory.buildFailResult("用户不存在");
        }
    }

    @GetMapping("api/admin/worklogList/dept/{deptid}")
    @ApiOperation(notes = "部门按时间获取", value = "部门按时间获取")
    public Result listByDateAndDept(@PathVariable("deptid") int deptid, Date startTime, Date endTime) throws Exception {
        if (deptService.idIsExist(deptid)) {
            List<Worklog> worklogList = worklogService.getWorklogByDept(startTime, endTime, deptid);
            return ResultFactory.buildSuccessResult(worklogList);
        } else {
            return ResultFactory.buildFailResult("部门不存在");
        }
    }

    @GetMapping("api/admin/worklogList/id/{id}")
    @ApiOperation(notes = "按id获取", value = "按id获取")
    public Result listAllById(Long id) throws Exception {
        if (worklogService.isExist(id)) {
            Worklog worklog = worklogService.getById(id);
            return ResultFactory.buildSuccessResult(worklog);
        } else {
            return ResultFactory.buildFailResult("id不存在");
        }
    }

    @PostMapping("/api/admin/addOrUpdateWorklog")
    @ApiOperation(notes = "新增或修改worklog", value = "新增或修改worklog")
    public Result addOrUpdateWorkLog(@RequestBody Worklog worklog) throws Exception {
        if (!userService.idIsExist(worklog.getUser_fk())) {
            return ResultFactory.buildFailResult("用户不存在");
        } else if (!deptService.idIsExist(worklog.getDept_fk())) {
            return ResultFactory.buildFailResult("部门不存在");
        } else {
            worklogService.addOrUpdateWorklog(worklog);
            return ResultFactory.buildSuccessResultWithoutData("新增或更新成功");
        }
    }
}
