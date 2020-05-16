package com.frgk.flowable.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    TaskInfoDao taskInfoDao;

    public PageEntity getMyTasks(ProcessInstanceQueryVo processQueryVo) throws MyException {
        List<TaskVo> tasks = new ArrayList<>();
        PageEntity pageEntity = null;
        try {
            Page<TaskVo> page = new Page<>(processQueryVo.getNowPage(), processQueryVo.getPageSize());
            IPage<TaskVo> taskPage = taskInfoDao.getApplyingTasks(page, processQueryVo);
            tasks = taskPage.getRecords();
            for (TaskVo task : tasks) {
                Map<String, Object> processVariables = runtimeService.getVariables(task.getProcessInstanceId());
                task.setVariables(processVariables);
            }
            pageEntity=new PageEntity(taskPage.getTotal(),taskPage.getPages(),taskPage.getSize(),taskPage.getCurrent(),tasks);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
        return pageEntity;
    }

    /**
     * 获取我的已办任务
     *
     * @param processQueryVo
     * @return
     */
    public PageEntity getMyApplyedTasks(ProcessInstanceQueryVo processQueryVo) throws MyException {
        List<TaskVo> tasks = new ArrayList<>();
        PageEntity pageEntity = null;
        try {
            Page<TaskVo> page = new Page<>(processQueryVo.getNowPage(), processQueryVo.getPageSize());
            IPage<TaskVo> taskPage = taskInfoDao.getMyApplyedTasks(page, processQueryVo);
            tasks = taskPage.getRecords();
            for (TaskVo task : tasks) {
                String instanceId = task.getProcessInstanceId();
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(instanceId).list();
                Map<String, Object> variables = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    HistoricVariableInstance historicVariableInstance = list.get(i);
                    String name = historicVariableInstance.getVariableName();
                    Object object = historicVariableInstance.getValue();
                    variables.put(name, object);
                    if (null != task.getInstanceEndTime()) {
                        variables.put("state", "结束");
                    }
                }
                task.setVariables(variables);
            }
            pageEntity=new PageEntity(taskPage.getTotal(),taskPage.getPages(),taskPage.getSize(),taskPage.getCurrent(),tasks);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);

        }
        return pageEntity;
    }

    /**
     * 执行任务
     *
     * @param request
     * @return
     */
    public void doTask(RequestVo request) throws MyException {
        try {
            String message = request.getMessage();
            String userId = request.getUserCode();
            Map<String, Object> variables = request.getVariables();
            variables.put("state", "进行中");
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
            e.printStackTrace();
            throw new MyException(CodeEnum.commonException);
        }
    }

    /**
     * 退回任务
     *
     * @param request
     * @return
     */
    public void rollback(RequestVo request) throws MyException {
        String target = request.getTarget();
        String[] t = target.split(",");
        request.setTargetKey(t[0]);
        request.setTargetName(t[1]);
        try {
            Task myTask = taskService.createTaskQuery().taskId(request.getTaskId()).singleResult();
            String instanceId = myTask.getProcessInstanceId();
            String message = "申请退回到" + request.getTargetName() + ",退回原因：" + request.getBackReason();
            List<String> key = new ArrayList<>();
            key.add(myTask.getTaskDefinitionKey());
            Map<String, Object> processVariables = myTask.getProcessVariables();
            processVariables.put("state", "退回");
            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(myTask.getProcessInstanceId())
                    .processVariables(processVariables)
                    .moveActivityIdsToSingleActivityId(key, request.getTargetKey())
                    .changeState();
            this.addComment(request.getTaskId(), request.getUserCode(), instanceId,
                    CommentTypeEnum.TH.toString(), message);
            //           修改报修状态
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
    }

    public List<FlowNodeVo> getBackNode(String processInstanceId) throws MyException {
        List<FlowNodeVo> list = new ArrayList<>();
        try {
            list = taskInfoDao.getBackNode(processInstanceId);
            //去重合并
            List<FlowNodeVo> datas = list.stream().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(() ->
                            new TreeSet<>(Comparator.comparing(nodeVo -> nodeVo.getNodeId()))), ArrayList::new));
            //排序
            datas.sort(Comparator.comparing(FlowNodeVo::getEndTime));
        } catch (Exception e) {
            throw new MyException(CodeEnum.commonException);
        }
        return list;
    }

}
