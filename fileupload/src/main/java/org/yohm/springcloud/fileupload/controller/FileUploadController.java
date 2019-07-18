package org.yohm.springcloud.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yohm.springcloud.fileupload.component.ImportQueueService;
import org.yohm.springcloud.fileupload.service.AopTestService;
import org.yohm.springcloud.fileupload.service.FinalExamImportService;
import org.yohm.springcloud.fileupload.model.JsonResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述
 * (文件上传)
 *
 * @author 海冰
 * @create 2019-04-06
 * @since 1.0.0
 */
@RestController
@RequestMapping("/fileupload")
public class FileUploadController {

    @Autowired
    private FinalExamImportService finalExamImportService;

    @Autowired
    private ImportQueueService queueService;

    @Autowired
    private AopTestService aopTestService;

    @RequestMapping(value = "/finalexam",method = RequestMethod.POST)
    public String finalexam(@RequestParam("file")MultipartFile file){
        JsonResponse response = finalExamImportService.finalExamExcelImport(file);
        return response.toString();
    }

    @GetMapping("/test")
    public JsonResponse testAop(){
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<100;i++){
            list.add(i);
        }
        String queueId = queueService.pushToQueue(list);

        return aopTestService.testAop(queueId);
    }
}
