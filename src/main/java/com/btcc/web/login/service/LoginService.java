package com.btcc.web.login.service;

import com.btcc.constant.LoginConstant;
import com.btcc.web.login.entity.LoginLog;
import com.btcc.web.login.entity.Menu;
import com.btcc.web.login.entity.Role;
import com.btcc.web.login.entity.User;
import com.btcc.web.login.mapper.LoginBtcchinaMkMapper;
import com.btcc.web.login.mapper.LoginMapper;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by peiyou on 10/25/16.
 */

@Service
public class LoginService {
    private Logger log = LoggerFactory.getLogger(LoginService.class);

    @Value(value = "${login.authentication.url}")
    public String authenticationUrl = "https://api-staging.btcc.com/api.php/account/authenticate";

    @Value(value = "${login.authentication.host}")
    private String authenticationHost = "api-staging.btcc.com";
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    LoginBtcchinaMkMapper loginBtcchinaMkMapper;

    public boolean basicLogin(User user) throws Exception {
        HttpHost targetHost = new HttpHost(authenticationHost, 443, "https");
        DefaultHttpClient httpclient = new DefaultHttpClient();
        BasicHttpContext localcontext = new BasicHttpContext();
        httpclient.getCredentialsProvider()
                .setCredentials(new AuthScope(authenticationHost,443,AuthScope.ANY_REALM), new UsernamePasswordCredentials(user.getUsername(), user.getPassword()));
        AuthCache authCache = new BasicAuthCache();
// Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        List<NameValuePair> postparams = new ArrayList<NameValuePair>();
        postparams.add(new BasicNameValuePair("captcha",""));
        postparams.add(new BasicNameValuePair("twofactorpwd",user.getTwofactorpwd()));
        postparams.add(new BasicNameValuePair("keepLogin","false"));
        postparams.add(new BasicNameValuePair("msie10","false"));
        log.info("login url :" + authenticationUrl);
        try{
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postparams,"UTF-8");
            HttpPost httpPost = new HttpPost(authenticationUrl);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(targetHost,httpPost,localcontext);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                Header h = httpResponse.getFirstHeader("Json-Web-Token");
                if(h!=null)
                    user.setJwt(h.getName());
                else
                    return false;
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            log.error("error",e);
        }
        return false;
    }


    public User selectUserByUserName(String username){
        if(username == null) return null;
        return loginMapper.selectUserByUsername(username);
    }

    public List<Role> selectRoleByUserId(Integer userId){
        if(userId == null) return null;
        return loginMapper.selectRoleByUserId(userId);
    }

    public List<Menu> selectMenuByRoleId(List<Role> roles){
        List<Integer> list  = new ArrayList<>();
        if(roles == null) return null;

        for(Role r : roles){
            list.add(r.getId());
        }
        return loginMapper.selectMenuByRoleId(list);
    }

    public void insertLoginLog(LoginLog log){
        loginMapper.insertLoginLog(log);
    }


    public void setRoleAndMenuOfUser(User user, HttpServletRequest request){
        //查询权限
        List<Role> roles = this.selectRoleByUserId(user.getUserId());
        //查询菜单
        List<Menu> menus = this.selectMenuByRoleId(roles);
        Set<String> urls = new HashSet<>();
        for(Menu r : menus){
            urls.add(r.getUrl().substring(0,r.getUrl().indexOf("?")==-1?r.getUrl().length():r.getUrl().indexOf("?")));
        }
        user.setRoles(roles);
        user.setMenus(menus);
        request.getSession().setAttribute(LoginConstant.CURRENT_LOGIN_USER,user);
        request.getSession().setAttribute(LoginConstant.CURRENT_LOGIN_USER_MENU,urls);
    }

    public int selectUserByOptKey(String username){
       return loginBtcchinaMkMapper.selectUserByOptKey(username);
    }


}
