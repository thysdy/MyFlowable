package com.frgk.flowable.entity;

import lombok.Data;

import java.util.Map;

@Data
public class ServiceRequestVo {
    private String userId;
    private String userName;
    private String processInstanceId;
    private String user;
    private String businessKey;
    private Map<String,Object> variables;

    public ServiceRequestVo(String userId, String userName, String processInstanceId, String user, String businessKey, Map<String, Object> variables) {
        this.userId = userId;
        this.userName = userName;
        this.processInstanceId = processInstanceId;
        this.user = user;
        this.businessKey = businessKey;
        this.variables = variables;
    }

    public ServiceRequestVo() {
    }
}
