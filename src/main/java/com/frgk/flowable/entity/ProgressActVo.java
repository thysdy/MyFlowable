package com.frgk.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ProgressActVo implements Serializable {
    /**
     * 动作名称
     */
    private String actName;
    private String actType;
    /**
     * 处理人
     */
    private String assign;
    /**
     * 动作名称
     */
    private String taskId;
    /**
     * 流程实例ID
     */
    private String instanceId;
    /**
     * 表单名称
     */
//    private String formName;
    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 任务结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private List<CommentVo> comments;

    private Boolean isNowTask=false;


}
