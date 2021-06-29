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
@Table(name = "tbl_task_start")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TaskStart {
    @Id
    @Column(name = "id")
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long taskId;
    private String taskName;
    private String taskContent;
    private Long taskMainPerson;
    private String taskMainPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date taskStartTime;
    private Long examinePerson;
    private String examinePersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date examineTime;
    private String examineComment;
    private Long approvalPerson;
    private String approvalPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date approvalTime;
    private String approvalComment;
    private Long ratifyPerson;
    private String ratifyPersonNameZh;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ratifyTime;
    private String ratifyComment;
    private int isEffective;


}
