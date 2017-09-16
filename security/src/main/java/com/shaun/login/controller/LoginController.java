package com.shaun.login.controller;

import com.alibaba.fastjson.JSON;
import com.shaun.commons.controller.BaseController;
import com.shaun.commons.entity.MyUser;
import com.shaun.commons.util.Md5EncodeUtil;
import com.shaun.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/5.
 */
@Controller
public class LoginController extends BaseController {

    //    LoginService loginService = (LoginService) super.getService("loginService");
    @Autowired
    LoginService loginService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "login.do")
    public String login() {
        return "/login.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "checkUser.do")
    public String checkUser(String userName) {
        Map map = loginService.queryByUserName(userName);
        Map resMap = new HashMap();
        if (map == null) {
            resMap.put("success", false);
            resMap.put("msg", "用户名不存在");
        } else {
            resMap.put("success", true);
        }
        return JSON.toJSONString(resMap);
    }

    @RequestMapping(value = "loginAction.do")
    @ResponseBody
    public String loginAction(String userName, String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        userName = userName.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, pwd);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        Map map = new HashMap();
        map.put("success",true);
        return JSON.toJSONString(map);
    }


    /**
     * @return
     */
    @RequestMapping("loginSuccessAction.do")
    public String loginSuccessAction() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(o instanceof UserDetails)) {

        } else {
            MyUser user = (MyUser) o;
            this.request.setAttribute("userName", user.getUsername());
        }
        return null;
    }
}
