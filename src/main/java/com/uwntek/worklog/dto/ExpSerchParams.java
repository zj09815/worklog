package com.uwntek.worklog.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ExpSerchParams {
    String searchString;
    Set<String> verifyStatus;
    Set<Long> userId;
    Set<Long> classifyId;
    Set<Long> tagId;
}
