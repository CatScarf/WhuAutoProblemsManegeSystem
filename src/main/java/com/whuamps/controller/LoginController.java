package com.whuamps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController {
    @PostMapping("/toAuto")
    public String toProblems(){
        return "redirect:/auto";
    }

    @PostMapping("/reLogin")
    public String reLogin(Map<String,Object> map){
        map.put("msg", "用户名或密码错误");
        //System.out.println("用户名或密码错误");
        return "login";
    }
}
