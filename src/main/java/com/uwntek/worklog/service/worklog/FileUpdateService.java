package com.uwntek.worklog.service.worklog;

import com.uwntek.worklog.dao.worklog.FileUpdateDAO;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.worklog.FileUpdate;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileUpdateService {
    @Autowired
    FileUpdateDAO fileDAO;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    public List<FileUpdate> findAllByUser(Long userid, int isEffective) {
        User user = userService.get(userid);
        return fileDAO.findAllByUserAndIsEffective(user, 1);
    }

    public List<FileUpdate> findAllByUserAndMonth(Long userid, Date month, int isEffective) {
        User user = userService.get(userid);
        return fileDAO.findAllByUserAndMonthAndIsEffective(user, month, 1);
    }

    public FileUpdate findById(Long id) {
        return fileDAO.getOne(id);
    }

    public List<FileUpdate> findAllByDeptAdnMonth(int deptid, Date month, int isEffective) {
        Dept dept = deptService.get(deptid);
        return fileDAO.findAllByDeptAndMonthAndIsEffective(dept, month, 1);
    }

    public void addOrUpdateFile(FileUpdate file) {
        file.setDept(deptService.get(userService.get(file.getUser_fk()).getDept_fk()));
        file.setDept_fk(deptService.get(userService.get(file.getUser_fk()).getDept_fk()).getId());
        file.setCreateUserName(userService.get(file.getUser_fk()).getUserNameZh());
        file.setDeptName(deptService.get(file.getDept_fk()).getDeptName());
        fileDAO.save(file);
    }
}
