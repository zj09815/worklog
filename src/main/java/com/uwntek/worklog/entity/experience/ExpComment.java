package com.uwntek.worklog.entity.experience;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_exp_comment")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class ExpComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    String cmtContent;

    Long cmtUser;

    Long cmtExp;

    String cmtUserNameZh;
    int isEffective;

    Long parentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-DD HH:mm:ss", timezone = "GMT+8")
    Date createTime;

    @Transient
    List<ExpComment> replyComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCmtContent() {
        return cmtContent;
    }

    public void setCmtContent(String cmtContent) {
        this.cmtContent = cmtContent;
    }

    public Long getCmtUser() {
        return cmtUser;
    }

    public void setCmtUser(Long cmtUser) {
        this.cmtUser = cmtUser;
    }

    public Long getCmtExp() {
        return cmtExp;
    }

    public void setCmtExp(Long cmtExp) {
        this.cmtExp = cmtExp;
    }

    public String getCmtUserNameZh() {
        return cmtUserNameZh;
    }

    public void setCmtUserNameZh(String cmtUserNameZh) {
        this.cmtUserNameZh = cmtUserNameZh;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ExpComment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<ExpComment> replyComments) {
        this.replyComments = replyComments;
    }
}
