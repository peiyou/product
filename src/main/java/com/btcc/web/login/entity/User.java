package com.btcc.web.login.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by peiyou on 10/24/16.
 */
public class User implements Serializable{



    private Integer userId;

    private String username;

    private String password;

    private String twofactorpwd;

    private int remember;

    private String jwt ;
    /**
     * 权限
     */
    private List<Role> roles;

    /**
     * 菜单
     */
    private List<Menu> menus;


    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public User(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getTwofactorpwd() {
        return twofactorpwd;
    }

    public void setTwofactorpwd(String twofactorpwd) {
        this.twofactorpwd = twofactorpwd;
    }

    public int getRemember() {
        return remember;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
