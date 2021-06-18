package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    List<User> findAllByUserNameAndIsEffective(String userName, int isEffective);

    User findByUserName(String userName);

    List<User> findAllByIsEffective(int isEffective);

    User findByUserNameAndPassword(String userName, String password);

    List<User> findByDeptAndIsEffective(int dept, int isEffective);

    boolean existsByPosition(int position);

}
