package com.foodchain.service;

import com.foodchain.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkOrderReplyService {

    @Resource
    private WorkOrderRepository repository;

}
