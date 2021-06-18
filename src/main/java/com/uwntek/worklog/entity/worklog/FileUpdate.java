package com.uwntek.worklog.entity.worklog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_file_update")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class FileUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @ManyToOne
    @JoinColumn(name = "create_user")
    User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMM", timezone = "GMT+8")
    Date month;
    int isEffective;
    @Column(name = "create_user", updatable = false, insertable = false)
    Long user_fk;
    String name;
    String url;
    @ManyToOne
    @JoinColumn(name = "dept")
    Dept dept;
    @Column(name = "dept", updatable = false, insertable = false)
    int dept_fk;
    String createUserName;
    String deptName;
    String oriName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public User getUser() {
        return user;
    }

 */

    public void setUser(User user) {
        this.user = user;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public Long getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(Long user_fk) {
        this.user_fk = user_fk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
/*
    public Dept getDept() {
        return dept;
    }

 */

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public int getDept_fk() {
        return dept_fk;
    }

    public void setDept_fk(int dept_fk) {
        this.dept_fk = dept_fk;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOriName() {
        return oriName;
    }

    public void setOriName(String oriName) {
        this.oriName = oriName;
    }
}
