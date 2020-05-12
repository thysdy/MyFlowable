package com.frgk.flowable.service;

import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.TaskInfoDao;
import com.frgk.flowable.common.tools.DateTools;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.flowable.BaseProcessService;
import com.frgk.flowable.flowable.FlowProcessDiagramGenerator;
import com.frgk.flowable.flowable.FlowableBpmnModelServiceImpl;

import io.micrometer.core.instrument.util.StringUtils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
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

import static com.frgk.flowable.common.tools.DateTools.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class ApplicationService extends BaseProcessService {

    @Autowired
    private TaskInfoDao repairInfoDao;
    @Autowired
    private FlowableBpmnModelServiceImpl flowableBpmnModelService;
    @Autowired
    private FlowProcessDiagramGenerator flowProcessDiagramGenerator;

    public ReturnVoT createImage(String processInstanceId, HttpServletResponse response) {
        ReturnVoT returnVo = new ReturnVoT();
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
                returnVo.setBoo(true);
            } catch (IOException e) {
                e.printStackTrace();
                returnVo.setInfo("生成流程进度失败！");
            } finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(inputStream);
            }

        } catch (Exception e) {
            returnVo.setInfo("生成流程进度失败！");
        }

        return returnVo;
    }

    public ReturnVoT getCommentsByInstanceId(String id) {
        ReturnVoT returnVo = new ReturnVoT();
        try {
            List<Comment> list = repairInfoDao.getCommentsByInstanceId(id);
            returnVo.setObject(list);
            if (list.size() > 0) {
                returnVo.setBoo(true);
            } else {
                returnVo.setInfo("当前无流程记录！");
            }
        } catch (Exception e) {
            returnVo.setInfo("导出流程记录失败！");
        }
        return returnVo;
    }

    public ReturnVoT exportExcel(List<String> ids, HttpServletResponse response) {
        ReturnVoT returnVo = new ReturnVoT();
        String data = DateTools.format(new Date(), DEFAULT_YEAR_MONTH_DAY_3);
        String excelName = "报修申请详情" + data;
        String sheetName = "报修申请详情";
        String[] title = new String[]{"ID", "编号", "站点", "联系人", "联系电话",
                "报修状态", "报修类别", "故障内容", "报修属性", "小工程", "范围内", "评分", "评价", "创建时间", "更新时间", "备注"};
        String[] colum = null;
        colum = new String[]{"id", "num", "station", "contact", "tel", "repair_state", "repair_category", "fault", "repair_attribute",
                "minor_works", "overextend", "score", "evaluate", "create_time", "update_time", "remark"};
        try {
//            List<RepairInfo> list = (List<RepairInfo>) this.getRepairDetails(ids).getObject();
//            ExportExcelUtil.export(excelName, sheetName, title, colum, list, response);
            returnVo.setBoo(true);
        } catch (Exception e) {
            returnVo.setInfo("导出Excel失败！");
        }

        return returnVo;
    }

    public String startProcessInstance(StartProcessInstance param) throws MyException {
        ProcessInstance processInstance = null;
        try {
            String id = param.getBusinessKey();
            //设置创建者id
            identityService.setAuthenticatedUserId(param.getCreator());
            Map<String, Object> variables = param.getVariables();
            variables.put("state", 1);
            processInstance = runtimeService.createProcessInstanceBuilder()
                    .businessKey(id)
                    .variables(variables)
                    .processDefinitionKey(param.getProcessDefinitionKey())
                    .start();
            //添加审批记录
            this.addComment(param.getCurrentUserCode(), processInstance.getProcessInstanceId(),
                    CommentTypeEnum.TJ.toString(),param.getMessage() );
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return processInstance.getProcessInstanceId();
    }
}