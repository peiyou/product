<!DOCTYPE html>
<html lang="en">
<#include "../public/taglib.ftl"/>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
   <#-- <link rel="icon" href="../../favicon.ico">-->

    <title>用户<#if from?? && from.id??>编辑<#else>新增</#if></title>

    <!-- Bootstrap core CSS -->
    <link href="${basePath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${basePath}/static/css/assets/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${basePath}/static/css/dashboard/dashboard.css" rel="stylesheet">
    <!--bootstrap-datetimepicker style -->
    <link href="${basePath}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${basePath}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="${basePath}/static/css/jquery-confirm/jquery-confirm.min.css" rel="stylesheet">


    <link type="text/css" href="${basePath}/static/js/cron/jquery-cron.css" rel="stylesheet" />
    <link type="text/css" href="${basePath}/static/js/gentleSelect/jquery-gentleSelect.css" rel="stylesheet">
</head>
<body>
<#include "../public/head.ftl" />
<div class="container-fluid">
    <div class="row">
        <#include "../public/common.ftl" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <h1 class="page-header"><#if from?? && from.id??>编辑<#else>新增</#if></h1>
            <form role="form" class="form-horizontal" action="dateForm" id="dataForm" method="post">
                <input type="hidden" name="id" value="${from.id!}" id="id"/>
                <div class="form-group">
                    <label >用户名:</label>
                    <input type="text" class="form-control" id="username" name="username" value="${from.username!}" placeholder="请输入用户名">
                </div>

                <div class="form-group radio">
                    <label ><input type="radio" name="disable" id="disable1"
                                   <#if !from.disable?? ||  from.disable != 1>checked="checked"</#if>
                                   value="0">启用</label>
                    <label ><input type="radio" name="disable" id="disable2"
                                   <#if from.disable?? && from.disable == 1>checked="checked"</#if>
                                   value="1">禁用</label>
                </div>

                <div class="checkbox">
                    <label for="name">角色:</label>
                    <#if roles??>
                        <#list roles as r>
                            <#assign flag = false>
                                <#if currentRoles??>
                                    <#list currentRoles as c>
                                        <#if c.id == r.id >
                                            <#assign flag = true>
                                        </#if>
                                    </#list>
                                </#if>
                            <#if flag>
                                <label>
                                    <input type="checkbox" checked="checked" value="${r.id}" name="roles" id="role${r.id}">
                                ${r.role}
                                </label>
                            <#else >
                                <label>
                                    <input type="checkbox" value="${r.id}" name="roles" id="role${r.id}">
                                ${r.role}
                                </label>
                            </#if>
                        </#list>
                    </#if>
                </div>

                <div class="form-group">
                    <label for="name">描述:</label>
                    <textarea class="form-control" rows="3" name="remark" id="remark">${from.remark!}</textarea>
                </div>
                <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">提交</button>
                <button type="button" class="btn btn-default" onclick="breakIndex()" id="submitButton">返回</button>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${basePath}/static/js/query/jquery-1.11.0.min.js"></script>
<script src="${basePath}/static/js/jquery-confirm/jquery-confirm.min.js" ></script>
<#--<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>-->
<script src="${basePath}/static/js/bootstrap/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<#--<script src="../../assets/js/vendor/holder.min.js"></script>-->
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${basePath}/static/js/assets/ie10-viewport-bug-workaround.js"></script>

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="${basePath}/static/js/assets/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="${basePath}/static/js/assets/ie-emulation-modes-warning.js"></script>
<!-- -->
<script src="${basePath}/static/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath}/static/js/public/page.js" ></script>
<script type="text/javascript" src="${basePath}/static/js/cron/jquery-cron.js"></script>
<script type="text/javascript" src="${basePath}/static/js/gentleSelect/jquery-gentleSelect.js" ></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<!--<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
<![endif]-->
<script type="text/javascript" >
    $(function(){


    });
    function onSubmit(){
        var id = $("#id").val();
        var url = '';
        if(id=='' || id == null)
            url = basePath + "/SystemController/insertUser"
        else
            url = basePath + "/SystemController/updateUser"
        var username=$("#username").val();
        var disable=$("input[name='disable']:checked").val();

        var roles = new Array();
        $("input[name='roles']:checked").each(function(i,u){
            roles.push($(u).val());
        });
        if(username==''){
            $.alert("用户名不能为空！");
            return ;
        }
        var param = {
            'id':id,
            'username':username,
            'disable':disable,
            'roles':roles,
            'remark':$("#remark").val()
        };
        $.ajax({
            url:url,
            type:"post",
            dataType:'json',
            data:param,
            success:function(data){
                if(data.success){
                    $.alert("提交成功");
                    window.location.href = basePath + "/SystemController/userIndex";
                }
            }
        });
    }

    function jsonParamDiv(num){
        if(num == 0){
            $("#jsonParamDiv").hide();
        }else{
            $("#jsonParamDiv").show();
        }
    }

    function breakIndex(){
        window.location.href = basePath + "/SystemController/userIndex";
    }
</script>
</body>
</html>
