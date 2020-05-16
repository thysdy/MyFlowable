package com.frgk.flowable.service;

import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.TaskInfoDao;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.flowable.BaseProcessService;
import com.frgk.flowable.flowable.FlowProcessDiagramGenerator;
import com.frgk.flowable.flowable.FlowableBpmnModelServiceImpl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class ApplicationService extends BaseProcessService {

    @Autowired
    private TaskInfoDao taskInfoDao;
    @Autowired
    private FlowableBpmnModelServiceImpl flowableBpmnModelService;
    @Autowired
    private FlowProcessDiagramGenerator flowProcessDiagramGenerator;

    public void createImage(String processInstanceId, HttpServletResponse response) throws MyException {
        try {
            //1.获取当前的流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String processDefinitionId = null;
            List<String> activeActivityIds = new ArrayList<>();
            List<String> highLightedFlows = new ArrayList<>();
            //2.获取所有的历史轨迹线对象
            List<HistoricActivityInstance> historicSquenceFlows = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).activityType(BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW).list();
            historicSquenceFlows.forEach(historicActivityInstance -> highLightedFlows.add(historicActivityInstance.getActivityId()));
            //3. 获取流程定义id和高亮的节点id
            if (processInstance != null) {
                //3.1. 正在运行的流程实例
                processDefinitionId = processInstance.getProcessDefinitionId();
                activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
            } else {
                //3.2. 已经结束的流程实例
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                processDefinitionId = historicProcessInstance.getProcessDefinitionId();
                //3.3. 获取结束节点列表
                List<HistoricActivityInstance> historicEnds = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId).activityType(BpmnXMLConstants.ELEMENT_EVENT_END).list();
                List<String> finalActiveActivityIds = activeActivityIds;
                historicEnds.forEach(historicActivityInstance -> finalActiveActivityIds.add(historicActivityInstance.getActivityId()));
            }
            InputStream inputStream = null;
            OutputStream out = null;
            try {
                //4. 获取bpmnModel对象
                BpmnModel bpmnModel = flowableBpmnModelService.getBpmnModelByProcessDefId(processDefinitionId);
                //5. 生成图片流
                inputStream = flowProcessDiagramGenerator.generateDiagram(bpmnModel, activeActivityIds, highLightedFlows);
                out = null;
                byte[] buf = new byte[1024];
                int legth = 0;

                out = response.getOutputStream();
                while ((legth = inputStream.read(buf)) != -1) {
                    out.write(buf, 0, legth);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new MyException(CodeEnum.ioException);
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(inputStream);
            }

        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
    }

    public List<Comment> getCommentsByInstanceId(String id) throws MyException {
        List<Comment> list = new ArrayList<>();
        try {
            list = taskInfoDao.getCommentsByInstanceId(id);
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        return list;
    }

    public String startProcessInstance(StartProcessInstance param) throws MyException {
        ProcessInstance processInstance = null;
        try {
            String id = param.getBusinessKey();
            //设置创建者id
            identityService.setAuthenticatedUserId(param.getCreator());
            Map<String, Object> variables = param.getVariables();
            variables.put("state", "开始");
            processInstance = runtimeService.createProcessInstanceBuilder()
                    .businessKey(id)
                    .variables(variables)
                    .processDefinitionKey(param.getProcessDefinitionKey())
                    .start();
            //添加审批记录
            this.addComment(param.getCurrentUserCode(), processInstance.getProcessInstanceId(),
                    CommentTypeEnum.TJ.toString(), param.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        String id = processInstance.getProcessInstanceId();
        return id;
    }

    public List<ProgressActVo> getInstanceProgressInfo(RequestVo requestVo) throws MyException {
        List<ProgressActVo> list = new ArrayList<>();
        try {
            list = taskInfoDao.getInstanceProgressInfo(requestVo.getInstanceId());
            list.get(list.size()-1).setIsNowTask(true);
            BpmnModel bpmnModel = repositoryService.getBpmnModel(requestVo.getProcessDefinitionId());
            Process process = bpmnModel.getProcesses().get(0);
            Collection<UserTask> flowElements1 = process.findFlowElementsOfType(UserTask.class);
            for (UserTask userTask : flowElements1) {
                int i = 1;
                for (ProgressActVo act : list) {
                    if (act.getActName().equals(userTask.getName())) {
                        i = 0;
                        break;
                    }
                }
                if (i == 1) {
                    ProgressActVo progressActVo = new ProgressActVo();
                    progressActVo.setActName(userTask.getName());
                    list.add(progressActVo);
                }
            }
            if (!"endEvent" .equals(list.get(list.size() - 1).getActType())) {
                ProgressActVo progressActVo = new ProgressActVo();
                progressActVo.setActName("结束");
                list.add(progressActVo);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return list;
    }
}