package servlet;

import bean.Message;
import service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MessageListServlet", urlPatterns = "/message/list.do")
public class MessageListServlet extends HttpServlet {
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
        int page = 1;
        int pageSize = 5;
        if (pageStr != null && (!"".equals(pageStr))) {
            page = Integer.parseInt(pageStr);
        }
        List<Message> messages = messageService.getMessages(page, pageSize);
        int count = messageService.countMessages();
        int last = count % pageSize == 0 ? (count / pageSize) : ((count / pageSize) + 1);
        req.setAttribute("messages", messages);
        req.setAttribute("page", page);
        req.setAttribute("last", last);
        req.getRequestDispatcher("/WEB-INF/views/biz/message_list.jsp").forward(req, resp);
    }
}
