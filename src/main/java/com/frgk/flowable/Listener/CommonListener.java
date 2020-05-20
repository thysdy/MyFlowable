package com.frgk.flowable.Listener;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.HttpUtil4;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.UserDao;
import com.frgk.flowable.entity.RequestVo;
import com.frgk.flowable.entity.ServiceRequestVo;
import com.frgk.flowable.entity.User;
import com.frgk.flowable.flowable.BaseProcessService;
import lombok.SneakyThrows;
import org.aspectj.apache.bcel.classfile.Code;
import org.flowable.common.engine.impl.el.FixedValue;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component(value = "commonListener")
public class CommonListener extends BaseProcessService implements TaskListener, ExecutionListener {
    private FixedValue url;
    private FixedValue paramString;
    private Map<String, Object> param;
    @Autowired
    UserDao userDao;

    @SneakyThrows
    @Override
    public void notify(DelegateTask delegateTask) {
        String serviceUrl = null;
        String params = null;
        Map<String, Object> param = new HashMap<>();
        try {
            serviceUrl = (String) url.getValue(delegateTask);
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        try {
            params = (String) paramString.getValue(delegateTask);
            if (null != params) {
                param = (Map<String, Object>) JSONUtils.parse(params);
            }
        } catch (Exception e) {
        }
        try {
            //获取流程变量
            Map<String, Object> variables = delegateTask.getVariables();
            String initiator = (String) variables.get("initiator");
            String userName = null;
            if (null != initiator && "" != initiator) {
                User user = userDao.findUserById(initiator);
                userName = user.getFirstName();
            }
            String processInstanceId = delegateTask.getProcessInstanceId();
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            String businessKey = historicProcessInstance.getBusinessKey();
            param.put("userId", initiator);
            param.put("userName", userName);
            param.put("processInstanceId", processInstanceId);
            param.put("user", userName);
            param.put("businessKey", businessKey);
            param.put("variables", variables);

            ServiceRequestVo serviceRequestVo = new ServiceRequestVo(initiator, userName, processInstanceId, userName, businessKey, variables);

            if (null != serviceUrl) {
                RestTemplate restTemplate = new RestTemplate();
                new RequestVo();
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(serviceUrl, serviceRequestVo, String.class);
            }
        } catch (Exception e) {
        }
    }

    @SneakyThrows
    @Override
    public void notify(DelegateExecution delegateExecution) {
        String serviceUrl = null;
        String params = null;
        Map<String, Object> param = new HashMap<>();
        try {
            serviceUrl = (String) url.getValue(delegateExecution);
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        try {
            params = (String) paramString.getValue(delegateExecution);
            if (null != params) {
                param = (Map<String, Object>) JSONUtils.parse(params);
            }
        } catch (Exception e) {
        }
        try {
            //获取流程变量
            Map<String, Object> variables = delegateExecution.getVariables();
            String initiator = (String) variables.get("initiator");
            String userName = null;
            if (null != initiator && "" != initiator) {
                User user = userDao.findUserById(initiator);
                userName = user.getFirstName();
            }
            String processInstanceId = delegateExecution.getProcessInstanceId();
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            String businessKey = historicProcessInstance.getBusinessKey();
            param.put("userId", initiator);
            param.put("userName", userName);
            param.put("processInstanceId", processInstanceId);
            param.put("user", userName);
            param.put("businessKey", businessKey);
            param.put("variables", variables);

            ServiceRequestVo serviceRequestVo = new ServiceRequestVo(initiator, userName, processInstanceId, userName, businessKey, variables);

            if (null != serviceUrl) {
                RestTemplate restTemplate = new RestTemplate();
                new RequestVo();
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8214/adverisement/sendMessage", serviceRequestVo, String.class);
            }
        } catch (Exception e) {
        }
    }
}
