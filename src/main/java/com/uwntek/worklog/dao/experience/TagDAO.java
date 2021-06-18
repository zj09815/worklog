package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagDAO extends JpaRepository<Tag, Long> {
    List<Tag> findAllByIsEffective(int isEffective);

    boolean existsByTagNameAndIsEffective(String tagName, int isEffective);

    List<Tag> findAllByParentIdAndIsEffective(Long parentId, int isEffective);

    Tag findByTagNameAndIsEffective(String tagName, int isEffective);

    void deleteByParentId(Long parentId);

    boolean existsByParentId(Long parentId);
}
