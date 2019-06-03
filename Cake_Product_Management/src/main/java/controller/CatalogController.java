package controller;

import biz.CatalogBiz;
import biz.impl.CatalogBizImpl;
import entity.Catalog;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogController {
    /*
     /admin/Catalog/list.do
     //打开添加界面
     /admin/Catalog/toAdd.do
     /admin/Catalog/add.do
     /admin/Catalog/remove.do
     */
    private CatalogBiz catalogBiz = new CatalogBizImpl();

    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询所有节点，只需查询根节点
        Catalog root=catalogBiz.getRoot();
        request.getServletContext().setAttribute("root",root);
        //这里只能用请求转发，不能重定向，因为有req.setAttr
        //WEB-INF用户无法访问，比较安全
        request.getRequestDispatcher("/WEB-INF/pages/admin/catalog_list.jsp").forward(request,response);
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Catalog root=catalogBiz.getRoot();
        request.setAttribute("root",root);
        //这里只能用请求转发，不能重定向，因为有req.setAttr
        //WEB-INF用户无法访问，比较安全
        request.getRequestDispatcher("/WEB-INF/pages/admin/catalog_add.jsp").forward(request,response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] titles=request.getParameterValues("title");
        String[] pids=request.getParameterValues("pid");
        String[] infos=request.getParameterValues("info");
        List<Catalog> list=new ArrayList<Catalog>();
        for (int i = 0; i <titles.length ; i++) {
            System.out.println(pids[i]);
            Catalog catalog=new Catalog();
            catalog.setTitle(titles[i]);
            catalog.setPid(Integer.parseInt(pids[i]));
            catalog.setInfo(infos[i]);
            list.add(catalog);
        }
        catalogBiz.add(list);
        response.sendRedirect("list.do");
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        catalogBiz.remove(id);
        response.sendRedirect("list.do");
    }
}

