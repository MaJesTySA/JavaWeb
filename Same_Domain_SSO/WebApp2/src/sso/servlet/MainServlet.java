package sso.servlet;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(name = "MainServlet",urlPatterns = {"/main","/logout","/ssoLogout","/removeCookie","/setCookie"})
public class MainServlet extends HttpServlet {
    private ExecutorService service= Executors.newFixedThreadPool(10);
    private String servers;

    private void syncCookie(String server,String ticket,String method){
        service.submit(new Runnable() {
            @Override
            public void run() {
                //进行远程调用，一个新的post请求，响另一个webapp的setCookie页面，发送一个带ticket参数的请求。
                HttpPost httpPost = new HttpPost(server+"/"+method+"?ticket="+ticket);
                CloseableHttpClient httpClient=null;
                CloseableHttpResponse response=null;
                //创建一个HttpClients，将这个请求发出去。
                httpClient = HttpClients.createDefault();
                try {
                    response=httpClient.execute(httpPost);
                    //得到响应，即ok
                    HttpEntity entity=response.getEntity();
                    String responseContent= EntityUtils.toString(entity,"UTF-8");
                    System.out.println("================"+method+responseContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(null!=response){
                        try {
                            response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(null!=httpClient){
                        try {
                            httpClient.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果是/main，说明是从服务器端登录后转发过来的地址，从中取出domains的值，也就是另一个web
        //的地址，然后为该应用调用setCookie方法
        if(Objects.equals("/main",req.getServletPath())){
            String domains=req.getParameter("domains");
            if(null!=domains){
                this.servers=domains;
            }
            String ticket=req.getParameter("ticket");
            if(null!=domains&&ticket!=null){
                for(String server:domains.split(",")){
                    if(!Objects.equals(null,server)&&!Objects.equals("",server.trim())){
                        syncCookie(server,ticket,"setCookie");
                    }
                }
            }
            req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req,resp);
        }
        else if (Objects.equals("/logout",req.getServletPath())){
            Cookie cookie=new Cookie("Ticket_Granting_Ticket",null);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            if(null!=servers){
                for(String server:servers.split(",")){
                    if(!Objects.equals(null,server)&&!Objects.equals("",server.trim())){
                        syncCookie(server,"","removeCookie");
                    }
                }
            }
            req.getRequestDispatcher("/WEB-INF/views/logout.jsp").forward(req,resp);
        }
        //处理setCookie，从另一个App传过来的setCookie。上面那个是设置别人的，这个是设置自己的
        //也就是说，当一web登录成功后调用setCookie方法，会发送一个新的请求到另一个webapp，另一个webapp就设置cookie。
        else if (Objects.equals("/setCookie",req.getServletPath())){
            String ticket=req.getParameter("ticket");
            resp.addCookie(new Cookie("Ticket_Granting_Ticket",ticket));
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/text;charset=utf-8");
            resp.getWriter().write("ok");
        }
        else if(Objects.equals("/removeCookie",req.getServletPath())){
            Cookie cookie=new Cookie("Ticket_Granting_Ticke",null);
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/text;charset=utf-8");
            resp.getWriter().write("ok");
        }
    }
}
