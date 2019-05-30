package Servlets;

import org.apache.poi.ss.usermodel.Workbook;
import services.ExcelService;
import services.ExcelServiceNoSave;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "ExportExcelServlet",urlPatterns = "/exportExcel")
public class ExportExcelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExcelServiceNoSave service=new ExcelServiceNoSave();
        Workbook workbook=service.export(true);
        response.setHeader("Content-Disposition","attachment;filename=export.xlsx");
        ServletOutputStream outputStream=response.getOutputStream();
        //使用文件下载到指定目录
//        FileOutputStream fileOutputStream=new FileOutputStream("d:/upload/export.xlsx");
//        workbook.write(fileOutputStream);
//        FileInputStream fileInputStream=new FileInputStream("d:/upload/export.xlsx");
//        byte[] bytes=new byte[fileInputStream.available()];
//        fileInputStream.read(bytes);
//        outputStream.write(bytes);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        workbook.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
