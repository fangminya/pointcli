package com.foodchain.controller;

import com.foodchain.entity.WorkOrder;
import com.foodchain.service.WorkOrderReplyService;
import com.foodchain.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yuanZ
 * @Date: 2018/8/21 13:31
 * @Description: 工单
**/
@Controller
@RequestMapping("/workOrder")
public class WorkOrderController extends BaseController {

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private WorkOrderReplyService workOrderReplyService;

    @RequestMapping("/load")
    public String load(Model model) {
        List<WorkOrder> orders = workOrderService.findByUserId(loginUser().getId());
        model.addAttribute("orders", orders);
        return clientPage("workOrder/list");
    }

    @RequestMapping("/addOrder")
    public String addOrder(Model model) {
        model.addAttribute("workOrder", new WorkOrder());
        return clientPage("workOrder/add");
    }

    @RequestMapping("/doAddOrder")
    public String doAddOrder(WorkOrder workOrder) {
        workOrderService.saveWorkOrder(workOrder, loginUser());
        return redirect("load");
    }
}
