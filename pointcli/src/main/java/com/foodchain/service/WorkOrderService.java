package com.foodchain.service;

import com.foodchain.consts.Code;
import com.foodchain.entity.User;
import com.foodchain.entity.WorkOrder;
import com.foodchain.repository.WorkOrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WorkOrderService {

    @Resource
    private WorkOrderRepository repository;

    public List<WorkOrder> findAll() {
        return repository.findAll();
    }

    public List<WorkOrder> findByUserId(String userId) {
        return addProperty(repository.findByUserId(userId));
    }

    public Boolean saveWorkOrder(WorkOrder workOrder, User user) {
        try {
            workOrder.setOrderCode("#"+Integer.toString(ThreadLocalRandom.current().nextInt(900000) + 100000)); //随机码
            workOrder.setUserId(user.getId());
            workOrder.setNickName(user.getNickName());
            workOrder.setOrderState(Code.WorkOrderState.UNREAD); //未读
            workOrder.setFromOrderState(Code.WorkOrderState.SEND); //发起方已发送
            workOrder.setToOrderState(Code.WorkOrderState.UNREAD); //接收方未读
            workOrder.setCreateTime(System.currentTimeMillis());
            workOrder.setUpdateTime(System.currentTimeMillis());
            workOrder.setReplyAddress(user.getUserName());
            repository.save(workOrder);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<WorkOrder> addProperty(List<WorkOrder> list) {
        for (WorkOrder order: list) { addProperty(order); }

        return list;
    }


    private void addProperty(WorkOrder order) {
        order.setOrderStateName(Code.WorkOrderState.get(order.getOrderState()));
        order.setOrderTypeName(Code.WorkOrderType.get(order.getOrderType()));
    }
}
