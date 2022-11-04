package com.tbcom.login.service.impl;


import java.io.Serializable;
import java.util.List;

public class UserVO implements Serializable{


    //아이디
    private String userId;

    //비번
    private String password;

    //권한
    private List<String> authority;

    //사용여부
    private boolean enabled;

    //이름
    private String name;

    // 리프레시 토큰
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<String> getAuthority() {
        return authority;
    }
    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", authority=" + authority +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}