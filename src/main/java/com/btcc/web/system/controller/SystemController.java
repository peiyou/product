package com.btcc.web.system.controller;

import com.btcc.constant.LoginConstant;
import com.btcc.web.login.controller.LoginController;
import com.btcc.web.login.entity.Role;
import com.btcc.web.login.entity.User;
import com.btcc.web.login.service.LoginService;
import com.btcc.web.system.entity.*;
import com.btcc.web.system.service.SystemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by peiyou on 2016/11/14.
 */
@Controller
@RequestMapping("/SystemController")
public class SystemController {

    private Logger logger = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    SystemService systemService;

    @Autowired
    LoginService loginService;

    @RequestMapping("/userIndex")
    public ModelAndView userIndex(SysUser user, ModelAndView modelAndView){
        PageInfo<SysUser> pageinfo = systemService.querySysUser(user);
        modelAndView.addObject("pageinfo",pageinfo);
        modelAndView.addObject("from",user);
        modelAndView.setViewName("/system/user-index");
        return modelAndView;
    }


    @RequestMapping("/editUser")
    public ModelAndView editUser(SysUser user, ModelAndView modelAndView){
        List<SysUser> page = null;
        List<SysRole> currentRoles = null;
        if(user.getId() != null){
            PageInfo<SysUser> pageinfo = systemService.querySysUser(user);
            page = pageinfo.getList();
            currentRoles = systemService.selectRoleByUserId(user.getId());
        }
        List<SysRole> roles = systemService.querySysRole(new SysRole());
        modelAndView.addObject("from",page!=null && page.size() > 0 ?page.get(0):new SysUser());
        modelAndView.addObject("currentRoles",currentRoles);
        modelAndView.addObject("roles",roles);
        modelAndView.setViewName("/system/user-edit");
        return modelAndView;
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(SysUser user, ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getId());
            systemService.deleteSysUserRoleByUserId(userRole);
            systemService.deleteSysUserById(user);
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();

    }

