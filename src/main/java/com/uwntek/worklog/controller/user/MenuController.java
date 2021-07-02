package com.uwntek.worklog.controller.user;

import com.uwntek.worklog.reult.Result;
import com.uwntek.worklog.reult.ResultFactory;
import com.uwntek.worklog.service.user.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/api/admin/menu")
    public Result menu(){
        return ResultFactory.buildSuccessResult(menuService.getMenusByCurrentUser());
    }

    @GetMapping("/api/admin/role/menu/{roleid}")
    public Result listAllMenus(@PathVariable int roleid){
        return ResultFactory.buildSuccessResult(menuService.getMenusByRoleId(roleid));
    }
}
