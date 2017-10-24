package com.btcc.web.login.mapper;

import com.btcc.datasource.ReportDataSource;
import com.btcc.web.login.entity.LoginLog;
import com.btcc.web.login.entity.Menu;
import com.btcc.web.login.entity.Role;
import com.btcc.web.login.entity.User;
import com.btcc.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by peiyou on 10/26/16.
 */
@ReportDataSource
public interface LoginMapper extends BaseMapper<User>{

    public User selectUserByUsername(String username);

    public List<Role> selectRoleByUserId(Integer userId);

    public List<Menu> selectMenuByRoleId(@Param("roles") List<Integer> roles);

    public void insertLoginLog(LoginLog log);
}
