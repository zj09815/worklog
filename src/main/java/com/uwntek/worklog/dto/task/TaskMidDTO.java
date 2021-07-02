package com.uwntek.worklog.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class TaskMidDTO {
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long taskId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskMidTime;
    private int taskMidId;
    private String taskMidContent;
    private String taskMidConclusion;
    private String taskMidExamineVerify;
    private String taskMidExamineSummary;
    private String taskMidExamineConclusion;
    private Long taskMidExaminePerson;
    private String taskMidExaminePersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskMidExamineTime;
    private Long taskMidApprovalPerson;
    private String taskMidApprovalPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskMidApprovalTime;
    private String taskMidApprovalComment;
    private int isEffective;
    private String taskMidStatus;
    private String taskNameInfo;
    private int taskPeriod;
    private String taskDept;
    private String taskAllTime;
}
