package com.uwntek.worklog.entity.worklog;

import lombok.Data;

@Data

public class MonthlogExport {
    String monthlog1;
    String monthlog2;
    String monthlog3;
    String monthlog4;
    String monthlogconclusion;
    String monthlogplan;
    String user;
    String createMonth;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }
}
