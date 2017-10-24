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

    <title>报表<#if from?? && from.id??>编辑<#else>新增</#if></title>

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

            <h1 class="page-header"><#if id??>编辑<#else>新增</#if></h1>
            <form role="form" class="form-group"
                  action="${basePath}/SystemController/deitBuildReportData" id="dataForm" method="post">
                <input type="hidden" name="id" value="${id!}" id="id"/>
                <div class="input-group">
                    <label for="menuName">报表名称：</label>
                    <input type="text" id="menuName" name="menuName" >
                </div>
                <div class="input-group">
                    <label for="">数据源：</label>
                    <select class="form-inline" name="datasource" id="datasource">
                        <option value="report">report库</option>
                        <option value="btcchina_mk">btcchina_mk库</option>
                        <option value="btcchina_phpbb" >btcchina_phpbb库</option>
                        <option value="prog" >prog库</option>
                        <option value="spotusd" >spotusd库</option>
                        <option value="bttx">bttx库</option>
                    </select>
                </div>
                <div class="input-group">
                    <label for="">sql:</label>
                    <textarea id="sql" name="sql" cols="100" rows="4" ></textarea>
                </div>
                <div class="input-group hidden" id="param_div_hidden">
                    <input class="btn btn-default" type="button" onclick="addParam2()" value="加参数">
                </div>
                <div class="input-group params_flag" id="param_div0">
                    <input type="text" name="label"  placeholder="参数描述"/>
                    <input type="text" name="name"  placeholder="参数名"/>
                    <select class="form-inline" name="type" id="type">
                        <option value="text">文本类型</option>
                        <option value="list">list类型</option>
                        <option value="date">时间类型</option>
                    </select>
                    <input name="default" placeholder="默认值" type="text">
                    <label><input name="requisite" type="checkbox" value="yes">必填</label>
                    <input type="button" class="btn btn-xs" onclick="addParam(this)" value="加参数">
                    <input type="button" class="btn btn-xs" onclick="deleteParam(this)" value="删除">
                </div>
                <div class="input-group">
                    <label for="menuName">报表头(用英文逗号分割,)：</label>
                    <input type="text" id="tableHead" name="tableHead" class="form-control" >
                </div>
                <div class="input-group">
                    <label for="menuName">数据对应关系(用英文逗号分割,)：</label>
                    <input type="text" id="tableBody" name="tableBody" class="form-control" >
                </div>
                <div class="input-group">
                    <label><input name="isDownload" type="checkbox" value="1">支持excel下载</label>
                </div>
                <br>
                <div class="input-group">
                    <label for="remark">备注:</label>
                    <textarea id="remark" name="remark" cols="100" rows="4" ></textarea>
                </div>
                <br>
                <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">提交</button>
                <button type="button" class="btn btn-default" onclick="breakIndex()" id="breakButton">返回</button>
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
    var indexDiv_id = 1;
    var div_count = 1;
    var hiddenDiv_id = '';
    $(function(){
        loadData();
    });

    function loadData(){
        var id = $("#id").val();
        if(id != ''){
            $.ajax({
                url:basePath+'/SystemController/queryBuildReport',
                type:"post",
                dataType:'json',
                data:{"id":id},
                success:function(data){
                    if(data.success){
                        $("#sql").val(data.params.sql);
                        $("#datasource").val(data.params.datasource);
                        $("#menuName").val(data.params.menuName);
                        $("#tableHead").val(data.params.tableHead);
                        $("#tableBody").val(data.params.tableBody);
                        $("#remark").val(data.buildReport.remark);
                        if(data.params.isDownload == 1)
                            $("input[name='isDownload']:eq(0)").prop("checked",true);
                        $.each(data.params.params,function(i,u){
                            if(i > 0){
                                var div = $("#param_div0");
                                var newDiv = div.clone(true);
                                $(newDiv).attr("id","param_div"+indexDiv_id);
                                div.after(newDiv);
                                indexDiv_id++;
                                div_count++;
                            }
                            $(".params_flag [name='label']:eq("+i+")").val(u.label);
                            $(".params_flag [name='name']:eq("+i+")").val(u.name);
                            $(".params_flag [name='type']:eq("+i+")").val(u.type);
                            $(".params_flag [name='default']:eq("+i+")").val(u.default);
                            if(u.requisite == 'yes'){
                                $(".params_flag [name='requisite']:eq("+i+")").prop("checked",true);
                            }

                        });

                        if(data.params.params.length == 0){

                            $("#param_div0").hide();
                            $("#param_div0").removeClass("params_flag");
                            hiddenDiv_id = $("#param_div0").attr("id");
                            $("#param_div_hidden").removeClass("hidden");
                            div_count--;
                        }
                    }
                }
            });
        }
    }


    var bigParams = {};
    function buildParam(){
        var params = {};
        var sql = $("#sql").val();
        var datasource = $("#datasource").val();
        var menuName = $("#menuName").val();
        var tableHead =  $("#tableHead").val();
        var tableBody = $("#tableBody").val();
        var remark = $("#remark").val();
        if(sql.trim() == ''){
            alert("sql 不能为空！");
            return false;
        }
        if(menuName.trim() == ''){
            alert("报表名称不能为空！");
            return false;
        }
        if(tableHead.trim()==''){
            alert("报表头不能为空！");
            return false;
        }
        if(tableBody.trim()==''){
            alert("数据对应关系不能为空！");
            return false;
        }
        var tableHeads = tableHead.split(",");
        var tableBodys = tableBody.split(",");
        if(tableHeads.length != tableBodys.length){
            alert("报表头与数据对应关系不一致，请确认后输入！");
            return false;
        }

        params.sql = sql;
        params.datasource = datasource;
        params.menuName = menuName;
        params.tableHead = tableHead;
        params.tableBody = tableBody;
        var isDownload = $("input[name='isDownload']:eq(0)");
        if(isDownload.is(":checked")){
            params.isDownload = 1;
        }else{
            params.isDownload = 0;
        }
        var len = $(".params_flag [name='label']").length;
        var childParams = new Array();
        var flag = false;
        for(var i=0;i<len;i++){
            var nParam = {};
            var label = $(".params_flag [name='label']:eq("+i+")").val();
            var name = $(".params_flag [name='name']:eq("+i+")").val();
            var type = $(".params_flag [name='type']:eq("+i+")").val();
            var default_ = $(".params_flag [name='default']:eq("+i+")").val();
            var requisite = 'no';
            var check = $(".params_flag [name='requisite']:eq("+i+")");
            if(check.is(":checked")){
                requisite = 'yes';
            }
            if(label.trim()=='' || name.trim()=='' || type.trim()==''){
                flag = true;
                break;
            }
            nParam.label = label;
            nParam.name = name;
            nParam.type = type;
            nParam.default = default_;
            nParam.requisite = requisite;
            childParams.push(nParam);
        }
        if(flag){
            alert("所有参数的描述、名字、类型不能为空！");
            return false;
        }
        params.params = childParams;
        var p = JSON.stringify(params);
        var id = $("#id").val();
        if(id.trim()!=''){
            bigParams.id = id;
        }
        bigParams.remark = remark;
        bigParams.menuName = menuName;
        bigParams.params = p;
        return true;
    }

    function addParam2(){
        $("#"+hiddenDiv_id).show();
        $("#"+hiddenDiv_id).addClass("params_flag");
        $("#param_div_hidden").addClass("hidden");
        indexDiv_id++;
        div_count++;
    }
    function addParam(btn){
        var div = $(btn).parent();
        var newDiv = div.clone(true);
        $(newDiv).attr("id","param_div"+indexDiv_id);
        div.after(newDiv);
        indexDiv_id++;
        div_count++;
    }

    function deleteParam(btn) {
        if(div_count == 1){//只有一个的时候
            $(btn).parent().hide();
            $(btn).parent().removeClass("params_flag");
            hiddenDiv_id = $(btn).parent().attr("id");
            $("#param_div_hidden").removeClass("hidden");
            div_count--;
            return;
        }
        $(btn).parent().remove();
        div_count--;
    }

    function onSubmit(){
        var id = $("#id").val();
        var url = '';
        if(id=='' || id == null)
            url = basePath + "/SystemController/insertBuildReport"
        else
            url = basePath + "/SystemController/updateBuildReport"

        if(!buildParam()){//绑定和验证参数
            return;
        }
        $.ajax({
            url:url,
            type:"post",
            dataType:'json',
            data:bigParams,
            success:function(data){
                if(data.success){
                    $.alert("提交成功");
                    window.location.href = basePath + "/SystemController/buildReportIndex";
                }else{
                    $.alert("系统出现异常情况...");
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
        window.location.href = basePath + "/SystemController/buildReportIndex";
    }
</script>
</body>
</html>
