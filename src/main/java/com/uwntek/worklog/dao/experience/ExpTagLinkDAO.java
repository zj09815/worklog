package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.ExpTagLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpTagLinkDAO extends JpaRepository<ExpTagLink, Long> {
    List<ExpTagLink> findAllByExpIdAndIsEffective(Long expid, int isEffective);

    boolean existsByExpIdAndTagIdAndIsEffective(Long expid, Long tagid, int isEffective);

    void deleteByExpIdAndAndTagIdAndIsEffective(Long expid, Long tagid, int isEffective);

    Page<ExpTagLink> findAllByTagIdAndIsEffective(Long tagid, int isEffective, Pageable pageable);

    List<ExpTagLink> findAllByTagId(Long tagid);

}
