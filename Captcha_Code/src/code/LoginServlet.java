package code;

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
        String flag=request.getParameter("flag");
        //获取用户传递过来的验证码
        String code = request.getParameter("code");
        //获取自己生成的验证码
        String gcode=(String)request.getSession().getAttribute("gcode");
        //获取自己生成的算术验证码
        String gcalc_code=(String)request.getSession().getAttribute("gcalc_code");
        //获取验证码框架产生的验证码（会话中存储的验证码）
        String kcode = (String)request.getSession().getAttribute("kcode");
        
        if(flag.equals("0")){
            if(code!=null&gcode!=null) {
                //如果用户输入的验证码和产生在服务器端的验证码一致，那么就告诉用户输入正确
                if (code.equalsIgnoreCase(gcode)) {
                    //登录逻辑、注册逻辑等相关的业务操作
                    out.print("success");
                } else {
                    out.print("fail");
                }
            }
        }
        if(flag.equals("1")){
            if(code!=null&gcalc_code!=null) {
                //如果用户输入的验证码和产生在服务器端的验证码一致，那么就告诉用户输入正确
                if (code.equalsIgnoreCase(gcalc_code)) {
                    //登录逻辑、注册逻辑等相关的业务操作
                    out.print("success");
                } else {
                    out.print("fail");
                }
            }
        }
        if(flag.equals("2")){
            if(code!=null&kcode!=null) {
                //如果用户输入的验证码和产生在服务器端的验证码一致，那么就告诉用户输入正确
                if (code.equalsIgnoreCase(kcode)) {
                    //登录逻辑、注册逻辑等相关的业务操作
                    out.print("success");
                } else {
                    out.print("fail");
                }
            }
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request,response);
    }
}
