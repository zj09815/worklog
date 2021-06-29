package com.uwntek.worklog.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "tbl_task")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Task {
    @Id
    @Column(name = "id")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date taskStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date taskEndTime;
    private int taskPeriod;
}
