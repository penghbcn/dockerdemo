package org.yohm.springcloud.fileupload.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yohm.springcloud.fileupload.component.ImportQueueService;
import org.yohm.springcloud.fileupload.mapper.TestMapper;
import org.yohm.springcloud.fileupload.util.SpringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能简述
 * (aopTest)
 *
 * @author 海冰
 * @date 2019-07-19
 * @since 1.0.0
 */
@Component
public class AopTest {

    private static final Integer CONCURRENT_NUMBER = 3;

    @Autowired
    private ImportQueueService queueService;

    @Autowired
    private TestMapper testMapper;

    @Async
    public void toAsync1(String queueId) throws InterruptedException {
        System.out.println("刚进入异步方法1");
        Thread.sleep(3000L);
        Long st = System.currentTimeMillis();
        LinkedBlockingDeque queue = queueService.getQueueById(queueId);
        System.out.println("异步方法1队列大小: " + queue.size());
        CountDownLatch latch = new CountDownLatch(CONCURRENT_NUMBER);
        List<Integer> result = Collections.synchronizedList(new ArrayList<>());
        System.out.println("准备进入异步方法2");
        for (int i = 0; i < CONCURRENT_NUMBER; i++) {
            SpringUtils.getBean(AopTest.class).toAsync2(queue,latch,result);
        }
        latch.await();
        SpringUtils.getBean(AopTest.class).toTransaction(result);
        System.out.println("异步方法1执行完毕");
        System.out.println("耗时: "+(System.currentTimeMillis()-st));
    }

    @Async
    public void toAsync2(LinkedBlockingDeque queue, CountDownLatch latch, List<Integer> result){
        System.out.println(Thread.currentThread().getName()+"刚进入异步方法2");
        System.out.println("异步方法2队列大小: " + queue.size());
        try {
            if(queue==null){
                System.out.println("queue=null");
            }
            while (queue!=null&&queue.size()>0){
                Integer num = (Integer) queue.removeFirst();
                Thread.sleep(30L);
                result.add(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
            System.out.println("异步方法2执行完毕");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void toTransaction(List<Integer> nums){
        System.out.println(Thread.currentThread().getName()+"刚进入事务方法");
        int i = testMapper.batchInsertNum(nums);
        System.out.println(i);
        System.out.println("事务方法执行完毕");
    }
}
