package com.foodchain.controller;

import com.foodchain.consts.Consts;
import com.foodchain.entity.User;
import com.foodchain.service.UserService;
import com.foodchain.shiro.ClientToken;
import com.foodchain.util.Misc;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class HomeController extends BaseController {

    @Resource
    private UserService baseUserService;

    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "manager/403";
    }

    @RequestMapping("/doLogin")
    public String doLogin() { return clientPage("home"); }

    @RequestMapping({"/login", "/"})
    public String login(Model model, User user) {
        String userName = user.getUserName();
        String passWord = user.getPassWord();

        User loginUser = (User) getObjectFromShiroSession(Consts.Session.LOGIN_USER);

        if (Misc.isNotEmpty(loginUser)) return redirect("doLogin");

        if (Misc.isEmpty(userName) || Misc.isEmpty(passWord)) {
            model.addAttribute("user", new User());
            return clientPage("login");
        }

        String msg = null;
        Subject currentSubject = SecurityUtils.getSubject();

        try {
            currentSubject.login(new ClientToken(userName, passWord));
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定";
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用";
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期";
        } catch (UnknownAccountException e) {
            msg = "帐号不存在";
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！";
        } catch (AuthenticationException e) {
            msg = "账号信息为空";
        } catch (Exception e) {
            msg = "系统异常";
        }

        if (currentSubject.isAuthenticated()) return redirect("doLogin");

        model.addAttribute("msg", msg);
        model.addAttribute("user", new User());
        return clientPage("login");
    }

    /*无用：需要将此部分代码用注册功能代替*/
    @RequestMapping("/add")
    @ResponseBody
    public String add() {
        String salt = Misc.randomCode(16);

        User user = new User();
        user.setUserName("501882671@qq.com");
        user.setPassWord(Misc.getShiroMd5Pwd("123456", salt, 2));
        user.setSalt(salt);
        user.setNickName("Daisy");
        user.setCreateTime(new Date());
        baseUserService.save(user);

        return "add Success~";
    }
}
