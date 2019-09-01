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
import java.util.Objects;

@WebServlet(name = "MessageServlet",urlPatterns = {"/addMessagePrompt.do","/addMessage.do"})
public class MessageServlet extends HttpServlet {
    private MessageService messageService;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathName=req.getServletPath();
        if(Objects.equals("/addMessagePrompt.do",pathName)){
            req.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(req,resp);
        }else if(Objects.equals("/addMessage.do",pathName)){
            User user=(User)req.getSession().getAttribute("user");
            if(user==null){
                req.getRequestDispatcher("/message/list.do").forward(req,resp);
            }else{
                String title=req.getParameter("title");
                String content=req.getParameter("content");
                boolean result=messageService.addMessage(new Message(user.getId(),user.getName(),title,content));
                if(result){
                    req.getRequestDispatcher("/message/list.do").forward(req,resp);
                }else{
                    req.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(req,resp);
                }
            }
        }else{
            req.getRequestDispatcher("/WEB-INF/views/biz/error/404.jsp").forward(req,resp);
        }
    }

    @Override
    public void init() throws ServletException {
        messageService=new MessageService();
    }
}
