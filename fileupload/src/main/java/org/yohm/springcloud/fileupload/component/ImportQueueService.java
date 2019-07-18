package org.yohm.springcloud.fileupload.component;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能简述
 * (导入时的队列)
 *
 * @author 海冰
 * @date 2019-07-18
 * @since 1.0.0
 */
@Validated
@Component
public class ImportQueueService {
    private Object lock = new Object();

    private volatile Map<String, LinkedBlockingDeque<Object>> importDequeMap;


    private Map<String, LinkedBlockingDeque<Object>> init(){
        if(importDequeMap==null){
            synchronized(lock){
                if(importDequeMap==null) {
                    importDequeMap = new ConcurrentHashMap<>(100);
                }
            }
        }
        return importDequeMap;
    }

    public String pushToQueue(@NotEmpty List<?> data){
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>(data);
        String key = UUID.randomUUID().toString();
        init().put(key,deque);
        return key;
    }

    public LinkedBlockingDeque<Object> getQueueById(String queueId){
        return init().get(queueId);
    }

}
