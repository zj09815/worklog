package com.uwntek.worklog.controller.experience;

import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.experience.ExpOptLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/experience/log")
public class ExpOptLogController {
    @Autowired
    ExpOptLogService expOptLogService;

    @GetMapping("/{expid}")
    @ApiOperation("根据经验库id获取事件")
    public Result getLogByExpId(@PathVariable Long expid){
        return ResultFactory.buildSuccessResult(expOptLogService.findAllByExpId(expid));
    }

    @GetMapping
    @ApiOperation("获取所有事件")
    public Result getAllLog(@RequestParam int pagenum){
        return ResultFactory.buildSuccessResult(expOptLogService.findAll(pagenum));
    }

}
