package com.uwntek.worklog.service.user;

import com.uwntek.worklog.dao.user.DeptDAO;
import com.uwntek.worklog.entity.user.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeptService {
    @Autowired
    DeptDAO deptDAO;
    @Autowired
    UserRoleService userRoleService;

    public void handleDepts(List<Dept> depts) {
        for (Dept dept : depts) {
            List<Dept> children = findAllByParentId(dept.getId(), 1);
            dept.setChildren(children);
        }
        Iterator<Dept> deptIterator = depts.iterator();
        while (deptIterator.hasNext()) {
            Dept dept = deptIterator.next();
            if (dept.getParentId() != 0) {
                deptIterator.remove();
            }
        }
    }

    public List<Dept> findAllByIsEffective(int isEffective) {
        List<Dept> deptList = deptDAO.findAllByIsEffective(isEffective);
        handleDepts(deptList);
        return deptList;
    }


    public List<Dept> findAllByParentId(int parentId, int isEffective) {
        return deptDAO.findAllByParentIdAndIsEffective(parentId, isEffective);
    }

    public boolean idIsExist(int id) {
        return deptDAO.existsById(id);
    }

    public boolean parentIdIsExist(int panrentId) {
        return deptDAO.existsByParentId(panrentId);
    }

    public void addOrUpdateDept(Dept dept) {
        deptDAO.save(dept);
    }

    public void deleteDept(int id) {
        deptDAO.deleteById(id);
    }

    public void deleteDeptByParentId(int parentId) {
        deptDAO.deleteByParentId(parentId);
    }

    public Dept get(int id) {
        Dept dept = deptDAO.findById(id).orElse(null);
        return dept;
    }
}
