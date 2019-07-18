package org.yohm.springcloud.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yohm.springcloud.fileupload.model.JsonResponse;
import org.yohm.springcloud.fileupload.service.TestService;
import org.yohm.springcloud.fileupload.util.RedisService;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述
 * (测试)
 *
 * @author 海冰
 * @create 2019-04-14
 * @since 1.0.0
 */
@RestController
public class TestController {

    @Autowired
    private RedisService redis;
//
//    @Autowired
//    private FinalExamImportService impl;

    @Autowired
    private TestService testService;

    private static final String CANCEL_COMMAND = "cancel_right_now";

    @RequestMapping("/")
    public String test(String name) {

        String key = "key";
        String value = "value";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String redisValue = value + i;
            list.add(redisValue);
        }
        redis.lLeftPushAll(key, list);
//        impl.readRedis(key);
        return redis.lRightPop(key).toString();
    }

    @RequestMapping("/interrupt1")
    public void interrupt1() {
        String key = "key";
        redis.lLeftPush(key, CANCEL_COMMAND);
    }

    @RequestMapping("/interrupt2")
    public void interrupt2() {
        String key = "key";
        redis.lRightPush(key, CANCEL_COMMAND);
    }

    @GetMapping("/insert")
    public JsonResponse insertOne(@RequestParam("data") String data) {
        return testService.insertOne(data);
    }

    @GetMapping("/select")
    public JsonResponse selectOne(){
        return testService.selectOne();
    }

}
