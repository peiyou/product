<#assign basePath="${request.getContextPath()}">
<#assign current_url = "${request.getRequestUri()!?replace(basePath,'')}">
<#setting number_format="#.########">
<script type="text/javascript">
    var basePath = "${basePath}";
</script>



<!-- 分页的宏 -->
<#macro pageHandle pageinfo>
    <div class="div-right cf">
        <div class="input-1">
            共：
            <span id="pages">${pageinfo.pages}</span>
            页，
            当前第
            <input type="text" id="currentPageNum" value="${pageinfo.pageNum}" onblur="changeCurrentPageNum()"/>
            页，
        </div>
        <div class="input-2">
            每页显示
            <input type="text" id="currentPageSize" value="${pageinfo.pageSize}" onblur="changeCurrentPageSize()"/>
            条
        </div>
    </div>
    <div class="div-left">
        <ul class="pagination" id="pagination">
            <#if pageinfo.pages &gt; 10 >
                <#if pageinfo.pageNum != 1>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.prePage},${pageinfo.pageSize})">上一页</a></li>
                </#if>
                <!--页码太多时 -->
                <#if pageinfo.pageNum - 3 &gt; 1> <!--前面大于3个页码-->
                    <li><a href="javascript:void(0);" onclick="page(1,${pageinfo.pageSize})">1</a></li>
                    <li><a href="javascript:void(0);">...</a></li>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.pageNum - 2 },${pageinfo.pageSize})">${pageinfo.pageNum - 2}</a></li>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.pageNum - 1 },${pageinfo.pageSize})">${pageinfo.pageNum - 1}</a></li>
                    <li class="active"><a href="javascript:void(0);">${pageinfo.pageNum}</a></li>
                <#else>
                    <#if pageinfo.pageNum-1 &gt;= 1 >
                        <#list 1..pageinfo.pageNum -1 as i>
                            <li><a href="javascript:void(0);" onclick="page(${i},${pageinfo.pageSize})">${i}</a></li>
                        </#list>
                    </#if>
                        <li class="active"><a href="javascript:void(0);">${pageinfo.pageNum}</a></li>
                </#if>

                <!-- 后面页码 -->
                <#if pageinfo.pageNum + 3 &lt; pageinfo.pages >
                     <li><a href="javascript:void(0);" onclick="page(${pageinfo.pageNum + 1 },${pageinfo.pageSize})">${pageinfo.pageNum + 1 }</a></li>
                     <li><a href="javascript:void(0);" onclick="page(${pageinfo.pageNum + 2 },${pageinfo.pageSize})">${pageinfo.pageNum + 2 }</a></li>
                     <li><a href="javascript:void(0);">...</a></li>
                     <li><a href="javascript:void(0);" onclick="page(${pageinfo.pages},${pageinfo.pageSize})">${pageinfo.pages}</a></li>
                <#else>
                    <#if pageinfo.pageNum != pageinfo.pages>
                        <#list (pageinfo.pageNum+1)..pageinfo.pages as i>
                            <li><a href="javascript:void(0);" onclick="page(${i},${pageinfo.pageSize})">${i}</a></li>
                        </#list>
                    </#if>
                </#if>

                <#if pageinfo.pageNum != pageinfo.pages>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.nextPage},${pageinfo.pageSize})">下一页</a></li>
                </#if>
            <#elseif pageinfo.pages &gt; 1><!-- 大于1页，小于等于10页时 -->
                <#if pageinfo.pageNum != 1>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.prePage},${pageinfo.pageSize})">上一页</a></li>
                </#if>

                <#list 1..pageinfo.pages as i>
                    <#if pageinfo.pageNum == i>
                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                    <#else >
                        <li><a href="javascript:void(0);" onclick="page(${i},${pageinfo.pageSize})">${i}</a></li>
                    </#if>
                </#list>

                <#if pageinfo.pageNum != pageinfo.pages>
                    <li><a href="javascript:void(0);" onclick="page(${pageinfo.nextPage},${pageinfo.pageSize})">下一页</a></li>
                </#if>
            <#elseif pageinfo.pages == 1> <!-- 等于1页时 -->
                <li class="active"><a href="javascript:void(0);" >1</a></li>
            </#if>
        </ul>
    </div>
</#macro>


<#macro th name>
    <th>${name!}</th>
</#macro>

<#macro td name=''>
    <td>${name!}</td>
</#macro>