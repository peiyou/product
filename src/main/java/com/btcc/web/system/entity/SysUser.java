package com.btcc.web.system.entity;

import com.btcc.util.BaseBean;

/**
 * Created by peiyou on 2016/11/14.
 */
public class SysUser extends BaseBean {
    private Integer id;

    private String username;

    private Integer disable;

    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
