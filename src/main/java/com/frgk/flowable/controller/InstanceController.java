package com.frgk.flowable.controller;

import com.frgk.flowable.common.MyException;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ReturnVo saProcessInstanceById(@RequestBody RequestVo request) {
        try {
            repairInstanceService.suspendOrActivateProcessInstanceById(request.getIds(), request.getSuspensionState());
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
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
            PageEntity pageEntity = repairInstanceService.getFlowableInstances(queryVo);
            return ReturnVo.success(pageEntity);
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

}
