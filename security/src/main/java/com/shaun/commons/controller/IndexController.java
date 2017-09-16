package com.shaun.commons.controller;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * @Description
 * @Date Created on 2017/9/8.
 */
@Controller
public class IndexController {
    @RequestMapping("indexAction.do")
    public String IndexAction(){
        return "index";
    }
}
