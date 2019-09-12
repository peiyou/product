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

    <title>调度任务编辑</title>

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

            <h1 class="page-header">编辑</h1>
            <form role="form" class="form-horizontal" action="dateForm" id="dataForm" method="post">
                <input type="hidden" name="jobId" value="${job.jobId!}" id="jobId"/>
                <div class="form-group">
                    <label >任务组:</label>
                    <input type="text" class="form-control" id="jobGroup" name="jobGroup" value="${job.jobGroup!}" placeholder="请输入组名">
                </div>
                <div class="form-group">
                    <label >任务名称:</label>
                    <input type="text" class="form-control" id="jobName" name="jobName" value="${job.jobName!}" placeholder="请输入名称">
                </div>
                <div class="form-group">
                    <label >任务周期:</label>
                    <div id='selector'> </div>
                    <div><input type="text"  class="form-control" name="cronExpression" id="cronExpression" value="${job.cronExpression!}"></div>
                </div>
                <div class="form-group">
                    <label >类名:</label>
                    <input type="text" class="form-control" id="beanClass" name="beanClass" placeholder="请输入包名.类名" value="${job.beanClass!}">
                </div>
                <div class="form-group">
                    <label >方法名:</label>
                    <input type="text" class="form-control" id="methodName" name="methodName" placeholder="请输入方法名" value="${job.methodName!}">
                </div>
                <div class="form-group radio">
                    <label ><input type="radio" name="isConcurrent" id="isConcurrent1"
                                   <#if job.isConcurrent?? && job.isConcurrent == 1>checked="checked"</#if>
                                   value="1">可以并发</label>
                    <label ><input type="radio" name="isConcurrent" id="isConcurrent2"
                                   <#if !job.isConcurrent?? || job.isConcurrent != 1>checked="checked"</#if>
                                   value="0">不可以并发</label>
                </div>
                <div class="form-group radio">
                    <label ><input type="radio" name="isParam" id="isParam1"
                                   <#if job.isParam?? && job.isParam == 1>checked="checked"</#if>
                                   value="1" onclick="jsonParamDiv(1)">需要参数</label>
                    <label ><input type="radio" name="isParam" id="isParam2"
                                   <#if !job.isParam?? || job.isParam != 1>checked="checked"</#if>
                                   value="0" onclick="jsonParamDiv(0)">不需要参数</label>
                </div>
                <div class="form-group" id="jsonParamDiv">
                    <label>参数：</label>
                    <textarea class="form-control" rows="3" name="jsonParam" id="jsonParam">${job.jsonParam!}</textarea>
                </div>
                <div class="form-group">
                    <label for="beginDate">开始时间:</label>
                    <input type="text" name="startTime" class="datetimepicker form-control"
                           id="beginDate" placeholder="Begin Date" value="${job.startTime!}">
                    <label for="endDate">结束时间:</label>
                    <input type="date" name="endTime" class="datetimepicker form-control"
                           id="endDate" placeholder="End Date" value="${job.endTime!}">
                </div>
                <div class="form-group radio">
                    <label ><input type="radio" name="jobStatus" id="jobStatus1"
                                   <#if !job.jobStatus?? ||  job.jobStatus != 0>checked="checked"</#if>
                                   value="1">启用</label>
                    <label ><input type="radio" name="jobStatus" id="jobStatus2"
                                   <#if job.jobStatus?? && job.jobStatus == 0>checked="checked"</#if>
                                   value="0">禁用</label>
                </div>

                <div class="form-group">
                    <label for="name">描述:</label>
                    <textarea class="form-control" rows="3" name="description" id="description">${job.description!}</textarea>
                </div>
                <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">提交</button>
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
        $("#beginDate").hide();
        $("#endDate").hide();
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

        var cronNumSpet = 1;
        $('#selector').cron({
            initial: "0 10 11 * *",
            onChange: function() {
                var value = $(this).cron("value");
                var vs = value.split(" ");
                if (vs[vs.length - 1] == '*'){
                    value = "0 "+ value.substr(0,value.length - 1) + '?'
                }else if(vs[2]=="*"){
                    vs[2] = "?";
                    value = '0 ';
                    for(var i=0;i<vs.length;i++){
                        value += vs[i];
                        if(i < vs.length -1){
                            value += ' ';
                        }
                    }
                }
                if(cronNumSpet == 1){
                    cronNumSpet++
                }else{
                    $('#cronExpression').val(value);
                }
            },
            useGentleSelect: true // default: false /
        });
    });
    function onSubmit(){
        var jobId = $("#jobId").val();
        var url = '';
        if(jobId=='' || jobId == null)
            url = basePath + "/TaskController/insert"
        else
            url = basePath + "/TaskController/update"
        var jobGroup=$("#jobGroup").val();
        var jobName=$("#jobName").val();
        var cronExpression=$("#cronExpression").val();
        var beanClass=$("#beanClass").val();
        var methodName=$("#methodName").val();
        if(jobGroup==''){
            $.alert("任务组名称不能为空！");
            return ;
        }

        if(jobGroup==''){
            $.alert("任务组名称不能为空！");
            return ;
        }
        if(jobName==''){
            $.alert("任务名称不能为空！");
            return ;
        }
        if(cronExpression==''){
            $.alert("任务计划不能为空！");
            return ;
        }
        if(beanClass==''){
            $.alert("类名不能为空！");
            return ;
        }
        if(methodName==''){
            $.alert("方法名不能为空！");
            return ;
        }
        var flag = false;
        $.ajax({
            url:basePath + "/TaskController/checkCronExpression",
            type:"post",
            dataType:'json',
            async: false,
            data:{'cron':cronExpression},
            success:function(data){
                flag = data.success;
            }

        });

        if(!flag){//表达式错误
            $.alert("cron 表达式无法验证通过，请确认后重新输入");
            return;
        }

        var param = {
            'jobId':jobId,
            'jobGroup':$("#jobGroup").val(),
            'jobName':$("#jobName").val(),
            'cronExpression':$("#cronExpression").val(),
            'beanClass':$("#beanClass").val(),
            'methodName':$("#methodName").val(),
            'isConcurrent':$("input[name='isConcurrent']:checked").val(),
            'isParam':$("input[name='isParam']:checked").val(),
            'jsonParam':$("#jsonParam").val(),
            'startTime':$("#beginDate").val(),
            'endTime':$("#endDate").val(),
            'jobStatus':$("input[name='jobStatus']:checked").val(),
            'description':$("#description").val()
        };
        $.ajax({
            url:url,
            type:"post",
            dataType:'json',
            data:param,
            success:function(data){
                if(data.success){
                    $.alert("提交成功");
                    window.location.href = basePath + "/TaskController/index";
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
</script>
</body>
</html>
