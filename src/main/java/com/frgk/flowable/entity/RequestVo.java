package com.frgk.flowable.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class RequestVo {

    private String id;
    private String userCode;
    private List<String> ids;
    private String name;
    private Date time;
    private String assignee;
    private String instanceId;
    private String processDefinitionId;
    private String taskId;
    private String target;
    private String targetKey;
    private String targetName;
    private int suspensionState;
    private String backReason;
    private String beginTime;
    private String endTime;
    private int currentPage;
    private int currentIndex;
    private int pageSize;
    private String lastCreateTime;
    private String message;
    private Map<String, Object> variables;

}
