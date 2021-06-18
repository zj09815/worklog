package com.uwntek.worklog.service.worklog;

import com.uwntek.worklog.dao.worklog.WorklogDAO;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.entity.worklog.Worklog;
import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WorklogService {
    @Autowired
    WorklogDAO worklogDAO;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;

    public List<Worklog> getWorklogByUser(Long userid) {
        User user = userService.get(userid);
        return worklogDAO.findByUserAndIsEffective(user, 1);
    }

    public Worklog getById(Long id) {
        return worklogDAO.getOne(id);
    }

    public List<Worklog> getWorklogByDept(Date startDate, Date endDate, int deptid) {
        Dept dept = deptService.get(deptid);
        return worklogDAO.findByWorklogDateBetweenAndDeptAndIsEffective(startDate, endDate, dept, 1, Sort.by(Sort.Direction.ASC, "user").and(Sort.by(Sort.Direction.ASC, "worklogDate")));
    }

    public List<Worklog> getWorklogByDate(Date startDate, Date endDate, Long createUser) {
        User user = userService.get(createUser);
        return worklogDAO.findByWorklogDateBetweenAndUserAndIsEffective(startDate, endDate, user, 1, Sort.by(Sort.Direction.ASC, "worklogDate"));
    }

    public boolean isExist(Long id) {
        return worklogDAO.existsById(id);
    }

    public void addOrUpdateWorklog(Worklog worklog) {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(worklog.getWorklogDate());
        String userStr = new DecimalFormat("000000").format(worklog.getUser_fk());
        String idStr = dateStr + userStr;
        worklog.setId(Long.valueOf(idStr));
        worklog.setDeptName(deptService.get(worklog.getDept_fk()).getDeptName());
        worklog.setWorklogCreater(userService.get(worklog.getUser_fk()).getUserName());
        worklog.setCreateUserName(userService.get(worklog.getUser_fk()).getUserNameZh());
        worklogDAO.save(worklog);
    }

}
