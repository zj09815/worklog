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
@Table(name = "tbl_task_mid")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TaskMid {
    @Id
    @Column(name = "id")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskMidExamineTime;
    private Long taskMidApprovalPerson;
    private String taskMidApprovalPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date taskMidApprovalTime;
    private String taskMidApprovalComment;
    private int isEffective;
    private String taskMidStatus;

}
