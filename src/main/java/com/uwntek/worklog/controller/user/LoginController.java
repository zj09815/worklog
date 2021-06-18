package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.entity.user.UserNameOnly;
import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping
@Api(value = "登录")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/api/login")
    @ApiOperation(notes = "用户登录", value = "用户登录")
    @ResponseBody
    /*
     * public Result login(@RequestBody User requestUser){ String userName =
     * requestUser.getUserName(); Subject subject = SecurityUtils.getSubject();
     * UsernamePasswordToken usernamePasswordToken = new
     * UsernamePasswordToken(userName,requestUser.getPassword());
     * usernamePasswordToken.setRememberMe(true); try {
     * subject.login(usernamePasswordToken); User user =
     * userService.findByUserName(userName); Long userId =user.getId(); int
     * userDept= user.getDept(); String userNameZh = user.getUserNameZh();
     * UserNameOnly outUser = new UserNameOnly(); outUser.setId(userId);
     * outUser.setUserName(userName); outUser.setDept(userDept);
     * outUser.setUserNameZh(userNameZh); return
     * ResultFactory.buildSuccessResult(outUser); } catch
     * (IncorrectCredentialsException e) { return
     * ResultFactory.buildFailResult("密码错误"); } catch (UnknownAccountException e) {
     * return ResultFactory.buildFailResult("账号不存在"); } }
     */
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return ResultFactory.buildFailResult("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return ResultFactory.buildFailResult("密码错误");
        } catch (LockedAccountException lae) {
            return ResultFactory.buildFailResult("账户已锁定");
        } catch (AuthenticationException ae) {
            return ResultFactory.buildFailResult("用户名或密码不正确");
        }
        if (subject.isAuthenticated()) {
            User user = userService.findByUserName(username);
            Long userId = user.getId();
            int userDept = user.getDept_fk();
            String userNameZh = user.getUserNameZh();
            UserNameOnly outUser = new UserNameOnly();
            outUser.setId(userId);
            outUser.setUserName(username);
            outUser.setDept(userDept);
            outUser.setUserNameZh(userNameZh);
            return ResultFactory.buildSuccessResult(outUser);
        } else {
            token.clear();
            return ResultFactory.buildFailResult("登录失败");
        }
    }

    @GetMapping("/api/logout")
    @ResponseBody
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "登出成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PostMapping("/api/register")
    @ApiOperation(notes = "用户注册", value = "用户注册")
    @ResponseBody
    public Result register(@RequestBody User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        userName = HtmlUtils.htmlEscape(userName);

        boolean exist = userService.isExist(userName);
        if (exist) {
            String message = "用户名已被使用";
            return ResultFactory.buildFailResult(message);
        }
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.addOrUpdateUser(user);
        return ResultFactory.buildSuccessResult(user);
    }

    @GetMapping("/api/authentication")
    @ApiOperation(notes = "身份认证", value = "身份认证")
    public String authentication() {
        return "身份认证成功";
    }

    @GetMapping("/nowhere")
    @ApiOperation("没有登录时的跳转")
    public String nowhere() {
        return "请登录后重试";
    }

}
