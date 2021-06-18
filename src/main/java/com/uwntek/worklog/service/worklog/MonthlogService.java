package com.uwntek.worklog.service.worklog;

import com.uwntek.worklog.dao.worklog.MonthlogDAO;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.worklog.Monthlog;
import com.uwntek.worklog.entity.user.User;
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
public class MonthlogService {
    @Autowired
    MonthlogDAO monthlogDAO;
    @Autowired
    DeptService deptService;
    @Autowired
    UserService userService;

    public List<Monthlog> findByDept(int deptid, Date month) {
        Dept dept = deptService.get(deptid);
        Sort user = Sort.by(Sort.Direction.ASC, "user");
        Sort createMonth = Sort.by(Sort.Direction.ASC, "createMonth");
        return monthlogDAO.findAllByIsEffectiveAndDeptAndCreateMonth(1, dept, month, user.and(createMonth));
    }

    public Monthlog getByUser(Long userid, Date month) {
        User user = userService.get(userid);
        return monthlogDAO.getMonthlogByIsEffectiveAndUserAndCreateMonth(1, user, month);
    }

    public void addOrUpdateMonthlog(Monthlog monthlog) {
        String dateStr = new SimpleDateFormat("yyyyMM").format(monthlog.getCreateMonth());
        String userStr = new DecimalFormat("000000").format(monthlog.getUser_fk());
        String idStr = dateStr + userStr;
        monthlog.setId(Long.valueOf(idStr));
        monthlog.setDeptName(deptService.get(monthlog.getDept_fk()).getDeptName());
        monthlog.setMonthlogCreater(userService.get(monthlog.getUser_fk()).getUserName());
        monthlog.setCreateUserName(userService.get(monthlog.getUser_fk()).getUserNameZh());
        monthlogDAO.save(monthlog);
    }
}
