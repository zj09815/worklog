package com.uwntek.worklog.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_task_check")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TaskCheck {
    @Id
    @Column(name = "id")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskCheckExamineTime;
    private Long taskCheckApprovalPerson;
    private String taskCheckApprovalPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskCheckApprovalTime;
    private String taskCheckApprovalComment;
    private int isEffective;
}
