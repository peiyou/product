<!-- 菜单处（The Menu）-->
<#--<div class="col-sm-3 col-md-2 sidebar">
    <ul class="nav nav-sidebar">
        &lt;#&ndash;<li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>&ndash;&gt;
        <#list CURRENT_LOGIN_USER.menus as menu>
            <li <#if current_url==menu.url>class="active"</#if>><a href="${basePath}${menu.url}">${menu.menuName}</a></li>
        </#list>
    </ul>
</div>-->
<#macro nav_ul package parentId>
    <#list package as p >
        <#if p.parentId == parentId>
            <#if !p.url?? || p.url == '' ><!-- 是个目录，可能有下一级-->
                <a href="#dashboard-menu${p.id}" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>${p.menuName}</a>
                <ul id="dashboard-menu${p.id}" class="nav nav-list collapse">
                    <@nav_li package p.id />
                </ul>
            <#else><!-- 是个url地址，说明没有下一级 -->
                <a href="${basePath}${p.url}" class="nav-header color51cac5" ><i class="icon-question-sign"></i>${p.menuName}</a>
            </#if>
        </#if>

    </#list>
</#macro>

<#macro nav_li package parentId>
     <#list package as p>
        <#if p.parentId == parentId>
            <#if !p.url?? || p.url == ''>
                <a href="#dashboard-menu${p.id}" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>${p.menuName}</a>
                <ul id="dashboard-menu${p.id}" class="nav nav-list collapse" style="height: 0px;">
                    <@nav_li package p.id />
                </ul>
            <#else>
                <li><a href="${basePath}${p.url}">${p.menuName}</a></li>
            </#if>
        </#if>
     </#list>
</#macro>


<div class="sidebar-nav">
    <#--<a href="#dashboard-menu" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>Dashboard</a>
    <ul id="dashboard-menu" class="nav nav-list collapse in">
        <li><a href="index.html">Home</a></li>
        <li ><a href="users.html">Sample List</a></li>
        <li ><a href="user.html">Sample Item</a></li>
        <li ><a href="media.html">Media</a></li>
        <li ><a href="calendar.html">Calendar</a></li>
        <a href="#num-test" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>test</a>
        <ul id="num-test" class="nav nav-list collapse in">
            <li><a href="index.html">Home</a></li>
            <li ><a href="users.html">Sample List</a></li>
        </ul>
    </ul>

    <a href="#accounts-menu" class="nav-header" data-toggle="collapse"><i class="icon-briefcase"></i>Account<span class="label label-info">+3</span></a>
    <ul id="accounts-menu" class="nav nav-list collapse">
        <li ><a href="sign-in.html">Sign In</a></li>
        <li ><a href="sign-up.html">Sign Up</a></li>
        <li ><a href="reset-password.html">Reset Password</a></li>
    </ul>

    <a href="#error-menu" class="nav-header collapsed" data-toggle="collapse"><i class="icon-exclamation-sign"></i>Error Pages <i class="icon-chevron-up"></i></a>
    <ul id="error-menu" class="nav nav-list collapse">
        <li ><a href="403.html">403 page</a></li>
        <li ><a href="404.html">404 page</a></li>
        <li ><a href="500.html">500 page</a></li>
        <li ><a href="503.html">503 page</a></li>
    </ul>

    <a href="#legal-menu" class="nav-header" data-toggle="collapse"><i class="icon-legal"></i>Legal</a>
    <ul id="legal-menu" class="nav nav-list collapse">
        <li ><a href="privacy-policy.html">Privacy Policy</a></li>
        <li ><a href="terms-and-conditions.html">Terms and Conditions</a></li>
    </ul>

    <a href="help.html" class="nav-header color51cac5" ><i class="icon-question-sign"></i>Help</a>
    <a href="faq.html" class="nav-header color51cac5" ><i class="icon-comment"></i>Faq</a>
    -->
    <@nav_ul package=CURRENT_LOGIN_USER.menus parentId=-1 />


</div>