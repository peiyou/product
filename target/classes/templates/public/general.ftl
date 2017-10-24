<!-- 自动生成form表单 -->
<!--
    params参数表示具体的输入框的个数
    from 表示上次查询的参数值

 -->
<#macro paramBear params from isDownload=0>
    <#if params?size % 2 == 0>
        <#list params as param>
            <#if param_index % 2 == 0><#--下标从0开始，所以为偶数个时，index是奇数个 -->
                <div class="input-group">
                <@paramTypes param from />
            <#else>
                <@paramTypes param from />
                </div>
            </#if>
        </#list>
        <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">查询</button>
        <#if isDownload==1>
            <button type="button" class="btn btn-default" onclick="downloadExcel()" id="submitButton">excel下载</button>
        </#if>
    <#else>
        <#list params as param>
            <#if param_index % 2 == 0><#--下标从0开始，所以为偶数个时，index是奇数个 -->
                <#if param_index == params?size - 1>
                    <div class="input-group">
                        <@paramTypes param from />
                        <button type="button" class="btn btn-default" onclick="onSubmit()" id="submitButton">查询</button>
                    <#if isDownload==1>
                        <button type="button" class="btn btn-default" onclick="downloadExcel()" id="submitButton">excel下载</button>
                    </#if>
                    <div>
                <#else>
                    <div class="input-group">
                        <@paramTypes param from />
                </#if>
            <#else>
                    <@paramTypes param from />
                </div>
            </#if>
        </#list>
    </#if>
</#macro>


<!-- 表单条件输入的类型，当前只支持date类型与text类型，后续有新加入的再写 -->
<#macro paramTypes param from>
    <#if param.type=='date'>
        <label for="${param.name!}">${param.label!}</label>
        <input type="text" name="${param.name!}" class="datetimepicker form-inline"
               id="${param.name!}" placeholder="${param.label!}" <#if from['${param.name}']??>value="${from['${param.name}']!param.default}" <#else>value="${param.default!}"</#if> <#if param.requisite?? && param.requisite=='yes'>required="required"</#if> >
    <#else>
        <label for="${param.name!}">${param.label!}</label>
        <input type="text" name="${param.name!}" class="form-inline"
               id="${param.name!}" placeholder="${param.label!}" <#if from['${param.name}']??>value="${from['${param.name}']!param.default}" <#else>value="${param.default!}"</#if> <#if param.requisite?? && param.requisite=='yes'>required="required"</#if>>
    </#if>
</#macro>


<!--
    生成th的个数
-->
<#macro tableHead tableHeads>
    <tr>
    <#list tableHeads as th>
            <th>${th?html}</th>
    </#list>
    </tr>
</#macro>

<!--
 生成td
 contexts 表示查询出来的结果集显示
 tableBodys 表示th对应的字段是哪个
 -->
<#macro tableBody contexts tableBodys>
    <#list contexts as c>
        <tr>
            <#list tableBodys as b>
                <#if b?ends_with('_string')>
                    <td title="${c[b]!?string?html}">${c[b]!?string?html}</td>
                <#elseif b?ends_with('_int')>
                    <td title="${c[b]!?string(',###.########')?html}">${c[b]!?string(',###.########')?html}</td>
                <#else>
                    <td title="${c[b]!?string?html}">${c[b]!?string?html}</td>
                </#if>
            </#list>
        </tr>
    </#list>
</#macro>