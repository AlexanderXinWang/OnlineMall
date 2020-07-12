package com.xju.onlinemall.controller;

import com.xju.onlinemall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private UserService userService;

    /*@RequestMapping("/")
    public String register() {
        return "/templates/my-account.html";
    }*/

}
