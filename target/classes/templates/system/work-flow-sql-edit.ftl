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

    <title>报表<#if workFlowSql?? && workFlowSql.id??>编辑<#else>新增</#if></title>

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

            <h1 class="page-header"><#if workFlowSql.id??>编辑<#else>新增</#if></h1>
            <form role="form" class="form-group-sm"
                  action="${basePath}/SystemController/" id="dataForm" method="post">
                <input type="hidden" name="id" value="${workFlowSql.id!}" id="id"/>
                <table class="table table-bordered">
                    <tr>
                        <td><label>流程名:</label><input class="form-control" id="name" name="name" value=""> </td>
                    </tr>
                    <tr>
                        <td><label>全局变量:<input type="button" class="btn btn-xs" value="加参数" onclick="addParam()"></label>
                            <table id="param_table">
                                <tr>
                                    <td>
                                        <input class="form-control-static" name="applicationParam" value="">
                                        <input type="button" class="btn btn-xs" value="删参数" onclick="deleteParam(this)"><br>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <input class="form-control-static" name="applicationParam" value="">
                                        <input type="button" class="btn btn-xs" value="删参数" onclick="deleteParam(this)"><br>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="sql_flow">
                            <label>SQL流程:</label>
                            <ul class="sql_ul">
                                <li>
                                    <div class="sql_data">
                                        <div class="sql_database">
                                            <label>数据库:</label>
                                            <select name="databaseName">
                                                <option value="report">dashboard库</option>
                                                <option value="btcchina_mk">btcchina_mk库</option>
                                                <option value="btcchina_phpbb">btcchina_phpbb库</option>
                                            </select>
                                        </div>
                                        <div class="sql_type">
                                            <label>SQL类型:</label>
                                            <select name="type">
                                                <option value="select">查询</option>
                                                <option value="insert">插入</option>
                                                <option value="update">修改</option>
                                                <option value="delete">删除</option>
                                            </select>
                                        </div>
                                        <div class="sql_run">
                                            <label>SQL:</label>
                                            <textarea name="sql" cols="100" rows="4"></textarea>
                                        </div>
                                        <div class="sql_condition">
                                            <label>下级的条件:</label>
                                            <input name="condition" type="text" class="form-control-static" />
                                        </div>
                                    </div>
                                    <input type="button" style="btn btn-xs" value="添加子级" onclick="addChilder(event)">
                                </li>
                            </ul>
                            <input id="brotherButton" type="button" class="btn btn-xs" value="添加同级节点" onclick="brotherChilder(event)">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>备注:</label>
                            <textarea name="remark" id="remark">${workFlowSql.remark!}</textarea>
                        </td>
                    </tr>
                </table>
                <input type="button" name="" value="提交" onclick="sumbitForm()">
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
        var id = $("#id").val();
        if(id == '' || id.trim == ''){
            return;
        }
        loadData(id);
    });
    function loadData(id){
        $.ajax({
            url:basePath+'/SystemController/sqlWorkFlowById',
            type:"post",
            dataType:'json',
            data:{"id":id},
            success:function(data){
                if(data.workFlowSql){
                    $("#name").val(data.workFlowSql.name);
                    $("#remark").val(data.workFlowSql.remark);
                    var json = eval('(' + data.workFlowSql.data + ')');

                     $("input[name='applicationParam']").each(function(){
                         $(this).parent().parent().remove();
                     });

                    if(typeof(json.applicationParam)!='undefined'){
                        var pa = json.applicationParam.paramsName;
                        for(var i=0;i<pa.length;i++){
                            addParam();
                            $("input[name='applicationParam']:last").val(pa[i]);
                        }
                    }
                    if(json.sqlFlow){
                        for(var i=0;i<json.sqlFlow.length;i++){
                            var ul = $("#sql_flow").children("ul:eq(0)");
                            console.log(ul)
                            var lis = $("#sql_flow").children("ul:eq(0)").children("li");
                            var next = json.sqlFlow[i];
                            if(lis.length - 1 == i){

                            }else{
                                $(ul).append(addLiChilder());
                            }
                            lis = $("#sql_flow").children("ul:eq(0)").children("li");
                            var sql_data = $(lis[i]).children(".sql_data:eq(0)");
                            var sql_database = $(sql_data).children(".sql_database:eq(0)");
                            var databaseName = $(sql_database).children("select[name='databaseName']:eq(0)");
                            $(databaseName).val(next.databaseName);
                            //type
                            var sql_type = $(sql_data).children(".sql_type:eq(0)");
                            var type = $(sql_type).children("select[name='type']:eq(0)");
                            $(type).val(next.type);
                            //sql
                            var sql_run = $(sql_data).children(".sql_run:eq(0)");
                            var sql = $(sql_run).children("textarea:eq(0)");
                            $(sql).val(next.sql);
                            //condition
                            var sql_condition = $(sql_data).children(".sql_condition:eq(0)");
                            var condition = $(sql_condition).children("input[name='condition']:eq(0)");
                            if(typeof(next.condition)!='undefined'){
                                $(condition).val(next.condition);
                            }
                            if(typeof(next.next)!='undefined'){
                                for(var j=0;j<next.next.length;j++){
                                    jsonToHtml(sql_data,next.next[j]);
                                }

                            }
                        }
                    }
                }
            }
        });
    }

    function jsonToHtml(sql_data,json){
        var li = null;
        if($(sql_data).children(".sql_next").length == 0){
            var sqlNext = document.createElement('div');
            sqlNext.className = 'sql_next';
            var ul =  document.createElement("ul");
            ul.className = "sql_ul sql_ul2";
            sqlNext.appendChild(ul);
            $(ul).append(addLiChilder());
            $(sql_data).append(sqlNext);
            li = $(ul).children("li:eq(0)");
        }else{
            var sql_next1 = $(sql_data).children(".sql_next:eq(0)");
            var ul = $(sql_next1).children(".sql_ul:eq(0)");
            $(ul).append(addLiChilder());
            var li = $(ul).children("li:last");
        }
        var next_sql_data = $(li).children(".sql_data");
        var sql_database = $(next_sql_data).children(".sql_database:eq(0)");
        var databaseName = $(sql_database).children("select[name='databaseName']:eq(0)");
        $(databaseName).val(json.databaseName);
        //type
        var sql_type = $(next_sql_data).children(".sql_type:eq(0)");
        var type = $(sql_type).children("select[name='type']:eq(0)");
        $(type).val(json.type);
        //sql
        var sql_run = $(next_sql_data).children(".sql_run:eq(0)");
        var sql = $(sql_run).children("textarea:eq(0)");
        $(sql).val(json.sql);
        //condition
        var sql_condition = $(next_sql_data).children(".sql_condition:eq(0)");
        var condition = $(sql_condition).children("input[name='condition']:eq(0)");
        if(typeof(json.condition)!='undefined'){
            $(condition).val(json.condition);
        }
        if(typeof(json.next)!='undefined'){
            for(var i=0;i<json.next.length;i++){
                jsonToHtml(next_sql_data,json.next[i]);
            }

        }
    }

    function addChilder(event){
        var parent = event.target.parentNode;
        var sqlNext;
        if(parent.getElementsByClassName('sql_next').length > 0){
            sqlNext = parent.getElementsByClassName('sql_next')[0];
            var ul = sqlNext.getElementsByClassName('sql_ul2')[0];
            $(ul).append(addLiChilder());

        }else{
            sqlNext = document.createElement('div');
            sqlNext.className = 'sql_next';
            var ul =  document.createElement("ul");
            ul.className = "sql_ul sql_ul2";
            sqlNext.appendChild(ul);
            $(ul).append(addLiChilder());
            parent.getElementsByClassName("sql_data")[0].appendChild(sqlNext)
        }
    }

    function addLiChilder() {
        var html = '<li>';
                html += '<div class="sql_data">';
                    html += '<div class="sql_database">';
                        html += '<label>数据库:</label>';
                        html += '<select name="databaseName">';
                            html += '<option value="report">dashboard库</option>';
                            html += '<option value="btcchina_mk">btcchina_mk库</option>';
                            html += '<option value="btcchina_phpbb">btcchina_phpbb库</option>';
                        html += '</select>';

                    html+='</div>';
                    html +='<div class="sql_type">';
                        html +='<label>SQL类型:</label>';
                        html += '<select name="type">';
                            html += '<option value="select">查询</option>';
                            html += '<option value="insert">插入</option>';
                            html += '<option value="update">修改</option>';
                            html += '<option value="delete">删除</option>';
                        html += '</select>';

                    html +='</div>';
                    html += '<div class="sql_run">';
                        html += '<label>SQL:</label>';
                        html += '<textarea name="sql" cols="100" rows="4"></textarea>';
                    html += '</div>';
                    html += '<div class="sql_condition">';
                        html += '<label>下级的条件:</label>';
                        html += '<input name="condition" type="text" class="form-control-static" />';
                    html += '</div>';

                html += '</div>';
                html +='<input type="button" style="btn btn-xs" value="添加子级" onclick="addChilder(event)">';
                html += '<input type="button" style="btn btn-xs" value="删除" onclick="deleteChilder(event)">';
            html+=' </li>';
            return $(html);
    }

    function deleteChilder(event){
        var parent = event.target.parentNode; // li
        var parentUl = parent.parentNode;
        parentUl.removeChild(parent);
        if(parentUl.getElementsByTagName("li").length ==0){
            var div = parentUl.parentNode;
            div.removeChild(parentUl);
            div.parentNode.removeChild(div);
        }
    }

    function brotherChilder(event){
       var td = event.target.parentNode;
       var ul = td.getElementsByClassName("sql_ul")[0];
       $(ul).append(addLiChilder());
    }

    var params_application = {};
    function buildJSON(){
        var params = {};
        var name_ = $("#name").val();
        if(name_ == ''){
            $.alert("名称不能为空!");
            return false;
        }
        var id = $("#id").val();
        if(id!=''){
            params.id = id;
        }
        params.name = $("#name").val();
        params.remark = $("#remark").val();

        var json = {};
        json.name = $("#name").val();
        //applicationParam
        if($("input[name='applicationParam']").length > 0){
            json.applicationParam = {};
            json.applicationParam.paramsName = [];
            var flag = true;
            $("input[name='applicationParam']").each(function(i,u){
                if($(u).val() == ''){
                    flag = false;
                    return true;
                }
                json.applicationParam.paramsName[i] = $(u).val();
            });
            if(!flag){
                $.alert("全局参数不能为空，如果没有，请删除输入框！");
                return flag;
            }

        }
        json.sqlFlow = [];
        var sql_flow = document.getElementById("sql_flow");
        var ul = sql_flow.getElementsByClassName("sql_ul")[0];
        var lis = ul.children;
        var f = true;
        for(var i=0;i<lis.length;i++){
            f = analysisLi(lis[i],json.sqlFlow,i);
            if(!f){
                break;
            }
        }
        if(!f){
            return false;
        }
        console.log(json)
        console.log(JSON.stringify(json))
        params.data = JSON.stringify(json);
        params_application = params;
        return true;
    }

