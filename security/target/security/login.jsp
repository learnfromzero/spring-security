<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/resources/inc.jsp"%><!--静态包含-->
<head>
    <title>登录</title>
    <link rel="stylesheet" href="<%=basePath%>/resources/login/css/login.css">
    <script type="text/javascript" src="<%=basePath%>/resources/js/jquery/jquery-1.9.1.js"></script>
</head>
<body>
    <div id="maiDiv" class="bgd_container">
        <form action ="j_spring_security_check" method="POST">
            <ul class="logContainer">
                <li>
                    <input class="inputBox" type ='text' name='username' placeholder="用户名" onblur="checkUser(this)">
                </li>
                <li>
                    <input class="inputBox" type ='password' name='password' placeholder="密码">
                </li>
                <li>
                    <input class="inputBox" type ='text' name='checkCode' placeholder="验证码">
                </li>
                <li>
                    <input class="logBtn" name ="submit" type="submit" value="登录">
                </li>
                <li class="line">
                    <span>使用下面方式登录：</span>
                </li>
                <li class="thirdLogin">
                    <input type="button" value="公民系统" onclick="XZLogin()"><input type="button" value="QQ">
                </li>
            </ul>
        </form>
    </div>
</body>
<script type="text/javascript">
    function checkUser(a) {
        var userName = $(a).val();
        $.ajax({
            url:'<%=path%>/checkUser.do',
            type:'POST',
            data:{"userName":userName},
            dataType:'JSON',
            success:function (data) {
                var result = JSON.parse(data);
                if(!result.success){
                    alert(result.msg);
                    $("#username").focus();
                    return;
                }
            }
        })
    }
    $(document).ready(function () {
        var a = window.location.search;
        var returnCode = a.substr(1);
        if(a==''){
            $("#logUsr,#log").hide();
        }else{
            var returns = returnCode.split("&");
            for(var i=0;i<returns.length;i++){
                var entry = returns[i];
                var key = entry.substr(0,entry.indexOf("="));
                if(key=="code"){//获取code
                    var code = entry.substr(entry.indexOf("=")+1);
                    //第二步，获取令牌access_token
                    var url = "http://localhost:8080/oauth2/oauth/token?client_id=397a70b8e6c54a3cb8b3edc0a48f5bf3&client_secret=vnpJOzVHfCH1MVQqh24kL2qgUrXiCZ6z&grant_type=authorization_code&code="+code+"&redirect_uri=http%3A%2F%2Flocalhost:8081%2Fsecurity%2Flogin.jsp";
                    $.ajax({
                        url:url,
                        type:"POST",
                        success:function (data) {
                            var access_token = data.access_token;
                            //第三步，通过令牌获取认证用户信息
                            $.ajax({
                                url:'http://localhost:8080/oauth2/m/user_info?access_token='+access_token,
                                type:'GET',
                                success:function(data){
                                    var username = data.username;
                                    alert(username)
                                },error:function () {
                                    alert("发生错误！");
                                }
                            })
                        }
                    })
                    break;
                }
            }
        }
    })

    function XZLogin() {
        var url = "http://localhost:8080/oauth2/oauth/authorize?client_id=397a70b8e6c54a3cb8b3edc0a48f5bf3&redirect_uri=http%3A%2F%2Flocalhost:8081%2Fsecurity%2Flogin.jsp&response_type=code&scope=read&state=your_state";
        window.location.href = url;
    }

</script>
</html>
