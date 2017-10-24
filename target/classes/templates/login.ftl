<!DOCTYPE html>
<html lang="en">
<#include "public/taglib.ftl"/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Signin Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="${basePath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${basePath}/static/css/login.css" rel="stylesheet">
    <script src="${basePath}/static/js/query/jquery-1.11.0.min.js"></script>
</head>

<body>

<div class="container">

    <form id="loginForm" class="form-signin" action="${basePath}/checkLogin" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        ${message!""}
        <label for="inputEmail" class="sr-only">User name</label>
        <input type="email" name="username" id="inputUser" class="form-control" placeholder="User name" required
               onblur="isEmpty('inputUser','userNameDiv')" onfocus="hideDiv('userNameDiv')">
        <div class="div_empty" id="userNameDiv">用户名不能为空！</div>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required
               onblur="isEmpty('inputPassword','passwordDiv')" onfocus="hideDiv('passwordDiv')">
        <div class="div_empty" id="passwordDiv">密码不能为空！</div>
        <label for="twofactorpwdId" class="sr-only">Password</label>
        <input type="text" name="twofactorpwd" id="twofactorpwdId" class="form-control" placeholder="Google Code" required
               onblur="isEmpty('twofactorpwdId','codeDiv')" onfocus="hideDiv('codeDiv')">
        <div class="div_empty" id="codeDiv">动态验证码不能为空!</div>
        <div class="checkbox">
            <label>
                <input type="checkbox" name="remember" value="1"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" onclick="onSubmitLogin()">Sign in</button>
    </form>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${basePath}/static/js/assets/ie10-viewport-bug-workaround.js"></script>
</body>
<script type="text/javascript" >
    function onSubmitLogin(){
        var inputUser = $("#inputUser").val();
        var password = $("#inputPassword").val();
        var twofactorpwd = $("#twofactorpwdId").val();
        var remember = $("input[type='checkbox']:checked").val();
        if(inputUser == '' || password == '' || twofactorpwd == ''){
            alert("用户名，密码，code都不能为空！");
            return;
        }
        if(remember != 1) remember = 0;
        $.ajax({
            url:$("#loginForm").attr("action"),
            type:"post",
            dataType:"json",
            data:{"username":inputUser,"password":password,"twofactorpwd":twofactorpwd,"remember":remember},
            success:function(data){
                if(data.success==1){
                    window.location.href = basePath+data.url;
                }else{
                    alert(data.message);
                }

            }
        });
    }
    function checkEmpty(){
        isEmpty("inputUser","userNameDiv");
        isEmpty("inputPassword","passwordDiv");
        isEmpty("twofactorpwdId","codeDiv");
    }
    function isEmpty(input,div){
        if($("#"+input).val()==''){
            $("#"+div).show();
        }else{
            $("#"+div).hide();
        }
    }

    function hideDiv(div){
        $("#"+div).hide();
    }
</script>
</html>
