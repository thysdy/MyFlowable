package com.frgk.flowable.controller;

import com.frgk.flowable.common.MyException;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.service.InstanceService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstanceController {
    @Autowired
    InstanceService repairInstanceService;

    /**
     * 挂起或激活流程实例
     *
     * @param request
     * @return
     */
    @PostMapping("/saProcessInstanceById")
    public ReturnVoT saProcessInstanceById(@RequestBody RequestVo request) {
        return repairInstanceService.suspendOrActivateProcessInstanceById(request.getIds(), request.getSuspensionState());

    }

    /**
     * 彻底删除流程实例
     *
     * @param requestVo
     * @return
     */
    @DeleteMapping("/deleteInstanceCompletely")
    public ReturnVo deleteInstanceCompletely(@RequestBody RequestVo requestVo) {
        try {
            repairInstanceService.deleteInstanceCompletely(requestVo.getIds());
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 查询流程实例
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/getFlowableInstances")
    public ReturnVo getFlowableInstances(@RequestBody ProcessInstanceQueryVo queryVo) {
        try {
          List<ProcessInstanceVo> list= repairInstanceService.getFlowableInstances(queryVo);
            return ReturnVo.success(list);
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

}
