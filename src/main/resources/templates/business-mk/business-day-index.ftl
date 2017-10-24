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

    <title>交易数据</title>

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
            <h1 class="page-header">查询</h1>
            <form id="dateForm" role="form" class="form-inline" action="${basePath}/reportController/businessReportIndex"
                method="post">
                <input type="hidden" name="pageNum" id="pageNum" value="${form.pageNum}" />
                <input type="hidden" name="pageSize" id="pageSize" value="${form.pageSize}" />
                <div class="form-group">
                    <label for="beginDate">开始时间:</label>
                    <input type="text" name="startDate" class="datetimepicker form-control"
                           id="beginDate" placeholder="Begin Date" value="${form.startDate}">
                    <label for="endDate">结束时间:</label>
                    <input type="date" name="endDate" class="datetimepicker form-control"
                           id="endDate" placeholder="End Date" value="${form.endDate}">
                    <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">查询</button>
                </div>
            </form>
            <h2 class="sub-header">数据列表</h2>
            <div class="table-responsive">
                <table class="table table-300 table-bordered">
                    <thead>
                    <tr>
                        <@th name="日期"/>
                        <@th name="新注册<br/>用户数"/>
                        <@th name="注册当天<br/>充值用户<br/>数"/>
                        <@th name="充值比特币<br/>人数"/>
                        <@th name="充值比特币<br>次数"/>
                        <@th name="充值比特币<br/>总量（฿）"/>
                        <@th name="提现比特币<br/>人数"/>
                        <@th name="提现比特币<br/>次数"/>
                        <@th name="提现比特币<br/>总量（฿）"/>
                        <@th name="提现比特币<br/>手续费（฿）"/>
                        <@th name="充值人民币<br/>人数"/>
                        <@th name="充值人民币<br/>次数"/>
                        <@th name="充值人民币<br/>总量（元）"/>
                        <@th name="提现人民币<br>用户数"/>
                        <@th name="提现人民币<br>次数"/>
                        <@th name="提现人民币<br>总量（元）"/>
                        <@th name="提现人民币<br>手续费（元）"/>
                        <@th name="市场深度"/>
                        <@th name="交易用户数"/>
                        <@th name="交易量"/>
                        <@th name="api交易<br>量"/>
                        <@th name="api交易<br>人数"/>
                        <@th name="借贷人民币<br>用户数"/>
                        <@th name="借贷人民币<br>量(元)"/>
                        <@th name="借货比特币<br>用户数"/>
                        <@th name="借贷比特币<br>总量"/>
                        <@th name="人民币借贷利息<br>（已收取）"/>
                        <@th name="比特币借贷利息<br>（已收取）"/>
                    </tr>
                    </thead>
                    <tbody id="dataBody">
                        <#list pageinfo.list as p>
                            <tr>
                                <@td name=p.statisDate/>
                                <@td name=''/>
                                <@td name=''/>
                                <@td name=p.fundbtcUsers/>
                                <@td name=p.fundbtcCount/>
                                <@td name=p.fundbtcAmount/>
                                <@td name=p.withdrawbtcUsers/>
                                <@td name=p.withdrawbtcCount/>
                                <@td name=p.withdrawbtcAmount/>
                                <@td name=p.withdrawbtcFee/>
                                <@td name=p.fundcnyUsers/>
                                <@td name=p.fundcnyCount/>
                                <@td name=p.fundcnyAmount/>
                                <@td name=p.withdrawcnyUsers/>
                                <@td name=p.withdrawcnyCount/>
                                <@td name=p.withdrawcnyAmount/>
                                <@td name=p.withdrawcnyFee/>
                                <@td name=''/>
                                <@td name=p.tradeUsers/>
                                <@td name=p.tradeAmount/>
                                <@td name=p.apiTradeAmount/>
                                <@td name=p.apiTradeUsers/>
                                <@td name=p.borrowedCnyUsers/>
                                <@td name=p.borrowedCnyAmount/>
                                <@td name=p.borrowedBtcUsers/>
                                <@td name=p.borrowedBtcAmount/>
                                <@td name=p.borrowedCnyInterestCollected/>
                                <@td name=p.borrowedBtcInterestCollected/>
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

        //dataAjax();
    });

    function onSubmit(){
        $("#dateForm").submit();
    }
</script>
</body>
</html>
