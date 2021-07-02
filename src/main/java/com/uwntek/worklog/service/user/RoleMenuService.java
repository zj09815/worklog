package com.uwntek.worklog.service.user;


import com.uwntek.worklog.dao.user.RoleMenuDAO;
import com.uwntek.worklog.entity.user.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleMenuService {
    @Autowired
    RoleMenuDAO roleMenuDAO;

    public List<RoleMenu> findAllByRoleId(int roleId){
        return roleMenuDAO.findAllByRoleId(roleId);
    }

    public List<RoleMenu> findAllByRoleId(List<Integer> roleIds){
        return roleMenuDAO.findAllByRoleIdIn(roleIds);
    }

    public void  save(RoleMenu roleMenu){
        roleMenuDAO.save(roleMenu);
    }

    @Modifying
    @Transactional
    public void updateRoleMenu(int roleId, Map<String, List<Integer>> menuIds){
        roleMenuDAO.deleteAllByRoleId(roleId);
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Integer mid: menuIds.get("menusIds")){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(mid);
            roleMenu.setRoleId(roleId);
            roleMenus.add(roleMenu);
        }
        roleMenuDAO.saveAll(roleMenus);
    }
}
