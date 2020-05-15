package com.frgk.flowable.service;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<ProcessInstanceVo> getFlowableInstances(ProcessInstanceQueryVo queryVo) throws MyException {
        List<ProcessInstanceVo> instances = new ArrayList<>();
        try {
            List<String> dates = null;
            dates = queryVo.getDate();
            if (null != dates && dates.size() == dataSize) {
                queryVo.setBeginTime(dates.get(0));
                queryVo.setEndTime(dates.get(1));
            }
            instances = processInstanceDao.getFlowableInstances(queryVo);

            for (ProcessInstanceVo instanceVo : instances) {
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceVo.getProcessInstanceId()).list();
                Map<String, Object> variables = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    HistoricVariableInstance historicVariableInstance = list.get(i);
                    String name = historicVariableInstance.getVariableName();
                    Object object = historicVariableInstance.getValue();
                    variables.put(name, object);
                }
                String state= (String) variables.get("state");
                if (null != instanceVo.getEndTime()) {
                    variables.put("state", "结束");
                }
                instanceVo.setVariables(variables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return instances;
    }

}
