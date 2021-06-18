package com.uwntek.worklog.entity.experience;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.service.user.UserService;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_exp_opt_log")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class ExpOptLog {

    @Id
    @Column(name = "id")
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private Long expId;
    private String optContent;
    private Long optUserId;
    private String optUserNameZh;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date optTime;

    public ExpOptLog(Long expId,Long optUserId,String optUserNameZh, String optContent) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.optTime = new Date();
        this.expId = expId;
        this.optUserId = optUserId;
        this.optUserNameZh = optUserNameZh;
        this.optContent = optContent;
    }

    public ExpOptLog() {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.optTime = new Date();
    }
}
