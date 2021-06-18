package com.uwntek.worklog.dao.worklog;


import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.entity.worklog.Worklog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface WorklogDAO extends JpaRepository<Worklog, Long> {
    public List<Worklog> findByUserAndIsEffective(User user, int isEffective);

    public List<Worklog> findByDeptAndIsEffective(Dept dept, int isEffective);

    public List<Worklog> findByWorklogDateBetweenAndUserAndIsEffective(Date startDate, Date endDate, User user, int isEffective, Sort sort);

    public List<Worklog> findByWorklogDateBetweenAndDeptAndIsEffective(Date startDate, Date endDate, Dept dept, int isEffective, Sort sort);
}
