package com.foodchain.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class ClientToken  implements HostAuthenticationToken, RememberMeAuthenticationToken {

    private String username;

    private char[] password;

    private String host;

    private boolean rememberMe = false;

    public ClientToken() {}

    public ClientToken(final String username, final char[] password) {
        this(username, password, false, null);
    }


    public ClientToken(final String username, final String password) {
        this(username, password != null ? password.toCharArray() : null, false, null);
    }


    public ClientToken(final String username, final char[] password, final String host) {
        this(username, password, false, host);
    }


    public ClientToken(final String username, final String password, final String host) {
        this(username, password != null ? password.toCharArray() : null, false, host);
    }


    public ClientToken(final String username, final char[] password, final boolean rememberMe) {
        this(username, password, rememberMe, null);
    }


    public ClientToken(final String username, final String password, final boolean rememberMe) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, null);
    }

    public ClientToken(final String username, final char[] password,
                        final boolean rememberMe, final String host) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public ClientToken(final String username, final String password,
                        final boolean rememberMe, final String host) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Object getPrincipal() {
        return getUsername();
    }

    public Object getCredentials() {
        return getPassword();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.username = null;
        this.host = null;
        this.rememberMe = false;

        if (this.password != null) {
            for (int i = 0; i < password.length; i++) {
                this.password[i] = 0x00;
            }
            this.password = null;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(username);
        sb.append(", rememberMe=").append(rememberMe);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }

}
