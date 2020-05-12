package com.frgk.flowable.dao;

import com.frgk.flowable.entity.CommentVo;

import java.util.List;

public interface IFlowableCommentDao {

    /**
     * 通过流程实例id获取审批意见列表
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId);

}
