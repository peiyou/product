package com.btcc.web.login.controller;

import com.btcc.constant.LoginConstant;
import com.btcc.web.login.entity.LoginLog;
import com.btcc.web.login.entity.Menu;
import com.btcc.web.login.entity.Role;
import com.btcc.web.login.entity.User;
import com.btcc.web.login.service.LoginService;
import com.btcc.util.DateUtils;
import com.btcc.util.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peiyou on 10/24/16.
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @Value(value = "${check.login}")
    private boolean checkLogin = true;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET )
    public ModelAndView login(ModelAndView model){
        model.setViewName("login");
        return model;
    }


    @RequestMapping(value = "/checkLogin",method = RequestMethod.POST)
    @ResponseBody
    public String checkLogin(ModelAndView model, User user, HttpServletRequest request){
        System.out.println("进入Login方法中......");
        JSONObject obj = new JSONObject();
        try {
            //开启登录google验证的用户才可以登录
            /*if(!(!checkLogin || loginService.selectUserByOptKey(user.getUsername()) > 0)){
                obj.put("success","0");
                obj.put("message","用户未开通google登录验证，不能登录！");
                return obj.toString();
            }*/

            //此处的登录暂时不用， 正式测试的时候再加入
            if( !checkLogin || loginService.basicLogin(user)) {
            //if(true){
                try {
                    UsernamePasswordToken token = new UsernamePasswordToken();
                    Subject currentUser = SecurityUtils.getSubject();
                    currentUser.getSession().setTimeout(7200000);
                    token.setPassword(user.getPassword().toCharArray());
                    token.setUsername(user.getUsername());
                    if (!currentUser.isAuthenticated()) {
                        //使用shiro来验证
                        if (1 == user.getRemember())
                            token.setRememberMe(true);
                        currentUser.login(token);//验证角色和权限
                        UserInfo(user, request);
                        //加入登录日志
                        addLog(user, request);
                    }
                    //model.setViewName("/TestController/index");
                    obj.put("success", "1");
                    obj.put("url", "/checkRole");
                    return obj.toString();
                } catch (AuthenticationException e) {
                    obj.put("success", "0");
                    obj.put("message", "请确认用户名、密码或验证码是否正确！");
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        obj.put("success","0");
        obj.put("message","请确认用户名、密码或验证码是否正确！");
        return obj.toString();
    }

    @RequestMapping("checkRole")
    public ModelAndView checkRole(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        User u = (User)request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER);
        if(u != null && u.getMenus()!=null && u.getMenus().size() > 0){
            if(u.getMenus().get(0)!=null){
                for(Menu menu : u.getMenus()){
                    if(menu.getUrl() != null && !menu.getUrl().equals("")){
                        modelAndView.setViewName("redirect:"+menu.getUrl());
                        break;
                    }
                }
                return modelAndView;
            }
        }
        modelAndView.setViewName("redirect:/403");
        return modelAndView;
    }

    /**
     * 登录日志
     * @param user
     * @param request
     */
    private void addLog(User user, HttpServletRequest request) {
        LoginLog log = new LoginLog();
        log.setIp(HttpUtils.getIpAddr(request));
        log.setUserName(user.getUsername());
        log.setLoginTime(DateUtils.dateFormat(new Date(),DateUtils.DATE_FULL_STR));
        loginService.insertLoginLog(log);
    }

    /**
     * 当前登录人的一些信息
     * @param user
     * @param request
     */
    private void UserInfo(User user, HttpServletRequest request) {
        //查询当前系统下的user做一个匹配
        User u = loginService.selectUserByUserName(user.getUsername());
        user.setUserId(u.getUserId());
        //设置权限信息
        loginService.setRoleAndMenuOfUser(user,request);
    }


    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ModelAndView loginout(ModelAndView model,HttpServletRequest request){
        SecurityUtils.getSubject().logout();
        request.getSession().removeAttribute(LoginConstant.CURRENT_LOGIN_USER);
        request.getSession().removeAttribute(LoginConstant.CURRENT_LOGIN_USER_MENU);
        model.setViewName("/login");
        return model;
    }

    @RequestMapping("/403")
    public ModelAndView forbidden(ModelAndView model){
        model.setViewName("403");
        return model;
    }



}
