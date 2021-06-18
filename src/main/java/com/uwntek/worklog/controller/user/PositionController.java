package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.entity.user.Position;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(value = "职位")
public class PositionController {
    @Autowired
    PositionService positionService;

    @GetMapping("/api/admin/positionList")
    @ApiOperation(notes = "显示有效职位", value = "positionList")
    public Result list() throws Exception {
        return ResultFactory.buildSuccessResult(positionService.findAllByIsEffective(1));
    }

    @PostMapping("api/admin/addOrUpdatePosition")
    @ApiOperation(notes = "增加或更新职位", value = "addOrUpdatePosition")
    public Result addOrUpdatePosition(@RequestBody Position position) throws Exception {
        positionService.addOrUpdate(position);
        return ResultFactory.buildSuccessResult(position);
    }
/*
    @PostMapping("api/admin/deletePosition")
    @ApiOperation(notes = "删除职位", value = "deletePosition")
    public void deletePosition(int id) throws Exception{
        positionService.deleteById(id);
    }
 */
}
