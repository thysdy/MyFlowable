package com.frgk.flowable.entity;

import lombok.Data;
import java.util.Date;

@Data
public class RepairInfo {
private String id;
private String num;
private Object station;
private String process_instance_id;
private String contact;
private String tel;
private String repair_state;
private String repair_category;
private String fault;
/**
 * 报修属性
 */
private String repair_attribute;
/**
 * 0：非小工程 1：小工程
 */
private String minor_works;
/**
 * 0：范围内 1：范围外)
 */
private String overextend;
private float score;
private String evaluate;
private Date create_time;
private Date end_time;
private Date update_time;
/**
 * 备注
 */
private String remark;
private String photo;

}
