package global;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns ="/*",
        initParams = {@WebInitParam(name = "encoding",value = "UTF-8")})
public class EncodingFilter implements Filter {
    //默认为utf8
    private String encoding="UTF-8";
    public void init(FilterConfig config) throws ServletException {
        if(config.getInitParameter("encoding")!=null){
            encoding=config.getInitParameter("encoding");
        }

    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest hreq=(HttpServletRequest)req;
        HttpServletResponse hresp=(HttpServletResponse)resp;
        hreq.setCharacterEncoding(encoding);
        hresp.setCharacterEncoding(encoding);
        chain.doFilter(hreq, hresp);
    }
}
