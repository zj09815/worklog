package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.ExpFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpFileDAO extends JpaRepository<ExpFile, Long> {
    List<ExpFile> findAllByFileExpAndIsEffective(Long fileExp, int isEffective);

}
