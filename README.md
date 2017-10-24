Dashboard
====================
* 系统模块说明
* 1 任务调度

    * 参数项是JSON模式的文本 如 {"baseScheduleWorkFlowSqlId":3,"startDate":"2015-01-29","endDate":"2015-02-05"}
    此处的baseScheduleWorkFlowSqlId是系统属性，id号为流式SQL的主键ID，这个调度表示为调用ID为3的流式SQL。其中startDate和endDate是
    流式SQL需要的参数。调用流式SQL的类必需为com.btcc.web.base.schedule.BaseSchedule，方法baseScheduleForParams、baseScheduleForDateParams
    两个，方法baseScheduleForParams无返回值，入参除baseScheduleWorkFlowSqlId之外其它参数由流式SQL指定。
    baseScheduleForDateParams有返回值，其它功能与baseScheduleForParams一致。
    返回值说明：<br>
    只会对startDate与endDate参数名的参数(指时间，格式为yyyy-MM-dd)做处理（startDate=endDate+1,endDate=endDate+(endDate-startDate)）然后返回。    

* 2 用户
    * 用户由官网 http://www.btcchina.com 注册得到，在添加用户时，需要用户提供官网的登录用户名，此系统的登录调用官网的API，用户加入些系统需要做官网的二次
    google验证，如果没做，请系统管理员让其添加后再给其开放对应的权限。
* 3 角色
    * 用于给用户分配角色，一个用户可有个角色。
* 4 菜单
    * 功能的菜单
* 5 动态报表(#5)

    * 5.1 sql处，可以写简单的IF语句与FOR循环
        1、IF语句由#if[condition]  !#if 结束，不能进行IF语句的嵌套，但可与FOR循环做嵌套。
        
            语法1：
            
                select * from userdate 
                where 1 = 1 
                #if[user_id != null || user_id !='']
                    and user_id = #{user_id}
                !#if
                
            语法2：
            
                select * from userdate
                where 1 = 1
                #if[user_ids != null && user_ids != '']
                and user_id in 
                    #for[index=i|open=(|separator=,|item=it|close=)|collection=list]
                        #{it}
                    !#for
                !#if
                
                #if[type == 1]
                    and type = 1
                !#if
     此处的for的入参为一个list或map集合，因页面中无法写代码在此处用处不大。
     在参数处如果使用list类型时，系统会自动生成一个xxx_list（由xxx参数按逗号分割成一个数组再转成list）  其中xxx表示页面中的参数名，在使用参数list时，我们需要使用xxx_list。
     例：
            
            
            1、页面参数名user_ids  值  1,2,4,5,6,7,8  那么会生成一个list，list的参数名称为user_ids_list，值为{1,2,4,5,6,7,8}
            
            #for[index=i|open=(|separator=,|item=it|close=)|collection=user_ids_list]
                 #{it}
            !#for
   
   
   * 5.2 关于数字格式化：
           * 1、在[数据对应关系]输入框中，如果属性名是以[_int]结尾的，则会格式化成[,###.#######]的格式，如:1,200.98
              <br>如果整数位不足三位时，则正常的显示如：300.12
              <br>最多保留8位有效数字。
              <br>如果是以[_string]结尾，则是普通的字符串，不会进行格式化，默认就是以字符串的形式显示的。
              <br>如果要更新报表中的类型，则可以在/templates/public/general.ftl的文件中名称为tableBody的宏做修改即可。
* 6 流式SQL

    * 全局变量
        由调度中传入（如上述中的startDate与endDate就是由这个变量来指定的）。
    * sql类型（insert、select、update、delete）
    * sql的语法与动态报表中的一致。可参考[动态报表](#5)
    * 下级条件是指用当前sql的结果或其它变量做条件判断，如果不成立，下级的sql就不会在执行，流程就结束了。
    
    * 添加子级 如果sql是有子级时，会以当前sql的结果为参数，转入至子级中去，子级中可以用父级（或祖父级）的结果做为参数，兄弟级之间是没有关系的。
        `注意`只有select类型的sql才会有结果返回的。
