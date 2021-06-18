package com.uwntek.worklog.dao.experience;

import com.uwntek.worklog.entity.experience.ExpOptLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpOptLogDAO extends JpaRepository<ExpOptLog, Long> {
    List<ExpOptLog> findAllByExpId(Long expId, Sort sort);
}
