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

    <title>调度列表</title>

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


</head>
<body>
<#include "../public/head.ftl" />
<div class="container-fluid">
    <div class="row">
        <#include "../public/common.ftl" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <#--<h1 class="page-header">Dashboard</h1>-->

            <#--<div class="row placeholders">
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
                <div class="col-xs-6 col-sm-3 placeholder">
                    <img src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" width="200" height="200" class="img-responsive" alt="Generic placeholder thumbnail">
                    <h4>Label</h4>
                    <span class="text-muted">Something else</span>
                </div>
            </div>-->
           <#-- <h1 class="page-header">查询</h1>-->
            <form role="form" class="form-inline" id="dateForm" method="post">
                <input type="hidden" name="pageNum" id="pageNum" value="${from.pageNum!1}" />
                <input type="hidden" name="pageSize" id="pageSize" value="${from.pageSize!50}" />
                <#--<div class="form-group">
                    <label for="beginDate">开始时间:</label>
                    <input type="text" name="startDate" class="datetimepicker form-control"
                           id="beginDate" placeholder="Begin Date" value="${from.startDate!}">
                    <label for="endDate">结束时间:</label>
                    <input type="date" name="endDate" class="datetimepicker form-control"
                           id="endDate" placeholder="End Date" value="${from.endDate!}">

                </div>-->
            </form>
            <h2 class="sub-header">数据列表</h2>
            <div class="table-responsive">
                <input type="button" class="btn btn-primary" value="新增" onclick="edit()"/>
                <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">查询</button>
                <p></p>
                <table class="table table-300 table-bordered">
                    <thead>
                    <tr>
                        <th>任务编号</th>
                        <th>任务名称</th>
                        <th>任务组</th>
                        <th>计划表达式</th>
                        <th>类名</th>
                        <th>方法名</th>
                        <th>并发状态</th>
                        <th>是否有参数</th>
                        <th>json参数值</th>
                        <th>任务开始时间</th>
                        <th>任务结束时间</th>
                        <th>任务状态</th>
                        <th>任务描述</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="dataBody">
                        <#list pageinfo.list as p>
                            <tr>
                                <td>${p.jobId!}</td>
                                <td>${p.jobName!}</td>
                                <td>${p.jobGroup!}</td>
                                <td>${p.cronExpression!}</td>
                                <td>${p.beanClass!}</td>
                                <td>${p.methodName!}</td>
                                <td><#if p.isConcurrent==1>可以并发<#else >不可以并发</#if></td>
                                <td><#if p.isParam==0>不需要<#else >需要</#if></td>
                                <td>${p.jsonParam!}</td>
                                <td>${p.startTime!}</td>
                                <td>${p.endTime!}</td>
                                <td><#if p.jobStatus==1>启用<#else>禁用</#if></td>
                                <td>${p.description!}</td>
                                <td>
                                    <input type="button" class="btn btn-primary" value="运行" title="本次会立即运行，之后会按规则运行，如果当前任务是禁用状态，只会运行一次。" onclick="confirmRun(${p.jobId})"/>
                                    <#if p.jobStatus==1>
                                        <input type="button" class="btn btn-primary" value="禁用" title="禁用后，任务将不再运行。" onclick="confirmJobStatus(${p.jobId},0)"/>
                                    <#else >
                                        <input type="button" class="btn btn-primary" value="启用" title="启动后，任务会加入到任务列表。" onclick="confirmJobStatus(${p.jobId},1)"/>
                                    </#if>
                                    <input type="button" class="btn btn-primary" value="修改" onclick="edit(${p.jobId})"/>
                                    <input type="button" class="btn btn-primary" value="删除" onclick="confrimDelete(${p.jobId!})"/>
                                </td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
            <@pageHandle pageinfo=pageinfo />
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
        $('#beginDate').datetimepicker({
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
        });

    });
    function onSubmit(){
        $("#dateForm").submit();
    }

    function confrimDelete(jobId){
        $.confirm({
            title: '删除',
            content: '确定要删除这个任务，删除后无法还原！确定吗？',
            buttons:{
                confirm: {
                    text:'确定',
                    btnClass:'btn-primary',
                    action:function(){
                        $.ajax({
                            url:basePath + '/TaskController/delete',
                            type:"post",
                            dataType:'json',
                            data:{
                                'jobId':jobId
                            },
                            success:function(data){
                                if(data.success){
                                    $.alert("成功删除！");
                                    $("#submitButton").click();
                                }
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary',
                    action: function () {

                    }
                }
            }
        });
    }

    function confirmJobStatus(jobId,jobStatus){
        var title = '';
        var content = '';
        if(jobStatus == 0){
            title = '禁用任务';
            content = '禁用后，任务将不再运行，确定要禁用这个任务吗？';
        }else{
            title = '启用任务';
            content = '启用后，任务将按规则运行，确定要启用这个任务吗？';
        }
        $.confirm({
            title: title,
            content: content,
            buttons:{
                confirm: {
                    text:'确定',
                    btnClass:'btn-primary',
                    action:function(){
                        $.ajax({
                            url:basePath + '/TaskController/update',
                            type:"post",
                            dataType:'json',
                            data:{
                                'jobId':jobId,
                                'jobStatus':jobStatus
                            },
                            success:function(data){
                                if(data.success){
                                    $.alert("成功删除！");
                                    $("#submitButton").click();
                                }
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary',
                    action: function () {

                    }
                }
            }
        });
    }

    function confirmRun(jobId){
        $.confirm({
            title: '立即运行',
            content: '本次会立即运行，之后会按规则运行，如果当前任务是禁用状态，只会运行一次。确定要立即运行吗？',
            buttons:{
                confirm: {
                    text:'确定',
                    btnClass:'btn-primary',
                    action:function(){
                        $.ajax({
                            url:basePath + '/TaskController/nowRun',
                            type:"post",
                            dataType:'json',
                            data:{
                                'jobId':jobId,
                                'tempRunStatus':true
                            },
                            success:function(data){
                                if(data.success){
                                    $.alert("任务已启动！");
                                    setTimeout(function(){
                                        $("#submitButton").click();
                                    },1500);



                                }
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary',
                    action: function () {

                    }
                }
            }
        });
    }

    function edit(jobId){
        if(jobId == null || typeof(jobId)=='undefined')
            window.location.href = basePath + "/TaskController/edit";
        else
            window.location.href = basePath + "/TaskController/edit?jobId="+jobId;
    }
</script>
</body>
</html>
