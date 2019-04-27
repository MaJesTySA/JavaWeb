<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>框架验证码</title>
    <style>#code{height: 30px;}</style>
</head>
<body>
<form action="/login" method="post">
    <p>验证码：
        <input type="text" name="kaptcha" value="" id="code" maxlength="4" placeholder="请输入验证码"/>
        <img src="http://localhost:8080/VerifyCode/kaptcha.jpg" id="changecode"/>
    </p>
    <p>
        <input type="hidden" id="flag" value="2" >
        <input type="button" value="确认" id="login">
    </p>
    <div id="result"></div>
</form>
<script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
<script>
    $(function () {
        $("#changecode").on("click",function () {
            $(this).attr("src","http://localhost:8080/VerifyCode/kaptcha.jpg?d="+new Date().getTime());
        });
        //给登录按钮绑定点击事件
        $("#login").on("click",function () {
            //获取用户输入的验证码
            var code=$("#code").val();
            var flag=$("#flag").val();
            var params={"code":code,"flag":flag};
            $.post("http://localhost:8080/VerifyCode/login",params,function (data) {
               if(data=="success"){
                   $("#result").html("验证码输入正确");
               }else{
                   $("#result").html("验证码输入错误，请重新输出");
                   $("#code").val("").focus();
               }
            })
        })
    })
</script>
</body>
</html>
