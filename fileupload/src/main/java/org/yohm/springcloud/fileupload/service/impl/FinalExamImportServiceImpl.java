package org.yohm.springcloud.fileupload.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yohm.springcloud.fileupload.constant.CommonConst;
import org.yohm.springcloud.fileupload.util.ExcelUtils;
import org.yohm.springcloud.fileupload.model.JsonResponse;
import org.yohm.springcloud.fileupload.model.TestModel;
import org.yohm.springcloud.fileupload.service.FinalExamImportService;
import org.yohm.springcloud.fileupload.util.RedisService;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 功能简述
 * (解析期末考试excel)
 *
 * @author 海冰
 * @date 2019-06-16
 * @since 1.0.0
 */
@Service
public class FinalExamImportServiceImpl implements FinalExamImportService {

    @Autowired
    private RedisService redis;
    private static final String CANCEL_COMMAND = "cancel_right_now";

    @Override
    public JsonResponse finalExamExcelImport(MultipartFile file) {
        Workbook wb = ExcelUtils.getWorkbook(file);
        if (wb == null) {
            return new JsonResponse(CommonConst.FAILURE, "解析失败,仅支持excel文件");
        }
        List<TestModel> data = analysisFinalExamSheet1(wb);
        Deque<Object> dataDeque = new LinkedBlockingDeque<>(data);

        System.out.println(data);
        return new JsonResponse(CommonConst.SUCCESS,"解析成功",data);
    }

    private List<TestModel> analysisFinalExamSheet1(Workbook wb) {
        Sheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        if (rowNum <= 1) {
            return null;
        }
        String[] head = new String[]{"班级", "姓名", "科目", "分数"};
        List<TestModel> students = new ArrayList<>();
        for (int i = 1; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                rowNum++;
                continue;
            }
            Cell gradeCell = row.getCell(0);
            if (gradeCell != null) {
                head[0] = (String) getCellValue(gradeCell);
                continue;
            }
            Cell nameCell = row.getCell(1);
            if (nameCell != null) {
                head[1] = (String) getCellValue(nameCell);
                continue;
            }

            String subject = (String) getCellValue(row.getCell(2));
            int score = (int) getCellValue(row.getCell(3));

            TestModel student = new TestModel();
//            student.setGrade(head[0]);
//            student.setName(head[1]);
//            student.setSubject(subject);
//            student.setScore(score);

            students.add(student);
        }
        return students;
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING: {
                return cell.getStringCellValue();
            }
            case Cell.CELL_TYPE_BOOLEAN: {
                return Boolean.toString(cell.getBooleanCellValue());
            }
            case Cell.CELL_TYPE_NUMERIC: {
                return (int) cell.getNumericCellValue();
            }
            case Cell.CELL_TYPE_FORMULA: {
                return cell.getCellFormula();
            }
            case Cell.CELL_TYPE_BLANK: {
                return " ";
            }
            default: {
                return " ";
            }
        }
    }

    @Async
    public void readRedis(String key) {

        try {
            while(redis.lSize(key)>0){
                String value = (String)redis.lRightPop(key);
                if(CANCEL_COMMAND.equals(value)){
                    throw new RuntimeException("已取消");
                }
                System.out.println(value);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            redis.delete(key);
        }
    }
}
