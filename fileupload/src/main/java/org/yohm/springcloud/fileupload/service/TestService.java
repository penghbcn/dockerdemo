package org.yohm.springcloud.fileupload.service;

import org.yohm.springcloud.fileupload.model.JsonResponse;

/**
 * 功能简述
 * (测试用)
 *
 * @author 海冰
 * @date 2019-06-17
 * @since 1.0.0
 */
public interface TestService {

    JsonResponse insertOne(String data);

    JsonResponse selectOne();
}