//    function getChild(parent){
//        for(var i=0; i<parent.childNodes.length;i++){
//            if(parent.childNodes.length !== 0){
//                getChild(parent.childNodes[i])
//            }else{
//                return parent.childNodes[i]
//            }
//        }
//    }


    function analysisLi(li,json,index){
        var sql_data = li.getElementsByClassName("sql_data")[0];
        // database
        var sql_database = sql_data.getElementsByClassName("sql_database")[0];

        var databaseName = sql_database.getElementsByTagName("select")[0];
        var dindex = databaseName.selectedIndex;
        databaseName = databaseName.options[dindex].value;

        // sql_typle
        var sql_type =  sql_data.getElementsByClassName('sql_type')[0];
        var type = sql_type.getElementsByTagName("select")[0];
        var tindex = type.selectedIndex;
        type = type.options[tindex].value;
        //run_sql
        var sql_run = sql_data.getElementsByClassName("sql_run")[0];
        var sql = sql_run.getElementsByTagName("textarea")[0].value;
        if(sql == '' || sql.trim == ''){
            $.alert("sql 都不可以为空，请输入正确的sql语句！")
            return false;
        }
        var flow_sql = {};
        flow_sql.databaseName = databaseName;
        flow_sql.type = type;
        flow_sql.sql = sql;
        //condition
        var sql_condition = sql_data.getElementsByClassName("sql_condition")[0];
        var condition = sql_condition.getElementsByTagName("input")[0].value;
        if(!(condition == '' || condition.length == 0)){
            flow_sql.condition = condition;
        }
        if(sql_data.getElementsByClassName("sql_next").length > 0){
            var sql_next = sql_data.getElementsByClassName("sql_next")[0];
            var sql_ul2 = sql_next.getElementsByClassName("sql_ul2")[0];
            var lis = sql_ul2.children;
            flow_sql.next = [];
            for(var i=0;i<lis.length;i++){
                analysisLi(lis[i],flow_sql.next,i);
            }
        }
        json[index] = flow_sql;
        return true;


    }

    function addParam(){
        var html = '<tr><td><input class="form-control-static" name="applicationParam" value="">  <input type="button" class="btn btn-xs" value="删参数" onclick="deleteParam(this)"><br></td></tr>';
        $("#param_table").append(html);
    }


    function deleteParam(btn){
        $(btn).parent().parent().remove()
    }

    function sumbitForm(){
        var url = basePath + '/SystemController/insertWorkSqlFlow';
        var id = $("#id").val();
        if(id != ''){
            url = basePath + '/SystemController/updateWorkSqlFlow';
        }
        var flag = buildJSON();
        if(flag){
            $.ajax({
                url:url,
                type:"post",
                dataType:'json',
                data:params_application,
                success:function(data){
                    if(data.success){
                        $.alert("提交成功");
                        window.location.href = basePath + "/SystemController/sqlWorkFlowIndex";
                    }else{
                        $.alert("系统出现异常情况...");
                    }
                }
            });
        }
    }
</script>
</body>
</html>
