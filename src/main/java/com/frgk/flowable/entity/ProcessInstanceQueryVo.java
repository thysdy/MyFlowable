package com.frgk.flowable.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcessInstanceQueryVo implements Serializable {

    private String userCode;
    private int suspensionState;
    private int province;
    private int city;
    private int district;
    private int stationType;
    private String projectNum;
    private String stationName;
    private String stationId;
    private List<String> date;
    private String repairState;
    private String beginTime;
    private String endTime;
    private int pageSize;
    private String lastCreateTime;
}
