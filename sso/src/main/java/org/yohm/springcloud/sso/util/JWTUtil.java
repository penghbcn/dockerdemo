package org.yohm.springcloud.sso.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yohm.springcloud.sso.model.User;
import sun.misc.BASE64Encoder;

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
    public void setKey(String key){
        JWTUtil.key = key;
    }

    /**
     * 生成jwtToken,过期时间30分钟
     * @param user
     * @return token
     */
    public static String generateToken(User user){
        Date expDate = new Date(System.currentTimeMillis()+30*60*1000);
        return generateToken(user,expDate);
    }

    /**
     * 生成指定过期时间的jwtToken
     * @param user
     * @param expDate 过期时间
     * @return
     */
    public static String generateToken(User user,Date expDate){
        String secretKey = new BASE64Encoder().encode(key.getBytes());
        return JWT.create().withExpiresAt(expDate)
                .withClaim("username",user.getUsername())
                .withArrayClaim("role",user.getRoles())
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 校验jwtToken是否有效
     * @param token
     * @return boolean
     */
    public static boolean isValidToken(String token){
        try {
            String secretKey = new BASE64Encoder().encode(key.getBytes());
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWT.require(algorithm).build().verify(token);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return false;
        }
        return true;
    }
}
