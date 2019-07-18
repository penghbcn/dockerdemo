package org.yohm.springcloud.fileupload.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能简述
 * (excel解析相关工具类)
 *
 * @author 海冰
 * @date 2019-06-16
 * @since 1.0.0
 */
public class ExcelUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);
//    private static Deque<Object> deque = new LinkedBlockingDeque<>(10000);

    /**
     * 根据后缀名获取对应的Workbook.获取失败则返回null
     * @param file
     * @return
     */
    public static Workbook getWorkbook(MultipartFile file){
        String fileName = file.getOriginalFilename();
        Workbook wb = null;
        try (InputStream fis = file.getInputStream()) {
            if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(),e);
        }
        return wb;
    }

    public static Deque<Object> pushToQueue(List<Object> data){
        return new LinkedBlockingDeque<>(data);
    }
}
