package com.uwntek.worklog.dao.worklog;

import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.worklog.FileUpdate;
import com.uwntek.worklog.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FileUpdateDAO extends JpaRepository<FileUpdate, Long> {
    List<FileUpdate> findAllByUserAndIsEffective(User user, int isEffective);

    List<FileUpdate> findAllByUserAndMonthAndIsEffective(User user, Date month, int isEffective);

    List<FileUpdate> findAllByDeptAndMonthAndIsEffective(Dept dept, Date month, int isEffective);

}
