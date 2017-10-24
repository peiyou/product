package com.btcc.filter;

import com.btcc.conf.UrlProperties;
import com.btcc.constant.LoginConstant;
import com.btcc.web.login.entity.Menu;
import com.btcc.web.login.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by peiyou on 10/26/16.
 */
@WebFilter(filterName = "urlFilter",urlPatterns = "/*")
@Component
public class UrlFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(UrlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String path = request.getServletPath();
        String basePath = request.getContextPath();
        String method = request.getMethod();
        if(method.equals("GET")) {//只验证get请求
            //登录了则进入
            if (request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER)!=null) {
                User user = (User) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER);
                List<Menu> menus = user.getMenus();
                if (menus == null) {//菜单为空，说明没有任何权限
                    response.sendRedirect(basePath + "/403");
                    return;
                }
                if(isQualification(request, response)){
                    filterChain.doFilter(request, response);
                }else{
                    response.sendRedirect(basePath + "/checkRole");
                    return ;
                }

            }else{
                //没有登录时，直接放行，因为有shiro的机制会去做相应的跳转的
                filterChain.doFilter(request, response);
            }
        }else{//不是GET请求，直接放行
            if(method.equals("POST")){
                try {
                    String referer =  request.getHeader("Referer");
                    if(referer==null || !isPostQualification(request,response)){
                        response.sendRedirect(basePath + "/403");
                        return;
                    }
                }catch (Exception e ){
                    logger.error(e.getMessage());
                    filterChain.doFilter(request, response);
                }
            }

            filterChain.doFilter(request, response);
        }

    }

    /**
     * 是否有权限进入action
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private boolean isQualification(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getServletPath();
        //得到用户的菜单列表
        return isQualification(request, path);
    }

    private boolean isPostQualification(HttpServletRequest request,HttpServletResponse response) {
        String basePath = request.getContextPath();
        String referer =  request.getHeader("Referer");
        String origin = request.getHeader("Origin");
        String hostPath = (origin + basePath);
        String lastPath = referer.substring(referer.indexOf(hostPath, 0) + hostPath.length());
        if(lastPath.indexOf("?") >= 0){
            lastPath = lastPath.substring(0,lastPath.indexOf("?"));
        }
        return isQualification(request, lastPath);
    }

    private boolean isQualification(HttpServletRequest request, String lastPath) {
        //得到用户的菜单列表
        Set<String> urls = (Set<String>) request.getSession().getAttribute(LoginConstant.CURRENT_LOGIN_USER_MENU);
        if (urls.contains(lastPath)) {//菜单列表中存在这个URL，说明用户可以访问
            return true;
        } else {//不存在菜单列表中时
            // 先判断是否在urlFilter.properties文件中添加了例外
            List<String> list = UrlProperties.exclude;
            for (String url : list) {
                if (lastPath.indexOf(url) > -1) {//如果是例外的url，说明可以访问
                    return true;
                }
            }
            //上面的情况都不符合，说明这个用户是自己输入URL的，只能让其联系管理员。
            return false;
        }
    }

    @Override
    public void destroy() {

    }


}
