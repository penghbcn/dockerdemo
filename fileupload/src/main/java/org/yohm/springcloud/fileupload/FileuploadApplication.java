package org.yohm.springcloud.fileupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author 海冰
 */
@SpringBootApplication
@EnableAsync
//@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@MapperScan("org.yohm.springcloud.fileupload.mapper")
public class FileuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileuploadApplication.class, args);
    }

}
