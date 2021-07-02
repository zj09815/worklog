package com.uwntek.worklog.service.user;


import com.uwntek.worklog.dao.user.MenuDAO;
import com.uwntek.worklog.entity.user.*;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuService {
    @Autowired
    MenuDAO menuDAO;
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleMenuService roleMenuService;

    public void handleMenus(List<Menu> menus){
        menus.forEach(menu -> {
            List<Menu> children = findAllByParentId(menu.getId());
            menu.setChildren(children);
                }
        );
        menus.removeIf(menu -> menu.getParentId()!= 0);
    }


    public List<Menu> findAllByParentId(int parentId){
        return menuDAO.findAllByParentId(parentId);
    }

    public List<Menu> getMenusByCurrentUser(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUserName(username);

        List<Integer> roleIds = userRoleService.listAllByUserId(user.getId())
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());

        List<Integer> menuIds = roleMenuService.findAllByRoleId(roleIds)
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        List<Menu> menus = menuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        handleMenus(menus);
        return menus;
    }

    public List<Menu> getMenusByRoleId(int roleId){
        List<Integer> menuIds = roleMenuService.findAllByRoleId(roleId)
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Menu> menus = menuDAO.findAllById(menuIds);

        handleMenus(menus);
        return menus;
    }


}
