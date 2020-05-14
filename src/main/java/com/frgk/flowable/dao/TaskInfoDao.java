package com.frgk.flowable.dao;

import com.frgk.flowable.entity.*;

import java.util.List;

public interface TaskInfoDao {
    /**
     * 获取待办任务
     * @param processQueryVo
     * @return
     */
    public List<TaskVo> getApplyingTasks(ProcessInstanceQueryVo processQueryVo);

    /**
     * 获取已办任务
     * @param processQueryVo
     * @return
     */
    List<TaskVo> getMyApplyedTasks(ProcessInstanceQueryVo processQueryVo);

    /**
     * 获取流程记录信息
     * @param id
     * @return
     */
    public List<Comment> getCommentsByInstanceId(String id);

    /**
     * 获取可退回节点
     * @param id
     * @return
     */
    public List<FlowNodeVo> getBackNode(String id);

    List<ProgressActVo> getInstanceProgressInfo(String instanceId);
}
