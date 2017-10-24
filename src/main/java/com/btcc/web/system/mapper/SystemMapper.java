package com.btcc.web.system.mapper;

import com.btcc.Parameter;
import com.btcc.datasource.ReportDataSource;
import com.btcc.util.BaseMapper;
import com.btcc.web.system.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/14.
 */
@ReportDataSource
public interface SystemMapper extends BaseMapper {

    void insertSysUser(SysUser user);

    void updateSysUserById(SysUser user);

    void deleteSysUserById(SysUser user);

    List<SysUser> querySysUser(Map<String,Object> map);

    void insertSysRole(SysRole role);

    void updateSysRoleById(SysRole role);

    void deleteSysRoleById(SysRole role);

    List<SysRole> querySysRole(Map<String,Object> map);

    List<SysRole> selectRoleByUserId(Integer userId);

    void insertSysUserRole(SysUserRole userRole);

    void deleteSysUserRoleByUserId(SysUserRole userRole);

    List<SysUserRole> querySysUserRole(Map<String,Object> map);

    void insertSysMenu(SysMenu menu);

    void updateSysMenuById(SysMenu menu);

    void deleteSysMenuById(SysMenu menu);

    List<SysMenu> querySysMenu(Map<String,Object> map);

    void insertSysRoleMenu(SysMenuRole menuRole);

    void deleteSysRoleMenuId(SysMenuRole menuRole);

    List<SysMenuRole> querySysRoleMenu(Map<String,Object> map);

    /**
     * 查询是目录的菜单
     * @return
     */
    List<SysMenu> querySysMenuByDirectory();


    List<BuildReport> queryBuildReport(Map<String,Object> map);

    void insertBuildReport(BuildReport buildReport);

    void updateBuildReport(BuildReport buildReport);

    void deleteBuildReport(BuildReport buildReport);

    /**
     * 根据buildReport的id查询对应的菜单ID
     * @param id
     * @return
     */
    List<SysMenu> queryBuildReportOfMenuById(int id);


    List<WorkFlowSql> queryWorkFlowSql(Parameter parameter);

    void updateWorkFlowSql(WorkFlowSql workFlowSql);

    void insertWorkFlowSql(WorkFlowSql workFlowSql);

    void deleteWorkFlowSql(WorkFlowSql workFlowSql);
}
