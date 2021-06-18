package com.uwntek.worklog.dao.worklog;

import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.worklog.Monthlog;
import com.uwntek.worklog.entity.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MonthlogDAO extends JpaRepository<Monthlog, Long> {
    List<Monthlog> findAllByIsEffectiveAndDeptAndCreateMonth(int isEffective, Dept dept, Date month, Sort sort);

    Monthlog getMonthlogByIsEffectiveAndUserAndCreateMonth(int isEffective, User user, Date month);
}
