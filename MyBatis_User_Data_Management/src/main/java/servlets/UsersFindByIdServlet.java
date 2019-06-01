package servlet;

import dao.UsersDAO;
import entity.Users;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersFindByIdServlet",urlPatterns = "/detail")
public class UsersFindByIdServlet extends HttpServlet {
    private UsersDAO usersDAO=new UsersDAO();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("id");
        Users user=usersDAO.findById(Integer.parseInt(id));
        req.setAttribute("user",user);
        req.getRequestDispatcher("/detail.jsp").forward(req,resp);
    }
}
