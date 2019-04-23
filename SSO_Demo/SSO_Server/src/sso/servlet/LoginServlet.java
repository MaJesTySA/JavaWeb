package sso.servlet;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet",urlPatterns = {"/login","/ssoLogin","/ssoLogout"},
        initParams = {@WebInitParam(name="domains",value = "http://localhost:8081,http://localhost:8082"),})
public class LoginServlet extends javax.servlet.http.HttpServlet {
    private String domains;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        domains=config.getInitParameter("domains");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Objects.equals("/login",req.getServletPath())){
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            //登录来源
            String source=req.getParameter("source");
            //如果当前请求是Login，就没有source。
            if(null==source||Objects.equals("",source)){
                String referer=req.getHeader("referer");
                source=referer.substring(referer.indexOf("source=")+7);
            }
            //登录校验
            if(Objects.equals(username,password)){
                //成功后产生用户标识
                String ticket= UUID.randomUUID().toString().replace("-","");
                System.out.println("***************"+ticket);
                //携带用户标识，重定向到来源app的main页面。同时还携带domains，指的是另一个webApp的地址，
                //也就是说这个重定向的请求，最后要发送到domains，另一个App，通知它也要登录。
                resp.sendRedirect(source+"/main?ticket="+ticket+"&domains="+
                        domains.replace(source + ",", "").replace("," + source, "").replace(source, ""));
            }else{
                req.setAttribute("source",source);
                //校验失败，返回Login.jsp重新登录
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
            }
        }
        //跳转的登录请求
        else if(Objects.equals("/ssoLogin",req.getServletPath())){
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
        }
        else if(Objects.equals("/ssoLogout",req.getServletPath())){
            String source=req.getParameter("source");
            if(source==null||Objects.equals("",source)){
                String referer=req.getHeader("referer");
                source=referer.substring(referer.indexOf("source=")+7);
            }
            resp.sendRedirect(source+"/logout?domains="+domains.replace(source+",","").
                    replace(","+source,"").
                    replace(source,""));
        }
    }
}