    @RequestMapping(value = "/insertUser" , method = RequestMethod.POST)
    @ResponseBody
    public String insertUser(SysUser user, @RequestParam(value = "roles[]",required = false) List<Integer> roles, ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try {
            systemService.insertSysUser(user);
            logger.info("insert sys_user -----> user name :"+user.getUsername() + "\t id:"+user.getId());
            if(roles != null && user.getId()!=null){
                for(Integer roleId:roles){
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(user.getId());
                    systemService.insertSysUserRole(userRole);
                }
            }
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping(value = "/updateUser" , method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(SysUser user,@RequestParam(value = "roles[]",required = false)List<Integer> roles,
                             ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try{
            systemService.updateSysUserById(user);
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getId());
            systemService.deleteSysUserRoleByUserId(userRole);
            if(roles != null) {
                for (Integer roleId : roles) {
                    userRole = new SysUserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    systemService.insertSysUserRole(userRole);
                }
            }
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }



    @RequestMapping("/roleIndex")
    public ModelAndView roleIndex(SysRole role,ModelAndView modelAndView){
        List<SysRole> list = systemService.querySysRole(role);
        modelAndView.setViewName("/system/role-index");
        modelAndView.addObject("from",role);
        modelAndView.addObject("roles",list);
        return modelAndView;
    }

    @RequestMapping("/editRole")
    public ModelAndView editRole(SysRole role,ModelAndView modelAndView){
        List<SysRole> list = null;
        List<SysMenuRole> menuRoles = null;
        if(role.getId() != null){
            list = systemService.querySysRole(role);
            SysMenuRole menuRole = new SysMenuRole();
            menuRole.setRoleId(role.getId());
            menuRoles = systemService.querySysRoleMenu(menuRole);
        }
        List<SysMenu> menus =null;
        PageInfo<SysMenu> pageInfo = systemService.querySysMenu(new SysMenu());
        menus = pageInfo.getList();
        modelAndView.setViewName("/system/role-edit");
        modelAndView.addObject("from",list != null && list.size()>0 ? list.get(0) : new SysRole());
        modelAndView.addObject("currentMenus",
                menuRoles !=null && menuRoles.size()>0 ?
                        JSONArray.fromObject(menuRoles).toString():
                        new JSONObject().toString());

        modelAndView.addObject("menus",menus !=null && menus.size()>0 ?
                JSONArray.fromObject(menus).toString():
                new JSONObject().toString());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    @ResponseBody
    public String deleteRole(SysRole role, ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try {
            SysMenuRole menuRole = new SysMenuRole();
            menuRole.setRoleId(role.getId());
            systemService.deleteSysRoleMenuId(menuRole);//删除对应的菜单关系
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(role.getId());
            systemService.deleteSysUserRoleByUserId(userRole);//删除对应的用户关系
            systemService.deleteSysRoleById(role);//删除角色
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();

    }

    @RequestMapping(value = "/insertRole" , method = RequestMethod.POST)
    @ResponseBody
    public String insertRole(SysRole role, @RequestParam(value = "menus[]",required=false) List<Integer> menus, ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try {
            systemService.insertSysRole(role);
            logger.info("insert sys_role -----> role name :"+role.getRole() + "\t id:"+role.getId());
            if(menus != null && role.getId()!=null){
                for(Integer menu:menus){
                    SysMenuRole menuRole = new SysMenuRole();
                    menuRole.setMenuId(menu);
                    menuRole.setRoleId(role.getId());
                    systemService.insertSysRoleMenu(menuRole);
                }
            }
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping(value = "/updateRole" , method = RequestMethod.POST)
    @ResponseBody
    public String updateRole(SysRole role,@RequestParam(value = "menus[]",required=false)List<Integer> menus,
                             ModelAndView modelAndView){
        JSONObject obj = new JSONObject();
        try{
            systemService.updateSysRoleById(role);
            SysMenuRole menuRole = new SysMenuRole();
            menuRole.setRoleId(role.getId());
            systemService.deleteSysRoleMenuId(menuRole);
            if(menus != null) {
                for (Integer menu : menus) {
                    menuRole = new SysMenuRole();
                    menuRole.setMenuId(menu);
                    menuRole.setRoleId(role.getId());
                    systemService.insertSysRoleMenu(menuRole);
                }
            }
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }


    /**
     * 菜单
     */
    @RequestMapping("/menuIndex")
    public ModelAndView menuIndex(SysMenu menu,ModelAndView modelAndView){
        PageInfo<SysMenu> pageInfo = systemService.querySysMenu(menu);
        modelAndView.setViewName("/system/menu-index");
        modelAndView.addObject("from",menu);
        modelAndView.addObject("menulist",pageInfo.getList()!=null && pageInfo.getList().size()>0
                ? JSONArray.fromObject(pageInfo.getList()).toString():new JSONArray().toString());
        return modelAndView;
    }

    @RequestMapping("/editMenu")
    public ModelAndView editMenu(SysMenu menu,ModelAndView modelAndView){
        PageInfo<SysMenu> pageInfo = null;
        List<SysMenu> menuDirectory = systemService.querySysMenuByDirectory();
        if(menu.getId() != null){
            pageInfo = systemService.querySysMenu(menu);
        }
        modelAndView.setViewName("/system/menu-edit");
        List<SysMenu> list = null;
        if(pageInfo != null)
            list = pageInfo.getList();
        modelAndView.addObject("from",list != null && list.size()>0 ? list.get(0) : new SysMenu());
        modelAndView.addObject("menuDirectory",menuDirectory!=null && menuDirectory.size()>0
                ? JSONArray.fromObject(menuDirectory).toString()
                : new JSONArray().toString());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteMenu",method = RequestMethod.POST)
    @ResponseBody
    public String deleteMenu(SysMenu menu, ModelAndView modelAndView, HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try {
            SysMenuRole menuRole = new SysMenuRole();
            menuRole.setMenuId(menu.getId());
            systemService.deleteSysRoleMenuId(menuRole);//删除对应的菜单关系
            systemService.deleteSysMenuById(menu);//删除菜单
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();

    }

    @RequestMapping(value = "/insertMenu" , method = RequestMethod.POST)
    @ResponseBody
    public String insertMenu(SysMenu menu,  ModelAndView modelAndView,HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try {
            systemService.insertSysMenu(menu);
            logger.info("insert sys_menu -----> menu name :"+menu.getMenuName() + "\t id:"+menu.getId());
            //管理员
            SysMenuRole menuRole = new SysMenuRole();
            menuRole.setRoleId(1);
            menuRole.setMenuId(menu.getId());
            systemService.insertSysRoleMenu(menuRole);
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping(value = "/updateMenu" , method = RequestMethod.POST)
    @ResponseBody
    public String updateMenu(SysMenu menu,
                             ModelAndView modelAndView,HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try{
            systemService.updateSysMenuById(menu);
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            logger.error(e.getMessage());
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }


    @RequestMapping(value = "/buildReportIndex")
    public ModelAndView buildReportIndex(ModelAndView modelAndView,BuildReport buildReport){
        PageInfo<BuildReport> pageInfo = systemService.queryBuildReport(buildReport);
        modelAndView.addObject("from",buildReport);
        modelAndView.addObject("pageinfo",pageInfo);
        modelAndView.setViewName("/system/build-report-index");
        return modelAndView;
    }

    @RequestMapping("/editBuildReport")
    public ModelAndView editBuildReport(ModelAndView modelAndView,Integer id){
        if(id != null)
            modelAndView.addObject("id",id);

        modelAndView.setViewName("/system/build-report-edit");
        return modelAndView;

    }


    @RequestMapping(value = "/queryBuildReport",method = RequestMethod.POST)
    @ResponseBody
    public String queryBuildReport(ModelAndView modelAndView,BuildReport buildReport){
        JSONObject object = new JSONObject();
        if(buildReport.getId() != null) {
            PageInfo<BuildReport> info = systemService.queryBuildReport(buildReport);
            if(info.getList()!=null && info.getList().size() > 0){
                buildReport = info.getList().get(0);
                object.put("params",JSONObject.fromObject(buildReport.getParams()));
                object.put("success",true);
                object.put("buildReport",buildReport);
                return object.toString();
            }
        }
        object.put("success",false);
        return object.toString();
    }

    @RequestMapping("/deleteBuildReport")
    @ResponseBody
    public String deleteBuildReport(ModelAndView modelAndView,BuildReport buildReport,
                                    HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try {
            systemService.deleteBuildReport(buildReport);
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            e.printStackTrace();
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();

    }


    @RequestMapping("/insertBuildReport")
    @ResponseBody
    public String insertBuildReport(ModelAndView modelAndView,BuildReport buildReport,
                                    HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try{
            systemService.insertBuildReport(buildReport);
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            e.printStackTrace();
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }

    @RequestMapping("/updateBuildReport")
    @ResponseBody
    public String updateBuildReport(ModelAndView modelAndView,BuildReport buildReport,
                                    HttpServletRequest request){
        JSONObject obj = new JSONObject();
        try{
            systemService.updateBuildReport(buildReport);
            loginService.setRoleAndMenuOfUser((User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER),request);
        }catch (Exception e){
            obj.put("success",false);
            e.printStackTrace();
            return obj.toString();
        }
        obj.put("success",true);
        return obj.toString();
    }

    /**
     * 查询sql流程式方法
     * @param modelAndView
     * @return
     */
    @RequestMapping("/sqlWorkFlowIndex")
    public ModelAndView sqlWorkFlowIndex(ModelAndView modelAndView,WorkFlowSql workFlowSql){
        if(workFlowSql == null){
            workFlowSql = new WorkFlowSql();
        }
        if(workFlowSql.getPageSize()==0){
            workFlowSql.setPageNum(1);
            workFlowSql.setPageSize(50);
        }
        PageInfo<WorkFlowSql> pageinfo = systemService.queryWorkFlowSql(workFlowSql);
        modelAndView.addObject("pageinfo",pageinfo);
        modelAndView.addObject("form",workFlowSql);
        modelAndView.setViewName("/system/work-flow-sql-index");
        return modelAndView;
    }

    /**
     * 编辑sql流程式方法
     * @param modelAndView
     * @param id
     * @return
     */
    @RequestMapping("/sqlWorkFlowEdit")
    public ModelAndView sqlWorkFlowEdit(ModelAndView modelAndView,Long id){
        WorkFlowSql workFlowSql = new WorkFlowSql();
        workFlowSql.setId(id);
        modelAndView.addObject("workFlowSql",workFlowSql);
        modelAndView.setViewName("/system/work-flow-sql-edit");
        return modelAndView;
    }

    @RequestMapping("/sqlWorkFlowById")
    @ResponseBody
    public String sqlWorkFlowById(ModelAndView modelAndView,Long id){
        JSONObject obj = new JSONObject();
        WorkFlowSql workFlowSql = systemService.queryWorkFlowSqlById(id);
        obj.put("workFlowSql",workFlowSql);
        return obj.toString();
    }

    @RequestMapping("/insertWorkSqlFlow")
    @ResponseBody
    public String insertWorkSqlFlow(ModelAndView modelAndView,WorkFlowSql workFlowSql){
        JSONObject obj = new JSONObject();
        try {
            systemService.insertWorkFlowSql(workFlowSql);
            obj.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            obj.put("success",false);
        }

        return obj.toString();
    }

    @RequestMapping("/updateWorkSqlFlow")
    @ResponseBody
    public String updateWorkSqlFlow(ModelAndView modelAndView,WorkFlowSql workFlowSql){
        JSONObject obj = new JSONObject();
        try {
            systemService.updateWorkFlowSql(workFlowSql);
            obj.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            obj.put("success",false);
        }
        return obj.toString();
    }

    @RequestMapping("/deleteWorkSqlFlow")
    @ResponseBody
    public String deleteWorkSqlFlow(ModelAndView modelAndView,WorkFlowSql workFlowSql){
        JSONObject obj = new JSONObject();
        try {
            systemService.deleteWorkFlowSql(workFlowSql);
            obj.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            obj.put("success",false);
        }
        return obj.toString();
    }
}
