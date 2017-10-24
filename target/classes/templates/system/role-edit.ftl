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

            <h1 class="page-header"><#if from?? && from.id??>编辑<#else>新增</#if></h1>
            <form role="form" class="form-horizontal" action="dateForm" id="dataForm" method="post">
                <input type="hidden" name="id" value="${from.id!}" id="id"/>
                <div class="form-group">
                    <label >角色名:</label>
                    <input type="text" class="form-control" id="role" name="role" value="${from.role!}" placeholder="请输入用户名">
                </div>
                <div class="form-group">
                    <label for="name">描述:</label>
                    <textarea class="form-control" rows="3" name="remark" id="remark">${from.remark!}</textarea>
                </div>
            </form>
            <div class="form-group">
                <label>菜单：</label>
                <button class="btn btn-default" data-toggle="modal" data-target="#myModal">
                    选择菜单
                </button>
            </div>

            <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">提交</button>
            <button type="button" class="btn btn-default" onclick="breakIndex()" id="submitButton">返回</button>
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
            url = basePath + "/SystemController/insertRole"
        else
            url = basePath + "/SystemController/updateRole"
        var role=$("#role").val();

        if(role==''){
            $.alert("角色名不能为空！");
            return ;
        }
        var param = {
            'id':id,
            'role':role,
            'menus':sys_menus,
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
                    window.location.href = basePath + "/SystemController/roleIndex";
                }
            }
        });
    }
    var sys_menus = new Array();//用于提交表单的菜单ID数组
    var dataTree = new Array();//用于生成tree的数据
    var dbMenus = eval('(' + '${menus}' + ')');//查询出全部的菜单
    var currentMenus = eval('(' + '${currentMenus}' + ')');//当前角色对应的权限菜单
    function formatTreeData(dataTree , parentId){
        if(typeof(dbMenus.length)!='undefined'){//说明数组中有值
            var index = 0;
            for(var i=0;i<dbMenus.length;i++) {
                var firstNode = {};
                var menu = dbMenus[i];
                if (menu.parentId == parentId) {
                    firstNode["text"] = menu.menuName;
                    firstNode["value"] = menu.id;
                    var flag = false;
                    if (typeof (currentMenus.length) != 'undefined') {
                        for (var j = 0; j < currentMenus.length; j++) {
                            if (currentMenus[j].menuId == menu.id) {
                                flag = true;
                            }
                        }
                    }
                    if (flag) {
                        firstNode["state"] = {"checked": true};
                        sys_menus.push(menu.id);
                    }
                    if (typeof (menu.url) == 'undefined' || menu.url == null || menu.url == '') {//则说明是个目录,可能有子级
                        formatTreeData2(firstNode, menu.id);
                    }

                    dataTree[index] = firstNode;
                    index++;
                }
            }
        }
    }

    function formatTreeData2(firstNode,parentId){
        var nodes = new Array();
        for (var i = 0; i < dbMenus.length; i++) {
            var menu = dbMenus[i];
            if(parentId == menu.parentId){
                var nextNode = {};
                nextNode["text"] = menu.menuName;
                nextNode["value"] = menu.id;
                var flag = false;
                if(typeof (currentMenus.length) != 'undefined'){
                    for(var j=0;j<currentMenus.length;j++){
                        if(currentMenus[j].menuId ==menu.id){
                            flag = true;
                        }
                    }
                }
                if(flag){
                    nextNode["state"] = {"checked":true};
                    sys_menus.push(menu.id);
                }
                if(typeof (menu.url)=='undefined' || menu.url == null || menu.url == '') {//则说明是个目录,可能有子级
                    formatTreeData2(nextNode,menu.id);
                }
                nodes.push(nextNode);
                firstNode["nodes"] = nodes;
            }
        }
    }


    function getTree() {
        formatTreeData(dataTree,-1);
        return dataTree;
    }

    $('#tree').treeview(
        {
            data: getTree(),
            showCheckbox:true
        }
    );
    //选中的触发事件
    $('#tree').on('nodeChecked', function(event, data) {

        checkBox_check_parent(data,1);
        var flag = false;
        for(var i=0;i<sys_menus.length;i++){
            if(sys_menus[i]==data.value){
                flag = true;
            }
        }
        if(!flag){
            sys_menus.push(data.value);
        }
        checkBox_check(data,1);

        
    });
    //取消选中的触发事件
    $('#tree').on('nodeUnchecked', function(event, data) {
        checkBox_check_parent(data,2);
        var temp = new Array();
        for(var i=0;i<sys_menus.length;i++){
            if(sys_menus[i] == data.value){
                continue;
            }
            temp.push(sys_menus[i]);
        }
        sys_menus = temp;
        checkBox_check(data,2);
    });

    //全选与取消时操作sys_menu的数据
    function checkBox_check(data,num) {
        if(num ==1){
            if(typeof(data.nodes)!='undefined'){
                for(var i=0;i<data.nodes.length;i++){
                    $('#tree').treeview('checkNode', [ data.nodes[i].nodeId, { silent: true } ]);
                    var flag = false;
                    for(var j=0;j<sys_menus.length;j++){
                        if(sys_menus[j]==data.nodes[i].value){
                            flag = true;
                        }
                    }
                    if(!flag){
                        sys_menus.push(data.nodes[i].value);
                    }
                    checkBox_check(data.nodes[i],num);
                }
            }else{
                return;
            }
        }else{
            if(typeof(data.nodes)!='undefined'){
                for(var i=0;i<data.nodes.length;i++) {
                    $('#tree').treeview('uncheckNode', [data.nodes[i].nodeId, {silent: true}]);
                    var temp = new Array();
                    for(var j=0;j<sys_menus.length;j++){
                        if(sys_menus[j] == data.nodes[i].value){
                            continue;
                        }
                        temp.push(sys_menus[j]);
                    }
                    sys_menus = temp;
                    checkBox_check(data.nodes[i],num);
                }

            }else{
                return;
            }
        }
    }

    function checkBox_check_parent(data,num) {
        if(num ==1){
            if(typeof (data.parentId) != 'undefined' ){
                $('#tree').treeview('checkNode', [ data.parentId, { silent: true } ]);
                var parentNode = $('#tree').treeview('getNode', data.parentId);
                var flag = false;
                for(var i=0;i<sys_menus.length;i++){
                    if(sys_menus[i]==parentNode.value){
                        flag = true;
                    }
                }
                if(!flag){
                    sys_menus.push(parentNode.value);
                }
                checkBox_check_parent(parentNode,num);
            }
        }else{
            if(typeof (data.parentId) != 'undefined' ) {
                var parentNode = $('#tree').treeview('getNode', data.parentId);
                var flag = false;
                for(var i = 0;i<parentNode.nodes.length;i++){
                    var node = parentNode.nodes[i];
                    if(node.state.checked){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    $('#tree').treeview('uncheckNode', [data.parentId, {silent: true}]);
                    var parentNode = $('#tree').treeview('getNode', data.parentId);
                    var temp = new Array();
                    for(var i=0;i<sys_menus.length;i++){
                        if(sys_menus[i] == parentNode.value){
                            continue;
                        }
                        temp.push(sys_menus[i]);
                    }
                    sys_menus = temp;
                }
                checkBox_check_parent(parentNode,num);
            }
        }
    }

    function breakIndex(){
        window.location.href = basePath + "/SystemController/roleIndex";
    }
</script>
</body>
</html>
