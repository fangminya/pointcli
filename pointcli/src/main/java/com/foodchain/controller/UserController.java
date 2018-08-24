package com.foodchain.controller;

import com.foodchain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService baseUserService;

    @ResponseBody
    @RequestMapping("/list")
    public String list() {
        return baseUserService.findByUserName("501882671@qq.com").toString();
    }

}
