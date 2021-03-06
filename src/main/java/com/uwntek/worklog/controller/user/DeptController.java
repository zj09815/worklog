package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@Api(value = "部门")
public class DeptController {
    @Autowired
    DeptService deptService;
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/api/admin/getDeptNameById")
    @ApiOperation("通过id获取部门名称")
    public Result getDeptNameById(int id) {
        if (deptService.idIsExist(id)) {
            return ResultFactory.buildSuccessResultWithoutData(deptService.get(id).getDeptName());
        } else {
            return ResultFactory.buildFailResult("部门id不存在");
        }
    }


    @GetMapping("/api/admin/deptList")
    @ApiOperation(notes = "显示有效组", value = "显示有效组")
    public Result list() throws Exception {
        List<Dept> deptList = deptService.findAllByIsEffective(1);
        return ResultFactory.buildSuccessResult(deptList);
    }

    @GetMapping("/api/admin/deptList/v2")
    @ApiOperation(notes = "根据登录用户动态显示有效组", value = "根据登录用户动态显示有效组")
    public Result listByCurrentUser() throws Exception {
        int roleId = userRoleService.getRoleId();
        List<Dept> deptList = new ArrayList<>();
        if (roleId == 1){
            deptList = deptService.findAllByIsEffective(1);
        }else {
            Dept dept = deptService.get(userRoleService.getDept());
            List<Dept> deptTree = new ArrayList<>();
            deptTree.add(dept);
            Dept deptRoot = deptService.get(2);
            deptRoot.setChildren(deptTree);
            deptList.add(deptRoot);
        }

        return ResultFactory.buildSuccessResult(deptList);
    }

    @GetMapping("api/admin/deptListByParentId")
    @ApiOperation(notes = "按ParentId显示有效组", value = "按ParentId显示有效组")
    public Result listByParentId(int parentId) throws Exception {
        if (deptService.parentIdIsExist(parentId)) {
            List<Dept> deptList = deptService.findAllByParentId(parentId, 1);
            return ResultFactory.buildSuccessResult(deptList);
        } else {
            return ResultFactory.buildFailResult("parentid不存在");
        }
    }

    @PostMapping("api/admin/addOrUpdateDept")
    @ApiOperation(notes = "增加或更新组", value = "增加或更新组")
    public Result addOrUpdate(@RequestBody Dept dept) throws Exception {
        deptService.addOrUpdateDept(dept);
        return ResultFactory.buildSuccessResult(dept);
    }
}
