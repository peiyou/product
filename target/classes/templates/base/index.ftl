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

    <title><#if from??>${from['menuName']!""}</#if></title>

    <!-- Bootstrap core CSS -->
    <link href="${basePath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${basePath}/static/css/assets/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${basePath}/static/css/dashboard/dashboard.css" rel="stylesheet">
    <!--bootstrap-datetimepicker style -->
    <link href="${basePath}/static/css/jquery-confirm/jquery-confirm.min.css" rel="stylesheet">
    <link href="${basePath}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">



</head>
<body>
<#include "../public/head.ftl" />
<#include "../public/general.ftl" />
<div class="container-fluid">
    <div class="row">
        <#include "../public/common.ftl" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <#--<h1 class="page-header">Dashboard</h1>-->
            <#if params??> <h1 class="page-header">查询</h1></#if>
            <form role="form" class="form-group" id="dateForm" method="post" action="${basePath}/baseController/${id}/index">
                <input type="hidden" name="pageNum" id="pageNum" value="${from['pageNum']!1}" />
                <input type="hidden" name="pageSize" id="pageSize" value="${from['pageSize']!50}" />
                <#if params??>
                    <@paramBear params=params from=from isDownload=isDownload/>
                </#if>
            </form>
            <h2 class="sub-header">数据列表</h2>
            <div class="table-responsive">
                <p></p>
                <#if tableHeads ??>
                    <#if tableHeads?size &lt; 10>
                        <table class="table table-bordered " >
                    <#elseif tableHeads?size &lt; 15 >
                        <table class="table table-150 table-bordered " >
                    <#elseif tableHeads?size &lt; 20 >
                        <table class="table table-200 table-bordered " >
                    <#elseif tableHeads?size &lt; 25 >
                        <table class="table table-250 table-bordered " >
                    <#else>
                        <table class="table table-300 table-bordered " >
                    </#if>
                <#else>
                    <table class="table table-bordered" >
                </#if>
                    <thead>
                    <tr>
                        <@tableHead tableHeads=tableHeads />
                    </tr>
                    </thead>
                    <tbody id="dataBody">
                        <#if pageinfo.list ??>
                             <@tableBody contexts=pageinfo.list tableBodys=tableBodys />
                        </#if>
                    </tbody>
                </table>
            </div>
                <#if pageinfo??>
                    <@pageHandle pageinfo=pageinfo />
                </#if>
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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<!--<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
<![endif]-->
<script type="text/javascript" >
    $(function(){
        $(".datetimepicker").each(function(i,u){
            $(this).datetimepicker({
                minView:2,
                endDate:new Date(),
                format:'yyyy-mm-dd',
                autoclose:"true"
            });
        });
    });

    function onSubmit(){
        $("#dateForm").submit();
    }
<#if isDownload==1>
    function downloadExcel(){
        var old = $("#dateForm").attr("action");
        $("#dateForm").attr("action",basePath + "/baseController/${id}/downloadExcel");
        $("#dateForm").submit();
        $("#dataForm").attr("action",old);
    }
</#if>
</script>
</body>
</html>
