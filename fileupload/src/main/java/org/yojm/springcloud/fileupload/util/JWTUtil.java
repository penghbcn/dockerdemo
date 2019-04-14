package org.yojm.springcloud.fileupload.util;

import com.auth0.jwt.JWT;

/**
 * 功能简述
 * (JWT工具类)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
public class JWTUtil {

    private static String KEY = "demo_sso";

    /**
     * 校验jwtToken
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        JWT.decode(token);
        return false;
    }
}
