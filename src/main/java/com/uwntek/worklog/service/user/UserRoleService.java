package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.UserRoleDAO;
import com.uwntek.worklog.entity.user.Role;
import com.uwntek.worklog.entity.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    UserRoleDAO userRoleDAO;

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

}
