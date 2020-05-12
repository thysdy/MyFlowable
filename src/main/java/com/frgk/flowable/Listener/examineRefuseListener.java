package com.frgk.flowable.Listener;

import com.frgk.flowable.entity.SendMessage;
import com.frgk.flowable.entity.SendPoint;
import com.frgk.flowable.service.MessageCenter;
import liquibase.pro.packaged.A;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "examineRefuseListener")
public class examineRefuseListener implements TaskListener, JavaDelegate {
    @Autowired
    MessageCenter messageCenter;

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.getProcessInstanceId();
        System.out.println("申请已拒绝！");
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("申请已拒绝！");
        String initiator= (String) delegateExecution.getVariable("initiator");
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setMessageTypeId("0");
//        sendMessage.setMessageTitle("广告投放申请");
//        sendMessage.setMessageBody("广告投放申请：" + delegateExecution.getProcessInstanceId() + "被拒绝！");
//        sendMessage.setSendUser("admin");
//        List<SendPoint> sendPoints = new ArrayList<>();
//        SendPoint sendPoint = new SendPoint();
//        //设置接收组和人
////        sendPoint.setGroupId();
//        sendPoint.setReceiveId(initiator);
//
//        sendPoints.add(sendPoint);
//        sendMessage.setSendPointList(sendPoints);
//        messageCenter.sendMessage(sendMessage);
    }
}
