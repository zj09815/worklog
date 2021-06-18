package com.uwntek.worklog.dto;

import lombok.Data;

@Data
public class UserInfo {
    Long id;
    String userName;
    String userNameZh;
    String image;
    String contact;
    String email;
    String remark;
    int dept;
    int position;
    int isEffective;
}
