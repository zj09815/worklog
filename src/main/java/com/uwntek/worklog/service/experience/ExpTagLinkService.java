package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.ExpTagLinkDAO;
import com.uwntek.worklog.entity.experience.ExpTagLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ExpTagLinkService {
    @Autowired
    ExpTagLinkDAO expTagLinkDAO;

    public List<ExpTagLink> getTagsByExp(Long expid) {
        return expTagLinkDAO.findAllByExpIdAndIsEffective(expid, 1);
    }

    public List<ExpTagLink> getExpsByTag(Long tagid) {
        return expTagLinkDAO.findAllByTagId(tagid);
    }

    public void addOrUpdate(ExpTagLink expTagLink) {
        expTagLinkDAO.save(expTagLink);
    }

    public boolean isExist(Long expid, Long tagid, int isEffective) {
        return expTagLinkDAO.existsByExpIdAndTagIdAndIsEffective(expid, tagid, 1);
    }

    public void delete(Long expid, Long tagid, int isEffective) {
        expTagLinkDAO.deleteByExpIdAndAndTagIdAndIsEffective(expid, tagid, 1);
    }
}
