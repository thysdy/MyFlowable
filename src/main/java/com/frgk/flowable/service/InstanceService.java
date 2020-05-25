package com.frgk.flowable.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.ProcessInstanceDao;
import com.frgk.flowable.dao.TaskInfoDao;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.flowable.BaseProcessService;
import com.frgk.flowable.flowable.DeleteFlowableProcessInstanceCmd;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class InstanceService extends BaseProcessService {
    @Autowired
    ProcessInstanceDao processInstanceDao;
    @Autowired
    TaskInfoDao taskInfoDao;
    /**
     * 激活流程实例
     */
    private final int jh = 1;
    /**
     * 挂起流程实例
     */
    private final int gq = 2;
    private final int dataSize = 2;

    public int suspendOrActivateProcessInstanceById(List<String> processInstanceIds, int suspensionState) throws MyException {
        try {
            if (suspensionState == gq) {
                for (String processInstanceId : processInstanceIds) {
                    runtimeService.suspendProcessInstanceById(processInstanceId);
                }
            } else if (suspensionState == jh) {
                for (String processInstanceId : processInstanceIds) {
                    runtimeService.activateProcessInstanceById(processInstanceId);
                }

            }
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        return suspensionState;
    }

    public void deleteInstanceCompletely(List<String> processInstanceIds) throws MyException {
        int i = 0;
        try {
            for (String processInstanceId : processInstanceIds) {
                long count = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count();
                if (count > 0) {
                    DeleteFlowableProcessInstanceCmd cmd = new DeleteFlowableProcessInstanceCmd(processInstanceId, "删除流程实例", true);
                    managementService.executeCommand(cmd);
                } else {
                    historyService.deleteHistoricProcessInstance(processInstanceId);
                }
            }
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
    }

    public PageEntity getFlowableInstances(ProcessInstanceQueryVo queryVo) throws MyException {
        PageEntity pageEntity = null;
        List<ProcessInstanceVo> instances = new ArrayList<>();
        try {
            List<String> dates = null;
            dates = queryVo.getDate();
            if (null != dates && dates.size() == dataSize) {
                queryVo.setBeginTime(dates.get(0));
                queryVo.setEndTime(dates.get(1));
            }
            Page<ProcessInstanceVo> page = new Page<>(queryVo.getNowPage(), queryVo.getPageSize());
            IPage<ProcessInstanceVo> instancePage = processInstanceDao.getFlowableInstances(page, queryVo);
            instances = instancePage.getRecords();

            for (ProcessInstanceVo instanceVo : instances) {
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceVo.getProcessInstanceId()).list();
                Map<String, Object> variables = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    HistoricVariableInstance historicVariableInstance = list.get(i);
                    String name = historicVariableInstance.getVariableName();
                    Object object = historicVariableInstance.getValue();
                    variables.put(name, object);
                }
//                if (null != instanceVo.getEndTime()) {
//                    variables.put("state", "结束");
//                }
                instanceVo.setVariables(variables);
            }
            pageEntity = new PageEntity(instancePage.getTotal(), instancePage.getPages(), instancePage.getSize(), instancePage.getCurrent(), instances);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return pageEntity;
    }

}
