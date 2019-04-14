package org.yojm.springcloud.fileupload.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.extractor.ExcelExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yojm.springcloud.fileupload.model.TestModel;

import java.io.InputStream;
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

    @RequestMapping(value = "/finalexam",method = RequestMethod.POST)
    public String finalexam(@RequestParam("file")MultipartFile file){
        List<TestModel> data = readExcel(file);
        return data.toString();
    }

    private static List<TestModel> readExcel(MultipartFile file) {
        Workbook wb = getWorkBook(file);
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
            student.setGrade(head[0]);
            student.setName(head[1]);
            student.setSubject(subject);
            student.setScore(score);

            students.add(student);
        }
        return students;
    }

    private static Workbook getWorkBook(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Workbook wb = null;
        try (InputStream fis = file.getInputStream()) {
            if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
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
}
