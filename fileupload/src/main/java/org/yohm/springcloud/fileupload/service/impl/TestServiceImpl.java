package org.yohm.springcloud.fileupload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yohm.springcloud.fileupload.mapper.TestMapper;
import org.yohm.springcloud.fileupload.model.JsonResponse;
import org.yohm.springcloud.fileupload.model.TestModel;
import org.yohm.springcloud.fileupload.service.TestService;

/**
 * 功能简述
 * (测试用)
 *
 * @author 海冰
 * @date 2019-06-17
 * @since 1.0.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse insertOne(String data) {
        System.out.println("pre: " + testMapper.selectLastOne());
        testMapper.insertOne(data);
        System.out.println("post: " + testMapper.selectLastOne());
        if("abc".equals(data)){
            throw new RuntimeException();
        }
        TestModel model = testMapper.selectLastOne();
        return new JsonResponse(200,"",model);
    }

    @Override
    public JsonResponse selectOne() {
        return new JsonResponse(200, "", testMapper.selectLastOne());
    }
}
