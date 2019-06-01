package servlet;

import dao.UsersDAO;
import entity.Users;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersDeleteServlet",urlPatterns = "/deluser")
public class UsersDeleteServlet extends HttpServlet {
    private UsersDAO usersDAO=new UsersDAO();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        String type=req.getParameter("type");
        if(type.equals("lock")){
            Users user=new Users();
            user.setId(Integer.parseInt(id));
            user.setUserStatus(1);
            usersDAO.updateUser(user);
        }
        else if(type.equals("del")){
            usersDAO.delUser(Integer.parseInt(id));
        }
        else if(type.equals("unlock")){
            Users user=new Users();
            user.setId(Integer.parseInt(id));
            user.setUserStatus(0);
            usersDAO.updateUser(user);
        }
        resp.sendRedirect("/index");
    }
}
