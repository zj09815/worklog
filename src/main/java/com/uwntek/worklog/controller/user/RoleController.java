package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.entity.user.Role;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.PermissionService;
import com.uwntek.worklog.service.user.RolePermissionService;
import com.uwntek.worklog.service.user.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value = "用户权限")
@RequestMapping
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RolePermissionService rolePermissionService;

    @GetMapping("/api/admin/role")
    @ApiOperation("获取所有角色列表")
    public Result listRoles() {
        return ResultFactory.buildSuccessResult(roleService.listWithPermissions());
    }

    @PutMapping("/api/admin/role/status")
    @ApiOperation("修改角色状态")
    public Result updateRoleStatus(@RequestBody Role requestRole) {
        Role role = roleService.updateRoleStatus(requestRole);
        return ResultFactory.buildSuccessResult("用户" + role.getNameZh() + "状态更新成功");
    }

    @PutMapping("/api/admin/role")
    @ApiOperation("修改角色信息")
    public Result editRole(@RequestBody Role requestRole) {
        roleService.addOrUpdate(requestRole);
        rolePermissionService.savePermissionChanges(requestRole.getId(), requestRole.getPermissions());
        return ResultFactory.buildSuccessResultWithoutData("修改信息成功");
    }

    @PostMapping("/api/admin/role")
    @ApiOperation("修改用户角色")
    public Result addRole(@RequestBody Role requestRole) {
        roleService.editRole(requestRole);
        return ResultFactory.buildSuccessResultWithoutData("修改用户成功");
    }

    @GetMapping("/api/admin/role/perm")
    @ApiOperation("权限列表")
    public Result listPerms() {
        return ResultFactory.buildSuccessResult(permissionService.list());
    }


}
