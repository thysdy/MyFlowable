package com.frgk.flowable.dao;

import com.frgk.flowable.entity.ProcessInstanceQueryVo;
import com.frgk.flowable.entity.ProcessInstanceVo;

import java.util.List;

public interface ProcessInstanceDao {
    /**
     * 查询流程实例
     *
     * @param processInstanceQueryVo 流程实例查询条件
     * @return
     */
    List<ProcessInstanceVo> getFlowableInstances(ProcessInstanceQueryVo processInstanceQueryVo);

    List<String> getAllInstanceIds();
}
