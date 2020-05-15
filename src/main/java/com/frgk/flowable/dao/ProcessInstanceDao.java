package com.frgk.flowable.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frgk.flowable.entity.ProcessInstanceQueryVo;
import com.frgk.flowable.entity.ProcessInstanceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessInstanceDao {
    /**
     * 查询流程实例
     *
     * @param processInstanceQueryVo 流程实例查询条件
     * @return
     */
    IPage<ProcessInstanceVo> getFlowableInstances(Page<ProcessInstanceVo> page,@Param("query") ProcessInstanceQueryVo processInstanceQueryVo);

    List<String> getAllInstanceIds();
}
