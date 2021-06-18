package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.dto.UserDTO;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@RequestMapping
@Api(value = "用户信息")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    @GetMapping("/api/admin/userListByDept")
    @ApiOperation(value = "按部门筛选用户")
    public Result listByDept(int dept) {
        List<UserDTO> userDTOList = userService.listByDept(dept, 1);
        if (deptService.idIsExist(dept)) {
            return ResultFactory.buildSuccessResult(userDTOList);
        } else {
            return ResultFactory.buildFailResult("部门不存在");
        }
    }

    @GetMapping("/api/admin/userList")
    @ApiOperation(value = "所有用户")
    public Result list() {
        List<UserDTO> userDTOList = userService.list();
        return ResultFactory.buildSuccessResult(userDTOList);
    }

    @PostMapping("/api/admin/addOrUpdateUser")
    @ApiOperation(value = "更新用户", notes = "id存在时更新")

    public Result addOrUpdateUser(@RequestBody User user) {
        if (!userService.idIsExist(user.getId())){
            return ResultFactory.buildFailResult("用户不存在");
        }
        if (!userService.positionIsExist(user.getPosition())) {
            return ResultFactory.buildFailResult("权限错误");
        } else if (!deptService.idIsExist(user.getDept_fk())) {
            return ResultFactory.buildFailResult("部门不存在");
        } else {
            try {
                userService.addOrUpdateUser(user);
                return ResultFactory.buildSuccessResultWithoutData("用户新增或修改成功");
            } catch (Exception e) {
                return ResultFactory.buildFailResult("添加失败,请检查信息是否正确完整");
            }
        }
    }

    @GetMapping("/api/admin/setPassword")
    @ApiOperation("修改密码")
    public Result setPassword(@RequestParam Long userid, @RequestParam String password){
        if (!userService.idIsExist(userid)){
            return ResultFactory.buildFailResult("用户不存在");
        }
        User user = userService.get(userid);
        String userName = user.getUserName();
        userName = HtmlUtils.htmlEscape(userName);
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.addOrUpdateUser(user);
        return ResultFactory.buildSuccessResult("用户密码修改成功");
    }

    @GetMapping("/api/admin/getUserNameZhById")
    @ApiOperation("通过id获取用户名")
    public Result getUserNameZhById(Long id) {
        if (userService.idIsExist(id)) {
            return ResultFactory.buildSuccessResultWithoutData(userService.get(id).getUserNameZh());
        } else {
            return ResultFactory.buildFailResult("用户id不存在");
        }
    }

    @GetMapping("/api/admin/existsByUserName")
    @ApiOperation(value = "用于新增用户时判断用户是否存在")
    public Result existsByUserName(String userName) {
        return ResultFactory.buildSuccessResult(userService.isExist(userName));
    }

    @PostMapping("/api/admin/deleteUser")
    @ApiOperation(value = "删除用户", notes = "将用户的isEffective字段置0")
    public Result deleteUser(Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResultFactory.buildFailResult("用户不存在");
        } else if (user.getIsEffective() == 0) {
            return ResultFactory.buildFailResult("用户已删除");
        } else {
            user.setIsEffective(0);
            userService.addOrUpdateUser(user);
            return ResultFactory.buildSuccessResultWithoutData("删除成功");
        }
    }


    @GetMapping("/api/admin/user/{userid}")
    @ApiOperation(value = "获取用户信息", notes = "输入userid")
    public Result getUserInfo(@RequestParam Long userid){
        if (!userService.idIsExist(userid)){
            return ResultFactory.buildFailResult("用户不存在");
        }

        return ResultFactory.buildSuccessResult(userService.getUserInfo(userid));
    }

}
