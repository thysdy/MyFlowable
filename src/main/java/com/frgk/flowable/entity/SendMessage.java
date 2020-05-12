package com.frgk.flowable.entity;

import java.util.List;

/**
 * 发送消息体字段
 */
public class SendMessage {

    /**
     * 消息类型
     */
    private String messageTypeId;

    /**
     * 附件地址
     */
    private String filePath;

    /**
     * 主题
     */
    private String messageTitle;

    /**
     * 消息体
     */
    private String messageBody;

    /**
     * 发件人
     */
    private String sendUser;

    /**
     * 发送主题列表
     */
    private List<SendPoint> sendPointList;

    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public List<SendPoint> getSendPointList() {
        return sendPointList;
    }

    public void setSendPointList(List<SendPoint> sendPointList) {
        this.sendPointList = sendPointList;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

}
