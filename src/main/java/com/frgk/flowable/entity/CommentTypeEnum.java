package com.frgk.flowable.entity;


/**
 * @title: : CommentTypeEnum
 * @projectName : flowable
 * @description: 审批意见的类型
 */
public enum  CommentTypeEnum {
    SP("审批"),
    TH("退回"),
    WP("派发"),
    JD("接单"),
    PJ("评价"),
    JS("结束"),
    CH("撤回"),
    ZC("暂存"),
    QS("签收"),
    ZH("知会"),
    ZY("转阅"),
    YY("已阅"),
    ZB("转办"),
    QJQ("前加签"),
    HJQ("后加签"),
    XTZX("系统执行"),
    TJ("提交"),
    CXTJ("重新提交"),
    SPJS("审批结束"),
    LCZZ("流程终止"),
    SQ("授权"),
    CFTG("重复跳过"),
    XT("协同"),
    PS("评审");
    /**
     * 名称
     */
    private String name;

    /**
     * 通过type获取Msg
     *
     * @param type
     * @return
     * @Description:
     */
    public static String getEnumMsgByType(String type) {
        for (CommentTypeEnum e : CommentTypeEnum.values()) {
            if (e.toString().equals(type)) {
                return e.name;
            }
        }
        return "";
    }
    private CommentTypeEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
