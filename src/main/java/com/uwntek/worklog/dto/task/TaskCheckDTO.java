package com.uwntek.worklog.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class TaskCheckDTO {
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long taskId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskCheckTime;
    private String taskCheckContent;
    private String taskCheckConclusion;
    private String taskCheckExamineVerify;
    private String taskCheckExamineSummary;
    private String taskCheckExamineConclusion;
    private Long taskCheckExaminePerson;
    private String taskCheckExaminePersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskCheckExamineTime;
    private Long taskCheckApprovalPerson;
    private String taskCheckApprovalPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskCheckApprovalTime;
    private String taskCheckApprovalComment;
    private int isEffective;
    private String taskNameInfo;
    private int taskPeriod;
    private String taskDept;
    private String taskAllTime;
}
