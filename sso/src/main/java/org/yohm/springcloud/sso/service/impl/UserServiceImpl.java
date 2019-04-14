package org.yohm.springcloud.sso.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.yohm.springcloud.sso.service.UserService;
import org.yohm.springcloud.sso.constant.CommonConst;
import org.yohm.springcloud.sso.mapper.UserMapper;
import org.yohm.springcloud.sso.model.JsonResponse;
import org.yohm.springcloud.sso.model.User;
import org.yohm.springcloud.sso.util.JWTUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 功能简述
 * (用户登录相关逻辑)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse register(User user, HttpServletResponse response) {
        User existUser = queryUserByUsernameAndPassword(user.getUsername(), null);
        if (existUser != null) {
            return new JsonResponse(CommonConst.FAILURE, "该用户已存在");
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        try {
            userMapper.insertOne(user);
            String jwtToken = JWTUtil.generateCookieToken(user);
            generateCookie("jwtToken", jwtToken, 25 * 60, response);
            return new JsonResponse(CommonConst.OK, "注册成功");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new JsonResponse(CommonConst.FAILURE, "注册时出现错误,请稍后再试");
    }

    @Override
    public JsonResponse login(User user, HttpServletResponse response) {
        String username = user.getUsername();
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        User existUser = queryUserByUsernameAndPassword(username, password);
        if (existUser != null && username.equals(existUser.getUsername())) {
            String jwtToken = JWTUtil.generateCookieToken(user);
            generateCookie("jwtToken", jwtToken, 25 * 60, response);
            return new JsonResponse(CommonConst.OK, "登录成功");
        }
        return new JsonResponse(CommonConst.FAILURE, "登录失败");
    }

    @Override
    public JsonResponse logout(HttpServletResponse response) {
        generateCookie("jwtToken", "", 0, response);
        return new JsonResponse(CommonConst.OK, "登出成功");
    }

    @Override
    public JsonResponse query(String username) {
        User user = queryUserByUsername(username);
        if (user == null) {
            return new JsonResponse(CommonConst.OK, "未查询到该用户信息");
        }
        return new JsonResponse(CommonConst.OK, "查询成功");
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    private User queryUserByUsername(String username) {
        return queryUserByUsernameAndPassword(username, null);
    }

    /**
     * 根据用户名和密码查询用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    private User queryUserByUsernameAndPassword(String username, String password) {
        return userMapper.selectOneByUserNameAndPassword(username, password);
    }

    /**
     * 生成cookie,添加到response
     *
     * @param value    cookie值
     * @param expTime  过期时间
     * @param response response
     */
    private void generateCookie(String key, String value, int expTime, HttpServletResponse response) {
        Cookie jwtTokenCookie = new Cookie(key, value);
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setMaxAge(expTime);
        response.addCookie(jwtTokenCookie);
    }
}
