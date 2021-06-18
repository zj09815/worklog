package com.uwntek.worklog.entity.worklog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uwntek.worklog.entity.user.Dept;
import com.uwntek.worklog.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_monthlog")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Monthlog {
    @Id
    @Column(name = "id")
    Long id;
    @Lob
    @Column(columnDefinition = "text")
    String monthlog1;
    @Lob
    @Column(columnDefinition = "text")
    String monthlog2;
    @Lob
    @Column(columnDefinition = "text")
    String monthlog3;
    @Lob
    @Column(columnDefinition = "text")
    String monthlog4;
    @Lob
    @Column(columnDefinition = "text")
    String monthlogconclusion;
    @Lob
    @Column(columnDefinition = "text")
    String monthlogplan;
    @ManyToOne
    @JoinColumn(name = "create_user")
    User user;
    @ManyToOne
    @JoinColumn(name = "dept")
    Dept dept;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "GMT+8")
    Date createMonth;
    int isEffective;
    @Column(name = "create_user", updatable = false, insertable = false)
    Long user_fk;
    @Column(name = "dept", updatable = false, insertable = false)
    int dept_fk;
    String monthlogCreater;
    String createUserName;
    String deptName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDept_fk() {
        return dept_fk;
    }

    public void setDept_fk(int dept_fk) {
        this.dept_fk = dept_fk;
    }

    public Long getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(Long user_fk) {
        this.user_fk = user_fk;
    }


    /*
        public User getUser() {
            return user;
        }
    */
    public void setUser(User user) {
        this.user = user;
    }

    /*
        public Dept getDept() {
            return dept;
        }
    */
    public void setDept(Dept dept) {
        this.dept = dept;
    }


    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public String getMonthlog1() {
        return monthlog1;
    }

    public void setMonthlog1(String monthlog1) {
        this.monthlog1 = monthlog1;
    }

    public String getMonthlog2() {
        return monthlog2;
    }

    public void setMonthlog2(String monthlog2) {
        this.monthlog2 = monthlog2;
    }

    public String getMonthlog3() {
        return monthlog3;
    }

    public void setMonthlog3(String monthlog3) {
        this.monthlog3 = monthlog3;
    }

    public String getMonthlog4() {
        return monthlog4;
    }

    public void setMonthlog4(String monthlog4) {
        this.monthlog4 = monthlog4;
    }

    public String getMonthlogconclusion() {
        return monthlogconclusion;
    }

    public void setMonthlogconclusion(String monthlogconclusion) {
        this.monthlogconclusion = monthlogconclusion;
    }

    public String getMonthlogplan() {
        return monthlogplan;
    }

    public void setMonthlogplan(String monthlogplan) {
        this.monthlogplan = monthlogplan;
    }

    public Date getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(Date createMonth) {
        this.createMonth = createMonth;
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

    public String getMonthlogCreater() {
        return monthlogCreater;
    }

    public void setMonthlogCreater(String monthlogCreater) {
        this.monthlogCreater = monthlogCreater;
    }
}
