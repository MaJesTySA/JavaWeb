package Servlets;

import dto.ImportWordParamDto;
import dto.ImportWordResultDto;
import dto.ParamDto;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import services.WordService;
import utils.RequestUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ImportWordServlet",urlPatterns = "/importWord")
public class ImportWordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(ServletFileUpload.isMultipartContent(request)){
            ParamDto dto= RequestUtil.parseParam(request);
            ImportWordParamDto paramDto=new ImportWordParamDto();
            paramDto.setTitle(dto.getParamMap().get("title"));
            paramDto.setWord(dto.getFilemap().get("word"));

            WordService service=new WordService();
            ImportWordResultDto resultDto=service.imp(paramDto);
            request.setAttribute("result",resultDto);
        }else{

        }
        request.getRequestDispatcher("/WEB-INF/jsp/importWordResult.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
