/*
  Web服务器打开，就查询所有分类，存到全局对象中，以免每次添加/编辑都需要查询所有分类。提高效率
*/
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
