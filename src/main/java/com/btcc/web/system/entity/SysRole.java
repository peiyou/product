package com.btcc.web.system.entity;

import com.btcc.util.BaseBean;

/**
 * Created by peiyou on 2016/11/14.
 */
public class SysRole extends BaseBean {
    private Integer id;
    private String role;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
