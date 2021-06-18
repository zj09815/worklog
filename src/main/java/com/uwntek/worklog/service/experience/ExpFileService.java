package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.ExpFileDAO;
import com.uwntek.worklog.dao.experience.ExperienceDAO;
import com.uwntek.worklog.entity.experience.ExpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpFileService {
    @Autowired
    ExpFileDAO expFileDAO;
    @Autowired
    ExperienceDAO experienceDAO;

    public List<ExpFile> findAllByExpandIsEffective(Long expId, int isEffective) {
        return expFileDAO.findAllByFileExpAndIsEffective(expId, isEffective);
    }

    public void addOrUpdateFile(ExpFile expFile) {
        expFileDAO.save(expFile);
    }

    public ExpFile findById(Long id) {
        return expFileDAO.getOne(id);
    }

    public boolean isExist(Long id) {
        return expFileDAO.existsById(id);
    }

    public void delete(Long id) {
        ExpFile expFile = expFileDAO.getOne(id);
        expFile.setIsEffective(0);
        expFileDAO.save(expFile);
    }
}
