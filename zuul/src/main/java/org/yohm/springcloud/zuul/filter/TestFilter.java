package org.yohm.springcloud.zuul.filter;

import com.auth0.jwt.JWT;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.FilterType;
import org.yohm.springcloud.zuul.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 功能简述
 * (测试用过滤器)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */

public class TestFilter extends ZuulFilter {

    private int order = 0;

    public TestFilter setPreOrder(int order){
        this.order = order;
        return this;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return order;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("Authorization");
        if(JWTUtil.isValidToken(token)){
            String username = JWT.decode(JWTUtil.cookieToken2jwtToken(token)).getClaim("username").asString();
            request.getParameterMap();// 关键步骤，一定要get一下,下面这行代码才能取到值
            Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
            System.out.println("requestQueryParams: "+requestQueryParams);
            if(requestQueryParams == null){
                requestQueryParams = new HashMap<>();
            }
            requestQueryParams.put("currentUser",Collections.singletonList(username));
            ctx.setRequestQueryParams(requestQueryParams);
            System.err.println(username);
        }
        return null;
    }
}
