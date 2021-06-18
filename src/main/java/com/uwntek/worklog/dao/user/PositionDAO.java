package com.uwntek.worklog.dao.user;

import com.uwntek.worklog.entity.user.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionDAO extends JpaRepository<Position, Integer> {
    Position getByIdAndIsEffective(int id, int isEffective);

    List<Position> findAllByIsEffective(int isEffective);
}
