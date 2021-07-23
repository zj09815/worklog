package com.uwntek.worklog.entity.task;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_task_user_permission")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TaskUserPermission {
    @Id
    @Column(name = "id")
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private Long taskId;
    private Long userId;
    public  TaskUserPermission(){
        setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }
    public TaskUserPermission(Long taskId, Long userId){
        setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        setTaskId(taskId);
        setUserId(userId);
    }
}
