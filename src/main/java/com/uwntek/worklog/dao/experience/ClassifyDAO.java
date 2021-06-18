package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.Classify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassifyDAO extends JpaRepository<Classify,Long> {
    List<Classify> findAllByIsEffective(int isEffective);

    boolean existsByClassifyNameAndIsEffective(String classifyName, int isEffective);

    List<Classify> findAllByParentIdAndIsEffective(Long parentId, int isEffective);

    Classify findByClassifyNameAndIsEffective(String classifyName, int isEffective);

    void deleteByParentId(Long parentId);

    boolean existsByParentId(Long parentId);
}
