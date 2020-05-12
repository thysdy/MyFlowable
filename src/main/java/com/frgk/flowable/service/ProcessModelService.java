package com.frgk.flowable.service;

import com.frgk.flowable.flowable.BaseProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProcessModelService extends BaseProcessService {

}
