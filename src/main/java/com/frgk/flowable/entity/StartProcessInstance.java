package com.frgk.flowable.entity;

import lombok.Data;

import java.util.Map;

@Data
public class StartProcessInstance {
    /**
     * 流程定义key 必填
     */
    private String processDefinitionKey;
    /**
     * 业务系统id 必填
     */
    private String businessKey;
    /**
     * 启动流程变量 选填
     */
    private Map<String, Object> variables;
    /**
     * 申请人工ID 必填
     */
    private String currentUserCode;
    /**
     * 系统标识 必填
     */
    private String systemSn;
    /**
     * 表单显示名称 必填
     */
    private String formName;
    /**
     * 流程提交人ID 必填
     */
    private String creator;

    private String message;
}
