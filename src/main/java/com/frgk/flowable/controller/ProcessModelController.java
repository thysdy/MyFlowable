package com.frgk.flowable.controller;

import com.frgk.flowable.service.ProcessModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessModelController {
    @Autowired
    ProcessModelService processModelService;
}
