package code;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("UTF-8");
        PrintWriter out = response.getWriter();
        String code = request.getParameter("code");
        //获取验证码框架产生的验证码（会话中存储的验证码）
        String sessionCode = (String)request.getSession().getAttribute("kcode");
        if(code!=null&sessionCode!=null) {
            //如果用户输入的验证码和产生在服务器端的验证码一致，那么就告诉用户输入正确
            if (code.equalsIgnoreCase(sessionCode)) {
                //登录逻辑、注册逻辑等相关的业务操作
                out.print("success");
            } else {
                out.print("fail");
            }
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request,response);
    }
}
