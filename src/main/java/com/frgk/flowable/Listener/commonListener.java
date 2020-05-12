package com.frgk.flowable.Listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component(value = "commonListener")
public class commonListener implements TaskListener, JavaDelegate {
    @Override
    public void notify(DelegateTask delegateTask) {

    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String initiator= (String) delegateExecution.getVariable("initiator");
        String processInstanceId = delegateExecution.getProcessInstanceId();
        String eventName = delegateExecution.getEventName();
        String processInstanceBusinessKey = delegateExecution.getProcessInstanceBusinessKey();



    }
}
