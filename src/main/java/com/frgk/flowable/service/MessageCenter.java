package com.frgk.flowable.service;

import com.frgk.flowable.entity.ReturnVo;
import com.frgk.flowable.entity.SendMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "MESSAGECENTER")
public interface MessageCenter {
@PostMapping("/messageMain/sendMessage")
    ReturnVo sendMessage(@RequestBody SendMessage sendMessage);
}
