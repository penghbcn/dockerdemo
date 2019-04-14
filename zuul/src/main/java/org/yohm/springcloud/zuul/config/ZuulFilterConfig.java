package org.yohm.springcloud.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yohm.springcloud.zuul.filter.TestFilter;

/**
 * 功能简述
 * (Zuul过滤器配置)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
@Configuration
public class ZuulFilterConfig {

    @Bean
    public TestFilter testFilter(){
        return new TestFilter().setPreOrder(0);
    }
}
