package Servlets;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import services.WordService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ExportWordServlet",urlPatterns = "/exportWord")
public class ExportWordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        WordService service=new WordService();
        Map<String,String> param=new HashMap<>();
        param.put("{name}",request.getParameter("name"));
        param.put("{age}",request.getParameter("age"));
        param.put("{time}",request.getParameter("time"));
        ServletOutputStream outputStream=response.getOutputStream();
        if (request.getParameter("isDocx")!=null && !"".equals(request.getParameter("isDocx"))){
            XWPFDocument docx=service.export07(param);
            System.out.println(param);
            response.setHeader("Content-Disposition","attachment;filename=export.docx");
            docx.write(outputStream);
            docx.close();
        }else{
            HWPFDocument doc=service.export03(param);
            response.setHeader("Content-Disposition","attachment;filename=export.doc");
            doc.write(outputStream);
            doc.close();
        }
        outputStream.flush();
        outputStream.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
