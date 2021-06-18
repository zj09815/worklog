package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.ExpComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpCommentDAO extends JpaRepository<ExpComment, Long> {
    List<ExpComment> findAllByIsEffective(int isEffective);

    List<ExpComment> findAllByParentIdAndIsEffective(Long parentId, int isEffective);

    List<ExpComment> findAllByCmtExpAndIsEffective(Long cmtExp, int isEffective);

    List<ExpComment> findAllByCmtUserAndIsEffective(Long user, int isEffective);

    void deleteByParentId(Long parentId);

    boolean existsByParentId(Long parentId);
}
