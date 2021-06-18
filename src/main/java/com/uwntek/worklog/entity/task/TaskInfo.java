package com.uwntek.worklog.entity.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;

import java.util.List;

//返回结果，包含参与人
@Data
public class TaskInfo{
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private String taskName;
    private Long taskMainPerson;
    private String taskMainPersonNameZh;
    private String processId;
    private int isEffective;
    private int midTimes;
    private int taskDept;
    private List<String> userAdd;
}