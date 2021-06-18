package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.TagDAO;
import com.uwntek.worklog.entity.experience.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagDAO tagDAO;

    public void handletags(List<Tag> tags) {
        for (Tag tag : tags) {
            List<Tag> children = findAllByParentId(tag.getId());
            tag.setChildren(children);
        }
        Iterator<Tag> tagIterator = tags.iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            if (tag.getParentId() != 0) {
                tagIterator.remove();
            }
        }
    }

    public List<Tag> findAll() {
        List<Tag> tagList = tagDAO.findAllByIsEffective(1);
        handletags(tagList);
        return tagList;
    }

    public void addOrUpdate(Tag tag) {
        tagDAO.save(tag);
    }

    public void delete(Long id) {
        Tag tag = tagDAO.getOne(id);
        tag.setIsEffective(0);
        tagDAO.save(tag);
    }

    public List<Tag> findAllByParentId(Long parentId) {
        return tagDAO.findAllByParentIdAndIsEffective(parentId, 1);
    }

    public boolean isExist(Long id) {
        return tagDAO.existsById(id);
    }

    public Tag findByid(Long id) {
        return tagDAO.getOne(id);
    }

    public boolean tagNameisExist(String tagName) {
        return tagDAO.existsByTagNameAndIsEffective(tagName, 1);
    }

    public Tag findByTagName(String tagName) {
        return tagDAO.findByTagNameAndIsEffective(tagName, 1);
    }

    public void deleteByParentId(Long parentId) {
        List<Tag> tagList = findAllByParentId(parentId);
        for (Tag tag : tagList) {
            tag.setIsEffective(0);
        }
    }
}
