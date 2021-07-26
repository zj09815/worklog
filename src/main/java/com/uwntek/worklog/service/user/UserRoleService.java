package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.UserRoleDAO;
import com.uwntek.worklog.entity.user.Role;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.entity.user.UserRole;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    UserService userService;

    public List<UserRole> listAllByUserId(Long userId) {
        return userRoleDAO.findAllByUserIdAndIsEffective(userId, 1);
    }

    @Transactional
    public void saveRoleChanges(Long userId, List<Role> roles) {
        userRoleDAO.deleteAllByUserId(userId);
        List<UserRole> userRoles = new ArrayList<>();
        roles.forEach(role -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            userRoles.add(userRole);
        });
        userRoleDAO.saveAll(userRoles);
    }

    public List<UserRole> listAll(){
        return userRoleDAO.findAllByIsEffective(1);
    }


    public int getRoleId() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUserName(username);
        Long userId = user.getId();
        int roleId = 3;
        List<UserRole> userRoles = listAllByUserId(userId);
        for (UserRole userRole : userRoles) {
            if (userRole.getRoleId() == 1) {
                roleId = 1;
                break;
            } else if (userRole.getRoleId() == 2) {
                roleId = 2;
                break;
            }
        }
        return roleId;
    }
    public String getUserName() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUserName(username);
        return username;
    }

    public int getDept(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUserName(username);
        return user.getDept();
    }

}
