package com.frgk.flowable.controller;

import com.frgk.flowable.entity.ReturnVoT;
import com.frgk.flowable.service.ProcessModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessModelController {
    @Autowired
    ProcessModelService processModelService;
    /**
     * 部署流程
     * @return
     */
    @PostMapping("/deployment")
    public ReturnVoT deployment() {
        return processModelService.deployment();
    }
}
