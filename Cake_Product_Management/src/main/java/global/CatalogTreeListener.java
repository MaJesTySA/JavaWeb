package global;

import biz.CatalogBiz;
import biz.impl.CatalogBizImpl;
import entity.Catalog;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class CatalogTreeListener implements ServletContextListener{
    private CatalogBiz catalogBiz=new CatalogBizImpl();
    public void contextInitialized(ServletContextEvent sce) {
        Catalog root=catalogBiz.getRoot();
        sce.getServletContext().setAttribute("root",root);
    }
}
