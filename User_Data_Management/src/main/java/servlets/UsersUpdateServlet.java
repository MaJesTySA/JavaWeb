package servlet;

import dao.UsersDAO;
import entity.Users;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "UsersUpdateServlet",urlPatterns = "/updateusers")
public class UsersUpdateServlet extends HttpServlet {
    private UsersDAO usersDAO=new UsersDAO();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        String nickname=req.getParameter("nickname");
        String age=req.getParameter("age");
        String gender=req.getParameter("gender");
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");
        String remark=req.getParameter("remark");
        Users user=new Users(Integer.parseInt(id),nickname,Integer.parseInt(age),gender,phone,email,new Date(),remark);
        usersDAO.updateUser(user);
        resp.sendRedirect("/detail?id="+user.getId());
    }
}
