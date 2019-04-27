<%@ page import="code.CaptchaCode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //清空浏览器缓存，浏览器会对图片进行记忆，以后的图片不会跟服务器进行交互，
    //带来的结果就是验证刷新没有效果。
    response.setHeader("pragma","no-cache");
    response.setHeader("cache-control","no-cache");
    response.setHeader("expires","0");

//    String code=CaptchaCode.drawImage(response);
//    session.setAttribute("code",code);
    String code=CaptchaCode.drawImageVerificate(response);
    session.setAttribute("gcalc_code",code);
    out.clear();
    out=pageContext.pushBody();
%>