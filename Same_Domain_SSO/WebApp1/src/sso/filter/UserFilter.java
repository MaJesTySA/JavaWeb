package sso.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "UserFilter",urlPatterns = "/*",initParams = {@WebInitParam(name="server",value="http://localhost:8080"),
        @WebInitParam(name="app",value="http://localhost:8081")})
public class UserFilter implements Filter {
    private String server;
    private String app;

    public void init(FilterConfig config) throws ServletException {
        server=config.getInitParameter("server");
        app=config.getInitParameter("app");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(Objects.equals("/ssoLogout",((HttpServletRequest)req).getServletPath())){
            ((HttpServletResponse)resp).sendRedirect(server+"/ssoLogout?source="+app);
            return;
        }
        String ticket=null;
        //如果Cookies不为空
        if(null!=((HttpServletRequest)req).getCookies()){
            for(Cookie cookie:((HttpServletRequest)req).getCookies()){
                //看是否有我们需要的Cookie
                if(Objects.equals(cookie.getName(),"Ticket_Granting_Ticket")){
                    ticket=cookie.getValue();
                    break;
                }
            }
        }

        //如果Ticket不为空，就表示已经登录过了，进入到主页
        if(!Objects.equals(null,ticket)){
            //判断超时时间
            String[] values=ticket.split(":");
            ticket=req.getParameter("ticket");
            if(Long.valueOf(values[1])<System.currentTimeMillis()){
                //超时
                //等于空，非单点登录的请求
                if(Objects.equals(null,ticket)){
                    //超时了就需要重新登录
                    ((HttpServletResponse)resp).sendRedirect(server+"/ssoLogin?source="+app);
                    return;
                }
                else{
                    //不为空，单点登录的请求
                    ticket=ticket+":"+(System.currentTimeMillis()+10000);
                    ((HttpServletResponse)resp).addCookie(new Cookie("Ticket_Granting_Ticket",ticket));
                    chain.doFilter(req,resp);
                    return;
                }
            }
            chain.doFilter(req, resp);
            return;
        }
        //如果ticket等于空
        ticket=req.getParameter("ticket");
        if(!Objects.equals(null,ticket)&&!Objects.equals("",ticket.trim())){
            //添加超时时间，+10秒。这里10000要加在括号里，不然就是个字符串
            ticket=ticket+":"+(System.currentTimeMillis()+10000);
            ((HttpServletResponse)resp).addCookie(new Cookie("Ticket_Granting_Ticket",ticket));
            chain.doFilter(req,resp);
        }else{
            ((HttpServletResponse)resp).sendRedirect(server+"/ssoLogin?source="+app);
        }
    }
}
