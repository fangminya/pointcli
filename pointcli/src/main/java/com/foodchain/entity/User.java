package com.foodchain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yuanZ
 * @Date: 2018/8/13 15:14
 * @Description: 用户账号信息表
**/
@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String salt;

    private String userName;

    private String passWord;

    private String nickName;

    private Date createTime;

}
