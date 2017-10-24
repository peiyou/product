package com.btcc.web.login.mapper;

import com.btcc.datasource.MkDataSource;

/**
 * Created by peiyou on 17/2/8.
 */
@MkDataSource
public interface LoginBtcchinaMkMapper {
    /**
     * 登录的用户是否开通google验证
     * @param username
     * @return
     */
    public int selectUserByOptKey(String username);
}
