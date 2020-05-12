package com.frgk.flowable.flowable;

import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FlowableBpmnModelServiceImpl extends BaseProcessService implements IFlowableBpmnModelService {
    @Override
    public BpmnModel getBpmnModelByProcessDefId(String processDefId) {
        return repositoryService.getBpmnModel(processDefId);
    }

    @Override
    public List<FlowNode> findFlowNodes(String processDefId) {
        return null;
    }

    @Override
    public List<EndEvent> findEndFlowElement(String processDefId) {
        return null;
    }

    @Override
    public boolean checkActivitySubprocessByActivityId(String processDefId, String activityId) {
        return false;
    }

    @Override
    public List<Activity> findActivityByActivityId(String processDefId, String activityId) {
        return null;
    }

    @Override
    public FlowNode findMainProcessActivityByActivityId(String processDefId, String activityId) {
        return null;
    }

    @Override
    public FlowNode findFlowNodeByActivityId(String processDefId, String activityId) {
        return null;
    }

    @Override
    public Activity findActivityByName(String processDefId, String name) {
        return null;
    }
}
