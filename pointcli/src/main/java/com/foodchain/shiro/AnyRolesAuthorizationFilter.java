package com.foodchain.shiro;

import com.foodchain.util.Misc;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.LinkedList;
import java.util.Set;

public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (Misc.isEmpty(rolesArray) || rolesArray.length == 0) return true;

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        boolean[] hasRoles = subject.hasRoles(new LinkedList<>(roles));

        for(boolean has: hasRoles) {
            if (has) return true;
        }
        
        return false;
    }

}
