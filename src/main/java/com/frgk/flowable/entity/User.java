package com.frgk.flowable.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;


}
