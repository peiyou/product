function page(pageNum,pageSize){
    $("#pageNum").val(pageNum);
    $("#pageSize").val(pageSize);
    $("#submitButton").click();
}


function pageHtml(pageinfo){
    var pages = pageinfo.pages;//一共多少页
    var pageNum = pageinfo.pageNum;//当前页
    var html = '';
    if(pages > 10){
        if(pageNum != 1){
            html += '<li><a href="javascript:void(0);" onclick="page('+pageinfo.prePage+','+pageinfo.pageSize+')">上一页</a></li>';
        }
        //页码太多时
        if(pageNum - 3 > 1){//前面大于3个页码
            html += '<li><a href="javascript:void(0);" onclick="page(1,'+pageinfo.pageSize+')">1</a></li>';
            html += '<li><a href="javascript:void(0);">...</a></li>';
            html += '<li><a href="javascript:void(0);" onclick="page('+(pageNum-2)+','+pageinfo.pageSize+')">'+(pageNum-2)+'</a></li>';
            html += '<li><a href="javascript:void(0);" onclick="page('+(pageNum-1)+','+pageinfo.pageSize+')">'+(pageNum-1)+'</a></li>';
            html += '<li class="active"><a href="javascript:void(0);">'+pageNum+'</a></li>';
        }else{
            for(var i=1;i<pageNum;i++){
                html += '<li><a href="javascript:void(0);" onclick="page('+i+','+pageinfo.pageSize+')">'+i+'</a></li>';
            }
            html += '<li class="active"><a href="javascript:void(0);">'+pageNum+'</a></li>';
        }

        //后面页码
        if(pageNum + 3 < pages){
            html += '<li><a href="javascript:void(0);" onclick="page('+(pageNum+1)+','+pageinfo.pageSize+')">'+(pageNum+1)+'</a></li>';
            html += '<li><a href="javascript:void(0);" onclick="page('+(pageNum+2)+','+pageinfo.pageSize+')">'+(pageNum+2)+'</a></li>';
            html += '<li><a href="javascript:void(0);">...</a></li>';
            html += '<li><a href="javascript:void(0);" onclick="page('+pageinfo.pages+','+pageinfo.pageSize+')">'+pageinfo.pages+'</a></li>';
        }else{
            for(var i=pageNum + 1;i<=pages;i++){
                html += '<li><a href="javascript:void(0);" onclick="page('+i+','+pageinfo.pageSize+')">'+i+'</a></li>';
            }
        }

        if(pageNum != pages){
            html += '<li><a href="javascript:void(0);" onclick="page('+pageinfo.nextPage+','+pageinfo.pageSize+')">下一页</a></li>';
        }
    }else if(pages > 1){//大于1页，小于等于7页的时候
        if(pageNum != 1){
            html += '<li><a href="javascript:void(0);" onclick="page('+pageinfo.prePage+','+pageinfo.pageSize+')">上一页</a></li>';
        }
        for (var i=1;i<=pages;i++){
            if(pageNum == i){
                html += '<li class="active"><a href="javascript:void(0);">'+i+'</a></li>';
            }else{
                html += '<li><a href="javascript:void(0);" onclick="page('+i+','+pageinfo.pageSize+')">'+i+'</a></li>';
            }
        }
        if(pageNum != pages){
            html += '<li><a href="javascript:void(0);" onclick="page('+pageinfo.nextPage+','+pageinfo.pageSize+')">下一页</a></li>';
        }


    }else if(pages == 1){//只有1页或没有
        html += '<li class="active"><a href="javascript:void(0);" >1</a></li>';
    }
    $("#pages").text(pages);
    $("#currentPageSize").val(pageinfo.pageSize);
    $("#currentPageNum").val(pageNum);
    $("#pagination").html(html);
}

/**
 * 修改每页显示N条
 */
function changeCurrentPageSize(){
    var currentPageSize = $("#pageSize").val();
    var chagePageSize = $("#currentPageSize").val();
    if(currentPageSize == chagePageSize){
        return;
    }
    var reg = new RegExp("^[0-9]*$");
    if(!reg.test(chagePageSize)){
        alert("请输入数字!");
        $("#currentPageSize").val(currentPageSize);
        return;
    }
    if(chagePageSize <= 0){
        alert("请输入大于0的数！");
        $("#currentPageSize").val(currentPageSize);
        return;
    }
    page(1,chagePageSize);
}


function changeCurrentPageNum(){
    var currentPageNum = $("#pageNum").val();
    var changePageNum = $("#currentPageNum").val();
    if(currentPageNum == changePageNum){
        return ;
    }
    var reg = new RegExp("^[0-9]*$");
    if(!reg.test(changePageNum)){
        alert("请输入数字!");
        $("#currentPageNum").val(currentPageNum);
        return;
    }
    if(changePageNum <= 0){
        alert("请输入大于0的数！");
        $("#currentPageNum").val(currentPageNum);
        return;
    }
    page(changePageNum,$("#pageSize").val());
}