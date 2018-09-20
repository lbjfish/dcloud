package com.sida.dcloud.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Xiruo on 2017/7/25.
 */
@Controller
@SessionAttributes("authorizationRequest")
public class OAuth2PageController {
    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request)
            throws Exception {

        return "authorize";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLogin(Map<String, Object> model, HttpServletRequest request)
            throws Exception {
        System.out.println("============="+request.getRequestURL()+request.getMethod());
        return "login";
    }
    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "hello";
    }
}