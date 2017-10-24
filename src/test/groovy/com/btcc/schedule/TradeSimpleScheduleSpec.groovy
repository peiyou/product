package com.btcc.schedule

import com.btcc.AppContextSpec
import spock.lang.Shared

/**
 * Created by peiyou on 17/1/19.
 */
class TradeSimpleScheduleSpec extends AppContextSpec {
    @Shared
    TradeSimpleSchedule tradeSimpleSchedule

    def "test tradeSimple batch insert data !" (){
        given:
            tradeSimpleSchedule = context.getBean(TradeSimpleSchedule.class)
            String obj = "{\"startDate\":\"2011-01-01\",\"endDate\":\"2017-01-01\"}";
        when:
            def obj_ = tradeSimpleSchedule.historyTradeSimple(obj);
        then:
            println(obj_)
            1==1
    }
}
