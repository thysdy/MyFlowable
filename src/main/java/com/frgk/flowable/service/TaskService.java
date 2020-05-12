package com.frgk.flowable.service;

import com.frgk.flowable.common.CodeEnum;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.TaskInfoDao;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.flowable.BaseProcessService;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class TaskService extends BaseProcessService {
    @Autowired
    TaskInfoDao repairInfoDao;
    private static final int dataSize = 2;

    public List<TaskVo> getMyTasks(ProcessInstanceQueryVo processQueryVo) throws MyException {
        List<TaskVo> tasks = new ArrayList<>();
        try {
            tasks = repairInfoDao.getApplyingTasks(processQueryVo);
            for (TaskVo task : tasks) {
                Map<String, Object> processVariables = runtimeService.getVariables(task.getProcessInstanceId());
                task.setVariables(processVariables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return tasks;
    }

    /**
     * 获取我的已办任务
     *
     * @param processQueryVo
     * @return
     */
    public List<TaskVo> getMyApplyedTasks(ProcessInstanceQueryVo processQueryVo) throws MyException {
        List<TaskVo> tasks = new ArrayList<>();
        try {
            tasks = repairInfoDao.getMyApplyedTasks(processQueryVo);
            for (TaskVo task : tasks) {
                String instanceId = task.getProcessInstanceId();
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceId).list();
                Map<String, Object> variables = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    HistoricVariableInstance historicVariableInstance = list.get(i);
                    String name = historicVariableInstance.getVariableName();
                    Object object = historicVariableInstance.getValue();
                    variables.put(name, object);
                }
                int state= (int) variables.get("state");
                if (null != task.getEndTime()&&2!=state) {
                variables.put("state",3);
                }
                task.setVariables(variables);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);

        }
        return tasks;
    }

    /**
     * 执行任务
     *
     * @param request
     * @return
     */
    public int doTask(RequestVo request) throws MyException {
        int i = 0;
        try {
            String message = request.getMessage();
            String userId = request.getUserCode();
            Map<String, Object> variables = request.getVariables();
            if(null!=variables.get("agree")){
                Boolean agree= (Boolean) variables.get("agree");
                if(!agree){
                    variables.put("state",2);
                }
            }
            for (String id : request.getIds()) {
                Task task = taskService.createTaskQuery().taskId(id).singleResult();
                String instanceId = task.getProcessInstanceId();
                //设置当前执行人
                taskService.setAssignee(task.getId(), userId);
                //设置下一任务执行人
                //执行任务
                taskService.complete(id, variables);
                //添加审批记录
                this.addComment(id, userId, instanceId,
                        null, message);
            }
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        return i;
    }

    /**
     * 退回任务
     *
     * @param request
     * @return
     */
    public ReturnVoT rollback(RequestVo request) {
        ReturnVoT returnVo = new ReturnVoT();
        String target = request.getTarget();
        String[] t = target.split(",");
        request.setTargetKey(t[0]);
        request.setTargetName(t[1]);
        try {
            Task task1 = taskService.createTaskQuery().taskId(request.getTaskId()).singleResult();
            String instanceId = task1.getProcessInstanceId();
            String message = "申请退回到" + request.getTargetName() + ",退回原因：" + request.getBackReason();
            List<String> key = new ArrayList<>();
            key.add(task1.getTaskDefinitionKey());

            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(task1.getProcessInstanceId())
                    .moveActivityIdsToSingleActivityId(key, request.getTargetKey())
                    .changeState();
            this.addComment(request.getTaskId(), request.getUserCode(), instanceId,
                    CommentTypeEnum.TH.toString(), message);
            //           修改报修状态
        } catch (Exception e) {
        }
        return returnVo;
    }

    public ReturnVoT getBackNode(String processInstanceId) {
        ReturnVoT returnVo = new ReturnVoT();
        try {
            List<FlowNodeVo> nodes = null;
            nodes = repairInfoDao.getBackNode(processInstanceId);
            //去重合并
            List<FlowNodeVo> datas = nodes.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(() ->
                            new TreeSet<>(Comparator.comparing(nodeVo -> nodeVo.getNodeId()))), ArrayList::new));
            //排序
            datas.sort(Comparator.comparing(FlowNodeVo::getEndTime));
            if (nodes.size() > 0) {
                returnVo.setBoo(true);
                returnVo.setInfo("获取退回节点成功！");
                returnVo.setObject(datas);
            } else {
                returnVo.setInfo("当前无可退回节点！");
            }
        } catch (Exception e) {
            returnVo.setInfo("获取退回节点失败！");
        }
        return returnVo;
    }

}
