package org.yohm.springcloud.sso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author 海冰
 */
@SpringBootApplication
@MapperScan("org.yohm.springcloud.sso.mapper")

public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}
