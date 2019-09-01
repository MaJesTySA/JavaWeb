package servlet;

import bean.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@WebServlet(name = "UserServlet", urlPatterns = {"/userInfo.do", "/editUserPrompt.do", "/editUser.do"})
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathName = req.getServletPath();
        if (Objects.equals("/userInfo.do", pathName)) {
            req.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(req, resp);
        } else if (Objects.equals("/editUserPrompt.do", pathName)) {
            //两种方法：
            //1.从Session中获取，与上面一样
            //2.可扩展的方法：根据页面ID从数据库查询
            Long id = Long.valueOf(req.getParameter("id"));
            User user = userService.getUserById(id);
            if (user != null) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/views/biz/edit_user.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(req, resp);
            }
        } else if (Objects.equals("/editUser.do", pathName)) {
            Long id = Long.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            String realName = req.getParameter("realName");
            String birthday = req.getParameter("birthday");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(password);
            user.setRealName(realName);
            try {
                user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
            } catch (ParseException e) {
                System.out.println("格式化生日失败");
                e.printStackTrace();
            }
            user.setPhone(phone);
            user.setAddress(address);
            boolean result = userService.updateUser(user);
            if (result) {
                req.getSession().setAttribute("user", user);
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/views/biz/user.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
        }
    }
}
