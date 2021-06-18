package com.uwntek.worklog.service.experience;

import com.uwntek.worklog.dao.experience.ClassifyDAO;
import com.uwntek.worklog.dao.experience.ExperienceDAO;
import com.uwntek.worklog.entity.experience.Classify;
import com.uwntek.worklog.entity.experience.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ClassifyService {
    @Autowired
    ClassifyDAO classifyDAO;
    @Autowired
    ExperienceDAO experienceDAO;

    public void handleclassifys(List<Classify> classifys) {
        for (Classify classify : classifys) {
            List<Classify> children = findAllByParentId(classify.getId());
            classify.setChildren(children);
        }
        Iterator<Classify> classifyIterator = classifys.iterator();
        while (classifyIterator.hasNext()) {
            Classify classify = classifyIterator.next();
            if (classify.getParentId() != 0) {
                classifyIterator.remove();
            }
        }
    }

    public List<Classify> findAll() {
        List<Classify> classifyList = classifyDAO.findAllByIsEffective(1);
        handleclassifys(classifyList);
        return classifyList;
    }

    public void addOrUpdate(Classify classify) {
        classifyDAO.save(classify);
        List<Experience> allByClassifyId = experienceDAO.findAllByClassifyId(classify.getId());
        for (Experience e:allByClassifyId){
            e.setExpClassify(classify.getClassifyName());
            experienceDAO.save(e);
        }
    }

    public void delete(Long id) {
        Classify classify = classifyDAO.getOne(id);
        classify.setIsEffective(0);
        classifyDAO.save(classify);

    }

    public List<Classify> findAllByParentId(Long parentId) {
        return classifyDAO.findAllByParentIdAndIsEffective(parentId, 1);
    }

    public boolean isExist(Long id) {
        return classifyDAO.existsById(id);
    }

    public Classify findByid(Long id) {
        return classifyDAO.getOne(id);
    }

    public boolean classifyNameisExist(String classifyName) {
        return classifyDAO.existsByClassifyNameAndIsEffective(classifyName, 1);
    }

    public Classify findByClassifyName(String classifyName) {
        return classifyDAO.findByClassifyNameAndIsEffective(classifyName, 1);
    }

    public void deleteByParentId(Long parentId) {
        List<Classify> classifyList = findAllByParentId(parentId);
        for (Classify classify : classifyList) {
            classify.setIsEffective(0);
        }

    }
}
