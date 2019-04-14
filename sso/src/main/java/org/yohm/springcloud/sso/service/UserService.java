package org.yohm.springcloud.sso.service;

import org.yohm.springcloud.sso.model.JsonResponse;
import org.yohm.springcloud.sso.model.User;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能简述
 * (登录相关实现)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     */
    JsonResponse register(User user, HttpServletResponse response);

    /**
     * 用户登录
     *
     * @param user     用户信息
     * @return token   登录凭据
     */
    JsonResponse login(User user, HttpServletResponse response);

    /**
     * 用户登出
     *
     * @param response
     * @return
     */
    JsonResponse logout(HttpServletResponse response);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     */
    JsonResponse query(String username);
}
