package org.yohm.springcloud.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yohm.springcloud.sso.service.UserService;
import org.yohm.springcloud.sso.model.JsonResponse;
import org.yohm.springcloud.sso.model.User;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能简述
 * (用户登录相关接口)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResponse register(@Validated @RequestBody User user, HttpServletResponse response) {
        return userService.register(user,response);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login(@Validated @RequestBody User user, HttpServletResponse response) {
        return userService.login(user,response);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResponse logout(HttpServletResponse response) {
        return userService.logout(response);
    }
}
