package Servlets;

import dto.ImportExcelParamDto;
import dto.ImportExcelResultDto;
import dto.ParamDto;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import services.ExcelServiceNoSave;
import utils.RequestUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImportExcelServlet",urlPatterns = "/importExcel")
public class ImportExcelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断表单的上传类型是不是文件类型
        if(ServletFileUpload.isMultipartContent(request)){

            ParamDto dto=RequestUtil.parseParam(request);
            ImportExcelParamDto paramDto=new ImportExcelParamDto();

            paramDto.setTitle(dto.getParamMap().get("title"));
            paramDto.setExcel(dto.getFilemap().get("excel"));
            //保存到本地的方法
            //ExcelService service=new ExcelService();
            ExcelServiceNoSave service=new ExcelServiceNoSave();
            ImportExcelResultDto resultDto=service.imp(paramDto);
            request.setAttribute("result",resultDto);
        }else{

        }
        request.getRequestDispatcher("/WEB-INF/jsp/importExcelResult.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
