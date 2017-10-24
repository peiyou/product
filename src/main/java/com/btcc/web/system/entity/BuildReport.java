package com.btcc.web.system.entity;

import com.btcc.util.BaseBean;

/**
 * Created by peiyou on 2016/11/21.
 */
public class BuildReport extends BaseBean{
    private Integer id;

    private String params;

    private String remark;

    private String menuName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
