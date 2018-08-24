package com.foodchain.shiro;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: yuanZ
 * Date: 2018/7/20 14:15
 * Description: Shiro配置
**/
@Configuration
public class ShiroConfig {

    @Bean(name = "systemShiroFilter")
    public ShiroFilterFactoryBean systemShiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置,默认自动寻找web根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的页面
        //shiroFilterFactoryBean.setSuccessUrl("/home");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getShiroFiltersChainMap());

        return shiroFilterFactoryBean;
    }

    /**
     * 拦截器(过滤链)
     */
    private Map<String, String> getShiroFiltersChainMap() {
        Map<String, String> filtersChainMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        // anon:所有url都可以匿名访问,authc:所有url都必须认证通过才可以访问
        filtersChainMap.put("/static/*", "anon");
        // 配置退出过滤器,其中的退出代码Shiro已经完成
        filtersChainMap.put("/logout", "logout");
        // 过滤链定义,从上向下顺序执行,一般将/**放在最下边
        filtersChainMap.put("/**", "authc");

        return filtersChainMap;
    }

    /**
     * 负责org.apache.shir
     * o.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor () {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 凭证匹配器
     * 防止密码数据库中明码保存,对form中输入的密码进行编码
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher  credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5"); // 散列算法
        credentialsMatcher.setHashIterations(2); // 散列的次数--->散列2次,md5(md5(...))
        return credentialsMatcher;
    }


    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public ClientShiroRealm shiroRealmClient() {
        ClientShiroRealm shiroRealm = new ClientShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /*********************** Shiro Session Begin **********************/
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false); // Url中不显示js session
        sessionManager.setGlobalSessionTimeout(1800000); // ms/30Min
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
        sessionDao.setActiveSessionsCacheName("passwordRetryCacheClient");
        return sessionDao;
    }
    /*********************** Shiro Session End **********************/
    /*********************** Shiro EhCache Begin ********************/
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        CacheManager cacheManager = CacheManager.getCacheManager("es");
        if (cacheManager == null) {
            try {
                cacheManager = CacheManager.create(ResourceUtils
                        .getInputStreamForPath("classpath:ehcache.xml"));
            } catch (CacheException | IOException e) {
                e.printStackTrace();
            }
        }
        ehCacheManager.setCacheManager(cacheManager);

        return ehCacheManager;
    }
    /*********************** Shiro EhCache End **********************/
    /********************** Shiro rememberMe Begin *******************/
    @Bean
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称,对应前端的checkBox的name
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // cookie的有效期单位s--->一周
        simpleCookie.setMaxAge(60 * 60 * 24 * 7);
        return simpleCookie;
    }
    /********************** Shiro rememberMe End *******************/

    /**
     * 生成rememberMeManager管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookie加密秘钥,默认Aes算法,秘钥长度(128 256 512)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 权限控制：整合了login,logout,权限,session的处理
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealmClient()); // 注入自定义Realm
        securityManager.setCacheManager(ehCacheManager()); // 注入缓存管理器
        securityManager.setSessionManager(sessionManager());
        //securityManager.setRememberMeManager(rememberMeManager()); // 记住我管理器
        return securityManager;
    }

    /**
     * 开启Shiro aop注解支持 使用代理方式
     * 注解：@RequiresPermissions
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor (
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * DefaultAdvisorAutoProxyCreator: 自动代理
     */
    @Bean
    @ConditionalOnMissingBean // 该注释注解的bean存在,则不再创建该bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAPC = new DefaultAdvisorAutoProxyCreator();
        defaultAAPC.setProxyTargetClass(true);
        return defaultAAPC;
    }
}
