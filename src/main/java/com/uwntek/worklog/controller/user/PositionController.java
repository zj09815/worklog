package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.entity.user.Position;
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
    public List<Position> list() throws Exception {
        return positionService.findAllByIsEffective(1);
    }

    @PostMapping("api/admin/addOrUpdatePosition")
    @ApiOperation(notes = "增加或更新职位", value = "addOrUpdatePosition")
    public Position addOrUpdatePosition(@RequestBody Position position) throws Exception {
        positionService.addOrUpdate(position);
        return position;
    }
/*
    @PostMapping("api/admin/deletePosition")
    @ApiOperation(notes = "删除职位", value = "deletePosition")
    public void deletePosition(int id) throws Exception{
        positionService.deleteById(id);
    }
 */
}
