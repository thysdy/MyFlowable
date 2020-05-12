package com.frgk.flowable.flowable;

import com.frgk.flowable.entity.CommentVo;
import org.flowable.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : bruce.liu
 * @title: : BaseProcessService
 * @projectName : flowable
 * @description: 基本的流程service
 * @date : 2019/12/220:50
 */
public abstract class BaseProcessService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    protected IFlowableCommentService flowableCommentService;

    /**
     * 添加审批意见
     *
     * @param taskId            任务id
     * @param userCode          处理人工号
     * @param processInstanceId 流程实例id
     * @param type              审批类型
     * @param message           审批意见
     */
    protected void addComment(String taskId, String userCode, String processInstanceId, String type, String message) {
        //1.添加备注
        CommentVo commentVo = new CommentVo(taskId, userCode, processInstanceId, type, message);
        flowableCommentService.addComment(commentVo);
        //TODO 2.修改扩展的流程实例表的状态以备查询待办的时候能带出来状态
        //TODO 3.发送mongodb的数据到消息队列里面
    }

    /**
     * 添加审批意见
     *
     * @param userCode          处理人工号
     * @param processInstanceId 流程实例id
     * @param type              审批类型
     * @param message           审批意见
     */
    protected void addComment(String userCode, String processInstanceId, String type, String message) {
        this.addComment(null, userCode, processInstanceId, type, message);
    }


}
