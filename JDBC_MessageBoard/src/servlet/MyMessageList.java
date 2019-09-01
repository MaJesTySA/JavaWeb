package servlet;

import bean.Message;
import bean.User;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/my/message/list.do")
public class MyMessageList extends HttpServlet {
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        messageService = new MessageService();
    }

    @Override
    public void destroy() {
        messageService = null;
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        User user=(User)req.getSession().getAttribute("user");
        int page = 1;
        int pageSize = 5;
        if (pageStr != null && (!"".equals(pageStr))) {
            page = Integer.parseInt(pageStr);
        }
        List<Message> messages = messageService.getMyMessages(page, pageSize,user.getName());
        int count = messageService.countMyMessages(user.getName());
        int last = count % pageSize == 0 ? (count / pageSize) : ((count / pageSize) + 1);
        req.setAttribute("messages", messages);
        req.setAttribute("page", page);
        req.setAttribute("last", last);
        req.getRequestDispatcher("/WEB-INF/views/biz/my_message_list.jsp").forward(req, resp);
    }
}
