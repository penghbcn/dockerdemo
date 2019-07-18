package org.yohm.springcloud.fileupload;

import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yohm.springcloud.fileupload.model.TestModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述
 * (测试)
 *
 * @author 海冰
 * @create 2019-04-06
 * @since 1.0.0
 */
public class DemoTest {
    public static void main(String[] args) {
        File file = new File("D:\\test.xlsx");
        List<TestModel> data = readExcel(file);
        System.out.println(data);
    }

    private static List<TestModel> readExcel(File file) {
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
            TestModel student = new TestModel();
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

//            student.setGrade(head[0]);
//            student.setName(head[1]);
//            student.setSubject(subject);
//            student.setScore(score);

            students.add(student);
        }
        return students;
    }

    private static Workbook getWorkBook(File file) {
        String extName = FileUtils.getFileExtension(file);
        Workbook wb = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            if ("xls".equals(extName)) {
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(extName)) {
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
