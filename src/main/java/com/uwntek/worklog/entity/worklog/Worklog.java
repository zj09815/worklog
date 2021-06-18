package com.uwntek.worklog.entity.worklog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_worklog")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Worklog {
    @Lob
    @Column(columnDefinition = "text")
    public String worklog;
    @Id
    @Column(name = "id")
    Long id;
    @ManyToOne
    @JoinColumn(name = "create_user")
    User user;
    int isEffective;
    @Column(name = "create_user", updatable = false, insertable = false)
    Long user_fk;
    @ManyToOne
    @JoinColumn(name = "dept")
    Dept dept;
    @Column(name = "dept", updatable = false, insertable = false)
    int dept_fk;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date worklogDate;
    String worklogCreater;
    String createUserName;
    String deptName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorklog() {
        return worklog;
    }

    public void setWorklog(String worklog) {
        this.worklog = worklog;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }
    /*
     * public User getUser(){ return user; }
     *
     */

    public void setUser(User user) {
        this.user = user;
    }
    /*
     * public Dept getDept() { return dept; }
     *
     */

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Date getWorklogDate() {
        return worklogDate;
    }

    public void setWorklogDate(Date worklogDate) {
        this.worklogDate = worklogDate;
    }

    public Long getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(Long user_fk) {
        this.user_fk = user_fk;
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

    public String getWorklogCreater() {
        return worklogCreater;
    }

    public void setWorklogCreater(String worklogCreater) {
        this.worklogCreater = worklogCreater;
    }
}
