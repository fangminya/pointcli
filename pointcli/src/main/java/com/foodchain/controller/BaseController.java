package com.foodchain.controller;

import com.foodchain.consts.Consts;
import com.foodchain.entity.User;
import com.foodchain.util.Misc;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BaseController {

    protected String redirect(String action) {
        return "redirect:" + action;
    }

    protected String clientPage(String page) {
        return getString("client/", page);
    }

    protected String getString(String ... strs) {
        StringBuffer buf = new StringBuffer();
        if (null != strs && strs.length > 0) {
            for (int i = 0; i < strs.length; ++i) {
                buf.append(strs[i]);
            }
        }
        return buf.toString();
    }

    protected String toJson(boolean success, String message, Object data) {
        return Misc.toJson(success, message, data);
    }

    protected User loginUser() {
        return (User) getShiroSession().getAttribute(Consts.Session.LOGIN_USER);
    }

    protected Session getShiroSession() {
        return  SecurityUtils.getSubject().getSession();
    }

    protected void addToShiroSession(String key, Object value) {
        getShiroSession().setAttribute(key, value);
    }

    protected String getStringFromShiroSession(String key) {
        Object obj = getObjectFromShiroSession(key);
        return (null == obj) ? null : obj.toString();
    }

    protected Object getObjectFromShiroSession(String key) {
        return getShiroSession().getAttribute(key);
    }

    protected void addToRequest(HttpServletRequest request, String key, Object value) {
        request.setAttribute(key, value);
    }

    /**
     * 返回流数据
     * @param response
     * @param object
     */
    protected void toWeb(HttpServletResponse response, Object object) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter objPw = response.getWriter();

            objPw.print(object);
            objPw.flush();
            objPw.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
