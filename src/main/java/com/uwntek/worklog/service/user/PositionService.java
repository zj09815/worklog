package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.PositionDAO;
import com.uwntek.worklog.entity.user.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Autowired
    PositionDAO positionDAO;

    public Position getPositionName(int id, int isEffective) {
        return positionDAO.getByIdAndIsEffective(id, isEffective);
    }

    public Position getPosition(int id){
        return positionDAO.findById(id).orElse(new Position());
    }

    //有效数据
    public List<Position> findAllByIsEffective(int isEffective) {
        return positionDAO.findAllByIsEffective(isEffective);
    }

    //基本方法
    public void addOrUpdate(Position position) {
        positionDAO.save(position);
    }

    public void deleteById(int id) {
        positionDAO.deleteById(id);
    }
}
