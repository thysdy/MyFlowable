//package com.frgk.flowable.Listener;
//
//import com.frgk.flowable.flowable.BaseProcessService;
//import org.flowable.bpmn.model.EndEvent;
//import org.flowable.bpmn.model.FlowElement;
//import org.flowable.bpmn.model.Process;
//import org.flowable.bpmn.model.SequenceFlow;
//import org.flowable.common.engine.api.delegate.event.FlowableEvent;
//import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
//import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
//import org.flowable.engine.impl.util.ProcessDefinitionUtil;
//import org.flowable.task.service.impl.persistence.entity.TaskEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * 全局监听器，判断流程是不是运行到了最后一个EndEvent
// * @author: YBY
// * @create: 2019-05-04 20:51
// **/
//@Component
//public class ProcessEndListener extends BaseProcessService implements FlowableEventListener {
//
//
//    @Override
//    public void onEvent(FlowableEvent event) {
//        // 当前节点任务实体,
//        TaskEntity taskEntity = (TaskEntity) ((FlowableEntityEventImpl) event).getEntity();
//        String taskId = taskEntity.getId();
//        String curActId = managementService.getNodeId(taskId);
//        String procDefId = ProcUtils.getProcessDefinitionByTaskId(taskEntity.getId()).getId();
//        Process process = ProcessDefinitionUtil.getProcess(procDefId);
//        //遍历整个process,找到endEventId是什么，与当前taskId作对比
//        List<FlowElement> flowElements = (List<FlowElement>) process.getFlowElements();
//        for (FlowElement flowElement : flowElements) {
//            if (flowElement instanceof SequenceFlow) {
//                SequenceFlow flow = (SequenceFlow) flowElement;
//                FlowElement sourceFlowElement = flow.getSourceFlowElement();
//                FlowElement targetFlowElement = flow.getTargetFlowElement();
//                //如果当前边的下一个节点是endEvent，那么获取当前边
//                if(targetFlowElement instanceof EndEvent && sourceFlowElement.getId().equals(curActId))
//                {
//                    System.out.println("下一个是结束节点！！");
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean isFailOnException() {
//        return false;
//    }
//
//    @Override
//    public boolean isFireOnTransactionLifecycleEvent() {
//        return false;
//    }
//
//    @Override
//    public String getOnTransaction() {
//        return null;
//    }
//}
