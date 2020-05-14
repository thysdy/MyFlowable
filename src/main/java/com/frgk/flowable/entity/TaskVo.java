package com.frgk.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class TaskVo implements Serializable {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 表单名称
     */
//    private String formName;
    /**
     * 业务主键
     */
    private String businessKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 流程定义id
     */
    private String processDefinitionId;


    /**
     *流程开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date instanceStartTime;
    /**
     *流程结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date instanceEndTime;
    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 系统标识
     */
//    private String systemSn;
    Map<String, Object> variables;
}
