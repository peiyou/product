package com.btcc.web.system.service;

import com.btcc.Parameter;
import com.btcc.web.system.entity.*;
import com.btcc.web.system.mapper.SystemMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by peiyou on 2016/11/14.
 */
@Service
public class SystemService {

    private Logger logger = LoggerFactory.getLogger(SystemService.class);
    @Autowired
    SystemMapper systemMapper;

    public void insertSysUser(SysUser user){
        systemMapper.insertSysUser(user);
    }

    public void updateSysUserById(SysUser user){
        systemMapper.updateSysUserById(user);
    }

    public void deleteSysUserById(SysUser user){
        systemMapper.deleteSysUserById(user);
    }

    public PageInfo<SysUser> querySysUser(SysUser user){
        PageInfo<SysUser> pageInfo = null;
        PageHelper.startPage(user.getPageNum(),user.getPageSize());
        Parameter parameter = new Parameter(user);
        List<SysUser> list = systemMapper.querySysUser(parameter);
        pageInfo = new PageInfo<SysUser>(list);
        return pageInfo;
    }

    public List<SysRole> selectRoleByUserId(Integer userId){
        return systemMapper.selectRoleByUserId(userId);
    }

    public List<SysRole> querySysRole(SysRole sysRole){
        return systemMapper.querySysRole(new Parameter(sysRole));
    }

    public void insertSysUserRole(SysUserRole userRole){
        systemMapper.insertSysUserRole(userRole);
    }

    public void deleteSysUserRoleByUserId(SysUserRole userRole){
        systemMapper.deleteSysUserRoleByUserId(userRole);
    }

    public void insertSysRole(SysRole role){
        systemMapper.insertSysRole(role);
    }

    public void updateSysRoleById(SysRole role){
        systemMapper.updateSysRoleById(role);
    }

    public void deleteSysRoleById(SysRole role){
        systemMapper.deleteSysRoleById(role);
    }

    public void insertSysMenu(SysMenu menu){
        systemMapper.insertSysMenu(menu);
    }

    public void updateSysMenuById(SysMenu menu){
        systemMapper.updateSysMenuById(menu);
    }

    public void deleteSysMenuById(SysMenu menu){
        systemMapper.deleteSysMenuById(menu);
    }

    /**
     * 查询是目录的菜单
     * @return
     */
    public List<SysMenu> querySysMenuByDirectory(){
        return systemMapper.querySysMenuByDirectory();
    }

    public PageInfo<SysMenu> querySysMenu(SysMenu menu){
        PageInfo<SysMenu> pageInfo = null;
        PageHelper.startPage(menu.getPageNum(),menu.getPageSize());
        Parameter parameter = new Parameter(menu);
        List<SysMenu> list =  systemMapper.querySysMenu(parameter);
        pageInfo = new PageInfo<SysMenu>(list);
        return pageInfo;
    }

    public void insertSysRoleMenu(SysMenuRole menuRole){
        systemMapper.insertSysRoleMenu(menuRole);
    }

    public void deleteSysRoleMenuId(SysMenuRole menuRole){
        systemMapper.deleteSysRoleMenuId(menuRole);
    }

    public List<SysMenuRole> querySysRoleMenu(SysMenuRole menuRole){
        Parameter parameter = new Parameter(menuRole);
        return systemMapper.querySysRoleMenu(parameter);
    }



    public PageInfo<BuildReport> queryBuildReport(BuildReport buildReport){
        PageInfo<BuildReport> pageInfo = null;
        PageHelper.startPage(buildReport.getPageNum(),buildReport.getPageSize());
        Parameter parameter = new Parameter(buildReport);
        List<BuildReport> list =systemMapper.queryBuildReport(parameter);
        pageInfo = new PageInfo<BuildReport>(list);
        return pageInfo;
    }

    /**
     * 删除buildReport的数据
     * 先删除对应的权限
     * 再删除对应的菜单
     * 最后删除数据
     * @param buildReport
     */
    public void deleteBuildReport(BuildReport buildReport){
        List<SysMenu> list = systemMapper.queryBuildReportOfMenuById(buildReport.getId());
        if(list != null){
            for(SysMenu menu:list){
                SysMenuRole sysMenuRole = new SysMenuRole();
                sysMenuRole.setMenuId(menu.getId());
                systemMapper.deleteSysRoleMenuId(sysMenuRole);
                systemMapper.deleteSysMenuById(menu);
            }

        }
        systemMapper.deleteBuildReport(buildReport);
    }

    public void insertBuildReport(BuildReport buildReport){
        if(buildReport.getRemark()==null)
            buildReport.setRemark("");
        systemMapper.insertBuildReport(buildReport);
        SysMenu menu = new SysMenu();
        menu.setMenuName(buildReport.getMenuName());
        menu.setUrl("/baseController/"+buildReport.getId()+"/index");
        systemMapper.insertSysMenu(menu);
        SysMenuRole menuRole = new SysMenuRole();
        menuRole.setMenuId(menu.getId());
        menuRole.setRoleId(1);
        systemMapper.insertSysRoleMenu(menuRole);
    }

    public void updateBuildReport(BuildReport buildReport){
        if(buildReport.getMenuName()!=null) {
            List<SysMenu> list = systemMapper.queryBuildReportOfMenuById(buildReport.getId());
            if (list != null) {
                for (SysMenu menu : list) {
                    menu.setMenuName(buildReport.getMenuName());
                    updateSysMenuById(menu);
                }
            }
        }
        systemMapper.updateBuildReport(buildReport);
    }


    public PageInfo<WorkFlowSql> queryWorkFlowSql(WorkFlowSql workFlowSql){
        PageInfo<WorkFlowSql> pageInfo = null;
        PageHelper.startPage(workFlowSql.getPageNum(),workFlowSql.getPageSize());
        Parameter parameter = new Parameter(workFlowSql);
        List<WorkFlowSql> list = systemMapper.queryWorkFlowSql(parameter);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public WorkFlowSql queryWorkFlowSqlById(Long id){
        if(id == null) return null;
        WorkFlowSql workFlowSql = new WorkFlowSql();
        workFlowSql.setId(id);
        PageInfo<WorkFlowSql> pageInfo = this.queryWorkFlowSql(workFlowSql);
        if(pageInfo != null){
            List<WorkFlowSql> list = pageInfo.getList();
            if(list != null && list.size() > 0){
                return list.get(0);
            }
        }
        return null;
    }

    public void updateWorkFlowSql(WorkFlowSql workFlowSql){
        systemMapper.updateWorkFlowSql(workFlowSql);
    }

    public void insertWorkFlowSql(WorkFlowSql workFlowSql){
        systemMapper.insertWorkFlowSql(workFlowSql);
    }

    public void deleteWorkFlowSql(WorkFlowSql workFlowSql){
        systemMapper.deleteWorkFlowSql(workFlowSql);
    }
}


