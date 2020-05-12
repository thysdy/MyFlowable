package com.frgk.flowable.controller;

import com.frgk.flowable.common.MyException;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 新增流程实例
     *
     * @param
     * @return
     */
    @PostMapping("/startProcessInstance")
    public ReturnVo startProcessInstance(@RequestBody List<StartProcessInstance> startProcessInstance) {
        try {
            for (StartProcessInstance instance : startProcessInstance) {
                applicationService.startProcessInstance(instance);
            }
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 获取流程记录信息
     *
     * @param requestVo
     * @return
     */
    @PostMapping("/getCommentsByInstanceId")
    public ReturnVoT getCommentsByInstanceId(@RequestBody RequestVo requestVo) {
        return applicationService.getCommentsByInstanceId(requestVo.getId());
    }

    /**
     * 获取流程图
     *
     * @param requestVo
     * @param response
     * @return
     */
    @PostMapping("/getInstanceProgress")
    public ReturnVoT getImage(@RequestBody RequestVo requestVo, HttpServletResponse response) {

        return applicationService.createImage(requestVo.getInstanceId(), response);

    }

    /**
     * 导出到Excel
     *
     * @param requestVo
     * @param response
     * @return
     */
    @PostMapping("/exportExcel")
    @ResponseBody
    public ReturnVoT exportExcel(@RequestBody RequestVo requestVo, HttpServletResponse response) {
        return applicationService.exportExcel(requestVo.getIds(), response);
    }
}