package org.yohm.springcloud.fileupload.service;

import org.springframework.web.multipart.MultipartFile;
import org.yohm.springcloud.fileupload.model.JsonResponse;

/**
 * 功能简述
 * (期末考试excel导入)
 *
 * @author 海冰
 * @date 2019-06-16
 * @since 1.0.0
 */
public interface FinalExamImportService {

    JsonResponse finalExamExcelImport(MultipartFile file);
}
