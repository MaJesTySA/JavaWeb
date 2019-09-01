package servlet;

import bean.User;
import dao.UserDAO;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/regist.do")
public class RegistServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (userService.checkUsername(username)){
            req.setAttribute("msg","用户名已经存在！");
            req.getRequestDispatcher("regPrompt.do").forward(req,resp);
            return;
        }
        String realName = req.getParameter("realname");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        boolean res;
        if (username != null && password != null){
            res=userService.saveUser(username,password,realName,address,phone);
        } else {
            req.setAttribute("msg","用户名和密码不能为空！");
            req.getRequestDispatcher("regPrompt.do").forward(req,resp);
            return;
        }
        if (res){
            req.setAttribute("msg", "注册成功！");
            req.getRequestDispatcher("login.do").forward(req,resp);
        } else{
            req.setAttribute("msg","注册失败！");
            req.getRequestDispatcher("regPrompt.do").forward(req,resp);
        }

    }
}
