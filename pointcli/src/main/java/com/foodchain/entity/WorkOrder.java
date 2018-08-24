package com.foodchain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: yuanZ
 * @Date: 2018/8/21 10:10
 * @Description: 工单类
**/
@Entity
@Getter
@Setter
@Table(name = "work_order")
public class WorkOrder implements Serializable {

    @Id
    @Column(name = "id", length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;


    @Column(name = "user_id", length = 32)
    private String userId;

    @Column(name = "nick_name", length = 32)
    private String nickName;

    /*工单编码：# + 6位随机数*/
    @Column(name = "order_code", length = 32)
    private String orderCode;

    /*工单类型：账户类,交易类,投诉类，建议类*/
    @Column(name = "order_type", length = 12)
    private String orderType;

    /*工单状态: 未读,已读,关闭,已发送*/
    @Column(name = "order_state")
    private String orderState;

    @Column(name = "from_order_state")
    private String fromOrderState;

    @Column(name = "to_order_state")
    private String toOrderState;

    @Column(name = "theme", length = 120)
    private String theme;

    /*回复地址:即用户的账户地址*/
    @Column(name = "reply_address",  length = 32)
    private String replyAddress;

    @Column(name = "reply_content", length = 1200)
    private String replyContent;

    @Column(name = "file", length = 120)
    private String file;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    @Transient
    private String orderTypeName;

    @Transient
    private String orderStateName;

    @Transient
    private String fromOrderStateName;

    @Transient
    private String toOrderStateName;
}
