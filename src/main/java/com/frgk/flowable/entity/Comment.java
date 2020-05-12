package com.frgk.flowable.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private String TYPE_;
    private Date TIME_;
    private String USER_ID_;
    private User user;
    private String TASK_ID_;
    private String PROC_INST_ID_;
    private String FULL_MSG_;

}
