package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.ExpCommentDAO;
import com.uwntek.worklog.dao.experience.ExperienceDAO;
import com.uwntek.worklog.entity.experience.ExpComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ExpCommentService {
    @Autowired
    ExpCommentDAO expCommentDAO;
    @Autowired
    ExperienceDAO experienceDAO;

    public void handleExpComments(List<ExpComment> expComments) {
        for (ExpComment expComment : expComments) {
            List<ExpComment> children = findAllByParentId(expComment.getId());
            expComment.setReplyComments(children);
        }
        Iterator<ExpComment> expCommentIterator = expComments.iterator();
        while (expCommentIterator.hasNext()) {
            ExpComment expComment = expCommentIterator.next();
            if (expComment.getParentId() != 0) {
                expCommentIterator.remove();
            }
        }
    }

    // public List<ExpComment> findAll() {
    // List<ExpComment> expCommentList = expCommentDAO.findAllByIsEffective(1);
    // handleExpComments(expCommentList);
    // return expCommentList;
    // }

    public List<ExpComment> findAllByCmtExp(Long expId, int isEffective) {
        List<ExpComment> expCommentList = expCommentDAO.findAllByCmtExpAndIsEffective(expId, 1);
        handleExpComments(expCommentList);
        return expCommentList;
    }

    public List<ExpComment> findAllByParentId(Long parentId) {
        List<ExpComment> expCommentList = expCommentDAO.findAllByParentIdAndIsEffective(parentId, 1);
        return expCommentList;
    }

    public boolean idIsExist(Long id) {
        return expCommentDAO.existsById(id);
    }

    public boolean parentIdIsExist(Long parentId) {
        return expCommentDAO.existsByParentId(parentId);
    }

    public void addOrUpdateExpComment(ExpComment expComment) {
        expCommentDAO.save(expComment);
    }

    public void deleteExpComment(Long id) {
        expCommentDAO.deleteById(id);
    }

    public void deleteExpCommentByParentId(Long parentId) {
        expCommentDAO.deleteByParentId(parentId);
    }

    public ExpComment get(Long id) {
        return expCommentDAO.getOne(id);
    }
}
