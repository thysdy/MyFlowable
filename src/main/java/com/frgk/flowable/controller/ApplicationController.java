package com.frgk.flowable.controller;

import com.frgk.flowable.common.MyException;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
            List<String> ids = new ArrayList<>();
            for (StartProcessInstance instance : startProcessInstance) {
                String id = applicationService.startProcessInstance(instance);
                ids.add(id);
            }
            return ReturnVo.success(ids);
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
    public ReturnVo getCommentsByInstanceId(@RequestBody RequestVo requestVo) {
        try {
            List<Comment> comments = applicationService.getCommentsByInstanceId(requestVo.getId());
            return ReturnVo.success(comments);
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 获取流程图
     *
     * @param requestVo
     * @param response
     * @return
     */
    @PostMapping("/getInstanceProgress")
    public ReturnVo getImage(@RequestBody RequestVo requestVo, HttpServletResponse response) {

        try {
            applicationService.createImage(requestVo.getInstanceId(), response);
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

}