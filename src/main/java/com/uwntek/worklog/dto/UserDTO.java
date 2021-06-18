package com.uwntek.worklog.dto;

import com.uwntek.worklog.service.user.DeptService;
import com.uwntek.worklog.service.user.PositionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class UserDTO {

    Long id;
    String userName;
    String userNameZh;
    int dept;
    int position;
    String deptName;
    String positionName;
    int isEffective;



}
