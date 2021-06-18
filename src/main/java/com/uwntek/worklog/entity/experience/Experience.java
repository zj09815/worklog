package com.uwntek.worklog.entity.experience;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.uwntek.worklog.entity.user.User;
import com.uwntek.worklog.util.LongJsonDeserializer;
import com.uwntek.worklog.util.LongJsonSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_experience")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Experience {
    @Id
    @Column(name = "id")
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    Long id;
    String expTitle;
    String expIndex;
    @Lob
    @Column(columnDefinition = "text")
    String expContent;
    @ManyToOne
    @JoinColumn(name = "create_user")
    User createUser;
    String createUserName;
    String createUserNameZh;
    String expVideo;
    int isEffective;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "update_time")
    Date updateTime;
    Long classifyId;
    String expClassify;
    String verifyStatus;

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getExpClassify() {
        return expClassify;
    }

    public void setExpClassify(String expClassify) {
        this.expClassify = expClassify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpTitle() {
        return expTitle;
    }

    public void setExpTitle(String expTitle) {
        this.expTitle = expTitle;
    }

    public String getExpIndex() {
        return expIndex;
    }

    public void setExpIndex(String expIndex) {
        this.expIndex = expIndex;
    }

    public String getExpContent() {
        return expContent;
    }

    public void setExpContent(String expContent) {
        this.expContent = expContent;
    }

    public String getExpVideo() {
        return expVideo;
    }

    public void setExpVideo(String expVideo) {
        this.expVideo = expVideo;
    }

    public User getCreateUser() {
        User userIdOnly = new User();
        userIdOnly.setId(createUser.getId());
        return userIdOnly;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserNameZh() {
        return createUserNameZh;
    }

    public void setCreateUserNameZh(String createUserNameZh) {
        this.createUserNameZh = createUserNameZh;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
