package global;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "GlobalControllerServlet",urlPatterns = "*.do")
public class GlobalControllerServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        /*
        以.do为后缀的Url交给这个Servlet
        /login.do          DefaultController  login
        /Cake/detail.do    CakeController     detail
        /admin/Cake/add.do CakeController     add
        */
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String path=request.getServletPath();
        //来自后台的请求，有admin
        if(path.indexOf("/admin")!=-1){
            path=path.substring(7);
        }else{
            //来自前台的请求，无admin
            path=path.substring(1);
        }
        /*
        login.do
        Cake/detail.do
        Cake/add.do
         */
        //判断有无模块
        int index=path.indexOf("/");
        String className=null;
        String methodName=null;
        //有模块
        if(index!=-1){
            className="controller."+path.substring(0,index)+"Controller";
            methodName=path.substring(index+1,path.indexOf(".do"));
        }else{
            className="controller.DefaultController";
            methodName=path.substring(0,path.indexOf(".do"));
        }
        try{
            Class cls=Class.forName(className);
            Object object=cls.newInstance();
            Method method=cls.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(object,request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
