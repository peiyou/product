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

    <title>${type} ${currency} data</title>

    <!-- Bootstrap core CSS -->
    <link href="${basePath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${basePath}/static/css/assets/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${basePath}/static/css/dashboard/dashboard.css" rel="stylesheet">
    <!--bootstrap-datetimepicker style -->
    <link href="${basePath}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${basePath}/static/css/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">



</head>
<body>
<#include "../public/head.ftl" />
<div class="container-fluid">
    <div class="row">
    <#include "../public/common.ftl" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <h1 class="page-header">查询</h1>
            <form id="dateForm" role="form" class="form-inline" action="${basePath}/withdrawOrDepositController/${type}/${currency}/index"
                method="post">
                    <input type="hidden" value="${basePath}/withdrawOrDepositController/${type}/${currency}/downloadExcel" id="urlDownload">
                    <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">查询</button>
                    <button type="button" class="btn btn-default" onclick="downloadExcel()" id="submitButton">下载excel</button>
            </form>
            <h2 class="sub-header">数据列表</h2>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <td>id</td>
                        <td>user_id</td>
                        <td>name</td>
                        <td>phone</td>
                        <td>phone_country</td>
                        <td>value</td>
                        <td>account_type</td>
                        <td>amount</td>
                        <td>bj_time</td>
                        <td>email</td>
                    </tr>
                    </thead>
                    <tbody id="dataBody">
                    <#if list??>
                        <#list list as p>
                            <tr>
                                <td>${p.id!}</td>
                                <td>${p.user_id!}</td>
                                <td>${p.name!}</td>
                                <td>${p.phone!}</td>
                                <td>${p.phone_country!}</td>
                                <td>${p.value!}</td>
                                <td>${p.account_type!}</td>
                                <td>${p.amount!}</td>
                                <td>${p.bj_time!}</td>
                                <td>${p.user_email!}</td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${basePath}/static/js/query/jquery-1.11.0.min.js"></script>

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
        /*$('#beginDate').datetimepicker({
            minView:2,
            endDate:new Date(),
            format:'yyyy-mm-dd'
        }).on('changeDate',function(){
            var beginDate = $("#beginDate").val();
            $("#endDate").datetimepicker('setStartDate',beginDate);
            $("#beginDate").datetimepicker('hide');
        });

        $('#endDate').datetimepicker({
            minView:2,
            endDate:new Date(),
            format:'yyyy-mm-dd'
        }).on('changeDate',function(){
            var endDate = $("#endDate").val();
            $("#beginDate").datetimepicker('setEndDate',endDate);
            $("#endDate").datetimepicker('hide');
        });*/

        //dataAjax();
    });

    function onSubmit(){
        $("#dateForm").submit();
    }

    function downloadExcel() {
        var old = $("#dateForm").attr("action");
        var new_ = $("#urlDownload").val();
        $("#dateForm").attr("action",new_);
        $("#dateForm").submit();

        $("#dateForm").attr("action",old);
    }
</script>
</body>
</html>
