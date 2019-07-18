package org.yohm.springcloud.fileupload.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yohm.springcloud.fileupload.component.ImportQueueService;
import org.yohm.springcloud.fileupload.mapper.TestMapper;
import org.yohm.springcloud.fileupload.model.JsonResponse;
import org.yohm.springcloud.fileupload.service.AopTestService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能简述
 * (测试动态代理)
 *
 * @author 海冰
 * @date 2019-07-19
 * @since 1.0.0
 */
@Service
public class AopTestServiceImpl implements AopTestService {

    @Autowired
    private AopTest aopTest;

    @Override
    public JsonResponse testAop(String queueId) {
        System.out.println("刚进入业务方法");
        try {
            Thread.sleep(3000L);
            System.out.println("准备进入异步方法1");
            aopTest.toAsync1(queueId);
            System.out.println("准备退出逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
