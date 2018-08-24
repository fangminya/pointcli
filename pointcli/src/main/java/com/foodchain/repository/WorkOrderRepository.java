package com.foodchain.repository;

import com.foodchain.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {

    List<WorkOrder> findByUserId(String UserId);

}
