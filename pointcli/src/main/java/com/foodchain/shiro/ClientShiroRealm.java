package com.foodchain.shiro;

import com.foodchain.consts.Consts;
import com.foodchain.entity.User;
import com.foodchain.service.UserService;
import com.foodchain.util.Misc;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class ClientShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService baseUserService;

    @Override
    public void setName(String name) {
        super.setName("ClientShiroRealm");
    }

    @Override
    public Class getAuthenticationTokenClass() {
        return ClientToken.class;
    }

    /**
     * 用于加密参数：AuthenticationToken是从表单传过来封装好的对象
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户输入的账号
        String username = (String) token.getPrincipal();

        if (Misc.isEmpty(username)) throw new AuthenticationException();

        // 通过username查找User对象
        // 实际项目中,这里可以根据实际情况做缓存,Shiro默认2min内不会重复执行此方法
        User user = baseUserService.findByUserName(username);

        if (Misc.isEmpty(user)) throw new UnknownAccountException();

        // 认证的实体信息可以是username,也可以是用户的实体类对象
        // Object principal = username;
        Object credentials = user.getPassWord();
        ByteSource credentialSalt = ByteSource.Util.bytes(user.getSalt());
        String realName = this.getName();
        // 封装username,password等信息,用户密码的对比是Shiro帮我们完成的
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user, credentials, credentialSalt, realName);

        Session shiroSession = SecurityUtils.getSubject().getSession();
        shiroSession.setAttribute(Consts.Session.LOGIN_USER, user);

        return info;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

}
