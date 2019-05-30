package services;

import dto.ImportExcelParamDto;
import dto.ImportExcelResultDto;
import entity.Student;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {
    public ImportExcelResultDto imp(ImportExcelParamDto dto){
        ImportExcelResultDto result=new ImportExcelResultDto();
        //设定最终返回响应的标题
        result.setTitle(dto.getTitle());
        //创建Student数据容器
        List<Student> studentList=new ArrayList<>();
        //设定最终返回响应的StudentList
        result.setStudentList(studentList);
        //保存文件到本地
        String fileName=null;
        try {
            fileName=FileUtil.save(dto.getExcel(),FileUtil.SAVE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("保存失败！");
        }
        //解析本地文件
        if(fileName!=null){
            Workbook workbook=null;
            try {
                workbook=WorkbookFactory.create(new File(FileUtil.SAVE_PATH+fileName));
                Sheet sheet=workbook.getSheetAt(0);
                //另一种方法
                //workbook.getSheet("Sheet1");
                int rowNum=sheet.getLastRowNum();
                for (int i = 1; i <=rowNum ; i++) {
                    Row row=sheet.getRow(i);
                    Student student=new Student();
                    studentList.add(student);
                    student.setName(row.getCell(0).getStringCellValue());
                    student.setAge((int)row.getCell(1).getNumericCellValue());
                    student.setTime(row.getCell(2).getDateCellValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setMsg("解析失败！");
            }finally {
                if(workbook!=null){
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    return result;
    }
}
