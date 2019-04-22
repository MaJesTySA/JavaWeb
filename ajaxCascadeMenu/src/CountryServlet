import com.alibaba.fastjson.JSON;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CountryServlet",urlPatterns = "/country")
public class CountryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String level=request.getParameter("level");
        String parent =request.getParameter("parent");
        List cList=new ArrayList();
        if(level.equals("1")){
            cList.add(new Country("China","cn"));
            cList.add(new Country("United States","us"));
            cList.add(new Country("United Kingdom","uk"));
        }else if(level.equals("2")){
            if(parent.equals("cn")){
                cList.add(new Country("Beijing","jing"));
                cList.add(new Country("Shanghai","hu"));
                cList.add(new Country("Sichuan","chuan"));
            }else if(parent.equals("us")){
                cList.add(new Country("NewYork","ny"));
                cList.add(new Country("Washington","dc"));
                cList.add(new Country("California","ca"));
            }else if(parent.equals("uk")){
                cList.add(new Country("Londonshire","lds"));
                cList.add(new Country("Oxfordshire","oxs"));
                cList.add(new Country("Cambridgeshire","cbs"));
            }
        }else if(level.equals("3")){
            if(parent.equals("jing")){
                cList.add(new Country("Chaoyang","cy"));
                cList.add(new Country("Haiding","hd"));
                cList.add(new Country("Cangping","cp"));
            }else if(parent.equals("hu")){
                cList.add(new Country("Pudong","pd"));
                cList.add(new Country("Baoshan","bs"));
            }else if(parent.equals("chuan")){
                cList.add(new Country("Chengdu","cd"));
                cList.add(new Country("Mianyang","my"));
                cList.add(new Country("Deyang","dy"));
            }else if(parent.equals("ny")){
                cList.add(new Country("Manhattan","mh"));
            }else if(parent.equals("ca")){
                cList.add(new Country("San Francisco","sf"));
            }else if(parent.equals("dc")){
                cList.add(new Country("WashingtonDC","wdc"));
            }else if(parent.equals("lds")){
                cList.add(new Country("LDRoyal","ldr"));
                cList.add(new Country("LDCity","lcy"));
            }else if(parent.equals("oxs")){
                cList.add(new Country("Oxford","ox"));
                cList.add(new Country("Reading","rd"));
                cList.add(new Country("Wokingham","wkh"));
            }else if(parent.equals("cbs")){
                cList.add(new Country("Cambridge","cb"));
            }
        }
        String cListJson= JSON.toJSONString(cList);
        response.getWriter().println(cListJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
