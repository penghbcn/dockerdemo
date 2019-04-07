package org.yohm.springcloud.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能简述
 * (connect test)
 *
 * @author 海冰
 * @create 2019-03-16
 * @since 1.0.0
 */
@RestController
public class TestController {

    @RequestMapping("/")
    public String call(){
        return "Hi! This is SsoService.";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file){
        return file.toString();
    }
}
