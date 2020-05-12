package com.frgk.flowable.Listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component(value = "examineEndListener")
public class examineEndListener implements TaskListener, JavaDelegate {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.getProcessInstanceId();
        System.out.println("申请已完成！");
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("申请已完成！");
        System.out.println(delegateExecution.getVariable("initiator"));
    }
}
