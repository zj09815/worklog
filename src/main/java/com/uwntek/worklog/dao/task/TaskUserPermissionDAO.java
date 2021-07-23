package com.uwntek.worklog.dao.task;

import com.uwntek.worklog.entity.task.TaskUserPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskUserPermissionDAO extends JpaRepository<TaskUserPermission,Long> {
    Page<TaskUserPermission> findAllByUserId(Long userId, Pageable pageable);
    boolean existsByTaskIdAndUserId(Long taskId, Long userId);
    Long deleteByTaskIdAndUserId(Long taskId, Long userId);
    TaskUserPermission getTaskUserPermissionByTaskIdAndUserId(Long taskId, Long userId);
    List<TaskUserPermission> findAllByTaskId(Long taskId);

    @Query("select t.taskId from TaskUserPermission t where t.userId = :userId")
    List<Long> getTaskIdByUserId(@Param("userId") Long userId);
}
