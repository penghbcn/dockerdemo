package org.yohm.springcloud.zuul.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yohm.springcloud.zuul.model.User;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 功能简述
 * (JWT工具类)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
@Component
public class JWTUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);
    private static String key;

    @Value("${jwt.key}")
    public void setKey(String key) {
        JWTUtil.key = key;
    }

    /**
     * 生成指定过期时间的jwtToken
     *
     * @param user
     * @param expDate 过期时间
     * @return
     */
    public static String generateToken(User user, Date expDate) {
        String secretKey = new BASE64Encoder().encode(key.getBytes());
        return "Bearer " + JWT.create().withExpiresAt(expDate)
                .withClaim("username", user.getUsername())
                .withArrayClaim("role", user.getRoles())
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 校验jwtToken是否有效
     *
     * @param token
     * @return boolean
     */
    public static boolean isValidToken(String token) {
        try {
            token = cookieToken2jwtToken(token);
            verifyToken(token);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static String cookieToken2jwtToken(String cookieToken)  {
        try {
            return URLDecoder.decode(cookieToken, "UTF-8").substring(7);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static void verifyToken(String token) throws IllegalArgumentException, JWTVerificationException {
        String secretKey = new BASE64Encoder().encode(key.getBytes());
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWT.require(algorithm).build().verify(token);
    }

    public static String generateCookieToken(User user) {
        Date expDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        return generateCookieToken(user, expDate);
    }

    public static String generateCookieToken(User user, Date expDate) {
        String jwtToken = generateToken(user, expDate);
        try {
            jwtToken = URLEncoder.encode(jwtToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
        return jwtToken;
    }
}
