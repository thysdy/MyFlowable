package com.frgk.flowable.service;

import com.frgk.flowable.entity.ReturnVoT;
import com.frgk.flowable.flowable.BaseProcessService;
import org.flowable.engine.repository.Deployment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProcessModelService extends BaseProcessService {

    public ReturnVoT deployment() {
        ReturnVoT returnVo = new ReturnVoT();
        try {
            Deployment deploy = repositoryService.createDeployment().addClasspathResource("processes/RepairAppV9.bpmn20.xml")
                    .name("RepairAppV9")
                    .key("RepairAppV9")
                    .category("10000")
                    .tenantId("repairflow")
                    .deploy();
            System.out.println(deploy.getId());
            returnVo.setBoo(true);
            returnVo.setInfo("部署成功！");
        } catch (Exception e) {
            returnVo.setInfo("部署失败！");
        }
        return returnVo;
    }
}
