package com.frgk.flowable.entity;

/**
 * 发送设备结合
 */
public class SendPoint {
    /**
     * 分组id（xxxx/xxxx/xxxx）
     */
    private String groupId;

    /**
     * 接受设备/用户id
     */
    private String receiveId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
}