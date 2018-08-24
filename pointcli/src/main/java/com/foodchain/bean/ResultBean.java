package com.foodchain.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: yuanZ
 * @Date: 2018/7/31 15:02
 * @Description: json结果集
**/
@Getter
@Setter
public class ResultBean implements Serializable {

    private Object data;

    private String message;

    private Boolean success = false;

}
