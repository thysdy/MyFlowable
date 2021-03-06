package com.frgk.flowable.controller;

import com.frgk.flowable.common.MyException;
import com.frgk.flowable.entity.*;
import com.frgk.flowable.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    /**
     * 获取我的待办任务
     *
     * @param processQueryVo
     * @return
     */
    @PostMapping("/getMyTasks")
    public ReturnVo getMyTasks(@RequestBody ProcessInstanceQueryVo processQueryVo) {
        try {
           PageEntity tasks = taskService.getMyTasks(processQueryVo);
            return ReturnVo.success(tasks);
        } catch (MyException e) {
            return ReturnVo.exception(e);

        }
    }

    /**
     * 获取我的已办任务
     *
     * @param processQueryVo
     * @return
     */
    @PostMapping("/getMyApplyedTasks")
    public ReturnVo getMyApplyedTasks(@RequestBody ProcessInstanceQueryVo processQueryVo) {
        try {
            PageEntity tasks = taskService.getMyApplyedTasks(processQueryVo);
            return ReturnVo.success(tasks);
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 执行任务
     *
     * @param requestVo
     * @return
     */
    @PostMapping("/doTask")
    public ReturnVo doTask(@RequestBody RequestVo requestVo) {
        try {
            taskService.doTask(requestVo);
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 获取可退回节点
     *
     * @param requestVo
     * @return
     */
    @PostMapping("/getBackNode")
    public ReturnVo getBackNode(@RequestBody RequestVo requestVo) {
        try {
            taskService.getBackNode(requestVo.getId());
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }

    /**
     * 退回
     *
     * @param request
     * @return
     */
    @PostMapping("/backRoll")
    public ReturnVo backRoll(@RequestBody RequestVo request) {
        try {
            taskService.rollback(request);
            return ReturnVo.success();
        } catch (MyException e) {
            return ReturnVo.exception(e);
        }
    }
}
