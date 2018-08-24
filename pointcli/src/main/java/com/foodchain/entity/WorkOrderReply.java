package com.foodchain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: yuanZ
 * @Date: 2018/8/21 11:04
 * @Description: 工单回复类
**/
@Entity
@Getter
@Setter
public class WorkOrderReply {

    @Id
    @Column(name = "id", length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "order_id", length = 32)
    private String orderId;

    @Column(name = "order_code", length = 32)
    private String orderCode;

    /*消息方向(用户，系统)*/
    @Column(name = "direction", length = 12)
    private String direction;

    @Column(name = "reply_content", length = 1200)
    private String replyContent;

    @Column(name = "file", length = 120)
    private String file;

    @Column(name = "create_time")
    private Long createTime;

}
