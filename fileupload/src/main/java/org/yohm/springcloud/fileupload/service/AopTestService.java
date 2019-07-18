package org.yohm.springcloud.fileupload.service;

import org.yohm.springcloud.fileupload.model.JsonResponse;

/**
 * 功能简述
 * (测试动态代理)
 *
 * @author 海冰
 * @date 2019-07-19
 * @since 1.0.0
 */
public interface AopTestService {

    JsonResponse testAop(String queueId);
}
