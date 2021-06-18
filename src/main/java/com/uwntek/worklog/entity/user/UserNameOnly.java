package com.uwntek.worklog.entity.user;

public class UserNameOnly {
    Long id;
    String userName;
    String userNameZh;
    int dept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getUserNameZh() {
        return userNameZh;
    }

    public void setUserNameZh(String userNameZh) {
        this.userNameZh = userNameZh;
    }
}

