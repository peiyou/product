package com.btcc.util

import com.btcc.AppContextSpec
import net.sf.json.JSONObject
import spock.lang.Specification

class MultipleFlowJsonUtilSpec extends AppContextSpec {
    def "test 流程执行sql"(){
        given:"json"
        def json2 = "{\n" +
                "    \"name\": \"2\",\n" +
                "    \"applicationParam\": {\n" +
                "        \"paramsName\": [\n" +
                "            \"startDate\",\n" +
                "            \"endDate\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"sqlFlow\": [\n" +
                "        {\n" +
                "            \"databaseName\": \"btcchina_mk\",\n" +
                "            \"type\": \"select\",\n" +
                "            \"sql\": \"SELECT from_unixtime(dateline, '%Y-%m-%d') as date, count(*) as fundbtcCount, sum(amount) as fundbtcAmount, count(DISTINCT user_id) as fundbtcUsers FROM blockchain_txn WHERE dateline >= UNIX_TIMESTAMP(#\\{startDate\\}) and dateline < UNIX_TIMESTAMP(#\\{endDate\\}) AND invalid IS NULL AND currency_type = 'btc' group by DATE ORDER  BY date asc\",\n" +
                "            \"next\": [\n" +
                "                {\n" +
                "                    \"databaseName\": \"report\",\n" +
                "                    \"type\": \"select\",\n" +
                "                    \"sql\": \"select count(1) as conditionCount from business_report_day where statis_date = unix_timestamp(#\\{date\\})\",\n" +
                "                    \"condition\": \"conditionCount > 0\",\n" +
                "                    \"next\": [\n" +
                "                        {\n" +
                "                            \"databaseName\": \"report\",\n" +
                "                            \"type\": \"update\",\n" +
                "                            \"sql\": \"update business_report_day set fundbtc_count=#\\{fundbtcCount\\}, fundbtc_Amount=#\\{fundbtcAmount\\},fundbtc_Users=#\\{fundbtcUsers\\} where statis_date = unix_timestamp(#\\{date\\})\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"databaseName\": \"report\",\n" +
                "                            \"type\": \"insert\",\n" +
                "                            \"sql\": \"insert into business_report_day(statis_date,fundbtc_count,fundbtc_Amount,fundbtc_Users)  values(unix_timestamp(#\\{date}),#\\{fundbtcCount\\},#\\{fundbtcAmount\\},#\\{fundbtcUsers\\} )\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"databaseName\": \"btcchina_mk\",\n" +
                "            \"type\": \"select\",\n" +
                "            \"sql\": \"SELECT         from_unixtime(dateline, '%Y-%m-%d') AS date,         count(*) AS withdrawbtcCount,         sum(amount) as withdrawbtcAmount,         sum(payfee) as withdrawbtcFee,         count(DISTINCT user_id) as withdrawbtcUsers         FROM withdrawbtc         WHERE dateline >= UNIX_TIMESTAMP(#\\{startDate\\}) and dateline < UNIX_TIMESTAMP(#\\{endDate\\})         AND status = 'success'         group by DATE         order BY  DATE asc\",\n" +
                "            \"next\": [\n" +
                "                {\n" +
                "                    \"databaseName\": \"report\",\n" +
                "                    \"type\": \"select\",\n" +
                "                    \"sql\": \"select count(1) as conditionCount from business_report_day where statis_date = unix_timestamp(#\\{date\\})\",\n" +
                "                    \"condition\": \"conditionCount > 0\",\n" +
                "                    \"next\": [\n" +
                "                        {\n" +
                "                            \"databaseName\": \"report\",\n" +
                "                            \"type\": \"update\",\n" +
                "                            \"sql\": \"update business_report_day set withdrawbtc_Count=#\\{withdrawbtcCount\\},withdrawbtc_Amount=#\\{withdrawbtcAmount\\},withdrawbtc_Fee=#\\{withdrawbtcFee\\},withdrawbtc_Users=#\\{withdrawbtcUsers\\} where statis_date = unix_timestamp(#\\{date\\}) \"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"databaseName\": \"report\",\n" +
                "                            \"type\": \"insert\",\n" +
                "                            \"sql\": \"insert into business_report_day(statis_date,withdrawbtc_Count,withdrawbtc_Amount,withdrawbtc_Fee,withdrawbtc_Users)  values(unix_timestamp(#\\{date\\}),#\\{withdrawbtcCount\\},#\\{withdrawbtcAmount\\},#\\{withdrawbtcFee\\},#\\{withdrawbtcUsers\\} )\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}"

        def jsonParams2 = "{\"startDate\":\"2011-01-01\",\"endDate\":\"2017-01-01\"}"
        def  json = "{\"name\":\"1\",\"sqlFlow\":[{\"databaseName\":\"report\",\"type\":\"select\",\"sql\":\"select count(1) as userNum,date_format(login_time,'%Y-%m-%d') as date  from login_log group by date\",\"resultType\":\"list\",\"next\":[{\"databaseName\":\"report\",\"type\":\"select\",\"sql\":\"select count(1) as result from login_log where login_time >= #\\{date\\} and login_time < DATE_ADD(#\\{date\\},INTERVAL 1 DAY)\",\"resultType\":\"int\",\"condition\":\"result > 0\",\"next\":[{\"databaseName\":\"report\",\"type\":\"insert\",\"sql\":\"insert into login_log(user_name,login_time) values('test',#\\{date\\})\"},{\"databaseName\":\"report\",\"type\":\"insert\",\"sql\":\"insert into login_log(user_name,login_time) values('test2',#\\{date\\})\"}]}]}]}";
        def  jsonParams = "{\"result\":1,\"date\":\"2017-01-01\"}";
        when:"执行"
        MultipleFlowJsonUtil.flowJsonSql(json2,JSONObject.fromObject(jsonParams2));
        then:"结束"
        1==1
    }

    def "测试从表中取出json数据并执行"(){
        given:"a"
        def workFlowSql = systemService.queryWorkFlowSqlById(2);
        def params = "{\"startDate\":\"2016-01-01\",\"endDate\":\"2016-02-01\"}";
        MultipleFlowJsonUtil.flowJsonSql(workFlowSql.data,JSONObject.fromObject(params))
        println("执行完成...")
        when:""

        then:""
        1==1
    }
}
