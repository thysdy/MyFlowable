package com.frgk.flowable.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frgk.flowable.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoDao {
    /**
     * 获取待办任务
     *
     * @param processQueryVo
     * @return
     */
    IPage<TaskVo> getApplyingTasks(Page<TaskVo> page, @Param("query") ProcessInstanceQueryVo processQueryVo);

    /**
     * 获取已办任务
     *
     * @param processQueryVo
     * @return
     */
    IPage<TaskVo> getMyApplyedTasks(Page<TaskVo> page,@Param("query") ProcessInstanceQueryVo processQueryVo);

    /**
     * 获取流程记录信息
     *
     * @param id
     * @return
     */
    List<Comment> getCommentsByInstanceId(String id);

    /**
     * 获取可退回节点
     *
     * @param id
     * @return
     */
    public List<FlowNodeVo> getBackNode(String id);

    List<ProgressActVo> getInstanceProgressInfo(String instanceId);
}
