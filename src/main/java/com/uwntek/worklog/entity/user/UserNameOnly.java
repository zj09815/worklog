package com.uwntek.worklog.entity.user;

import lombok.Data;

@Data
public class UserNameOnly {
    Long id;
    String userName;
    String userNameZh;
    int dept;
    int position;
}

