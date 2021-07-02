package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuDAO extends JpaRepository<Menu,Integer> {
    Menu findById(int id);
    List<Menu> findAllByParentId(int parentId);
}
