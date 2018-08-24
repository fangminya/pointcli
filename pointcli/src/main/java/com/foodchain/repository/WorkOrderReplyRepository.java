package com.foodchain.repository;

import com.foodchain.entity.WorkOrderReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderReplyRepository extends JpaRepository<WorkOrderReply, String> {
}
