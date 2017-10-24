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

    <title>角色<#if from?? && from.id??>编辑<#else>新增</#if></title>

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
    <link  type="text/css" href="${basePath}/static/css/bootstrap-treeview/bootstrap-treeview.css"/>

</head>
<body>
<#include "../public/head.ftl" />
<div class="container-fluid">
    <div class="row">
        <#include "../public/common.ftl" />
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                菜单
                            </h4>
                        </div>
                        <div class="modal-body">
                            <!-- 菜单内容 -->
                            <div id="tree"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">
                                确定
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

            <h1 class="page-header"><#if from?? && from.id??>编辑<#else>新增</#if></h1>
            <form role="form" class="form-horizontal" action="dateForm" id="dataForm" method="post">
                <input type="hidden" name="id" value="${from.id!}" id="id"/>
                <input type="hidden" name="parentId" value="${from.parentId!-1}" id="parentId"/>
                <div class="form-group">
                   <label>父级菜单：</label>
                   <label id="parentId_label">顶级菜单</label>
                   <input type="button" class="btn btn-default" value="选择父级" data-toggle="modal" data-target="#myModal" />
                </div>
                <div class="form-group">
                    <label >菜单名称:</label>
                    <input type="text" class="form-control" id="menuName" name="menuName" value="${from.menuName!}" placeholder="请输入菜单名称">
                </div>
                <div class="form-group">
                    <label >URL:</label>
                    <input type="text" class="form-control" id="url" name="url" value="${from.url!}" placeholder="请输入URL">
                </div>
                <div class="form-group">
                    <label>状态：</label>
                    <label ><input type="radio" name="disable" id="disable1"
                                   <#if !from.disable?? ||  from.disable != 1>checked="checked"</#if>
                                   value="0">启用</label>
                    <label ><input type="radio" name="disable" id="disable2"
                                   <#if from.disable?? && from.disable == 1>checked="checked"</#if>
                                   value="1">禁用</label>
                </div>
                <div class="form-group">
                    <label for="sequence">顺序：</label>
                    <input type="number" class="form-control" id="sequence" name="sequence" value="${from.sequence!1}"/>
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
<script src="${basePath}/static/js/bootstrap/bootstrap.js"></script>
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
<script type="text/javascript" src="${basePath}/static/js/bootstrap-treeview/bootstrap-treeview.js"></script>

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
            url = basePath + "/SystemController/insertMenu"
        else
            url = basePath + "/SystemController/updateMenu"
        var menuName=$("#menuName").val();

        if(menuName==''){
            $.alert("菜单名称不能为空！");
            return ;
        }
        var menuUrl = $("#url").val();
        var param = {
            'id':id,
            'menuName':menuName,
            'parentId':$("#parentId").val(),
            'sequence':$("#sequence").val(),
            'remark':$("#remark").val(),
            'url':$.trim(menuUrl)
        };
        $.ajax({
            url:url,
            type:"post",
            dataType:'json',
            data:param,
            success:function(data){
                if(data.success){
                    $.alert("提交成功");
                    window.location.href = basePath + "/SystemController/menuIndex";
                }
            }
        });
    }


    var menuDirectory =  eval('(' + '${menuDirectory!}' + ')');
    var defaultNode = "${from.parentId!-1}";
    var treeData = new Array();

    function formatTreeData(treeData){
        //生成顶级菜单
        var firstNode = {"text":"顶级菜单" ,"value":-1};
        if(defaultNode == -1){
            firstNode["state"] = {"selected":true};
            $("#parentId").val(-1);
            $("#parentId_label").text("顶级菜单");
        }
        formatTreeData2(firstNode,-1);
        treeData[0] = firstNode;
    }

    function formatTreeData2(treeDataNode,parentId){
        var nodes = new Array();
        for(var i=0;i<menuDirectory.length;i++){
            if(menuDirectory[i].parentId == parentId){
                var nodeNode = {"text":menuDirectory[i].menuName,"value":menuDirectory[i].id};
                if(defaultNode == menuDirectory[i].id){
                    nodeNode["state"] = {"selected":true};
                    $("#parentId").val(menuDirectory[i].id);
                    $("#parentId_label").text(menuDirectory[i].menuName);
                }
                formatTreeData2(nodeNode,menuDirectory[i].id);
                nodes.push(nodeNode);
                treeDataNode["nodes"] = nodes;
            }
        }
    }

    function getTree() {
        formatTreeData(treeData);
        return treeData;
    }
    $('#tree').treeview(
        {
            data: getTree()
        }
    );

    $('#tree').on('nodeSelected', function(event, data) {
        $("#parentId").val(data.value);
        $("#parentId_label").text(data.text);
    });

    $("#tree").on('nodeUnselected',function(event, data){
        $('#tree').treeview('nodeSelected', [ 1, { silent: true } ]);
        $("#parentId").val(-1);
        $("#parentId_label").text("顶级菜单");
    });

    function breakIndex(){
        window.location.href = basePath + "/SystemController/menuIndex";
    }
</script>
</body>
</html>
