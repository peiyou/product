package com.btcc.web.report.website.service;

import com.btcc.constant.ReportConstant;
import com.btcc.util.DateUtils;
import com.btcc.util.MathUtils;
import com.btcc.web.report.website.mapper.PlusInfoMapper;
import com.btcc.web.report.website.mapper.RegisterUserMapper;
import com.btcc.web.report.website.mapper.WebSiteUserBalanceMapper;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * Created by peiyou on 2017/8/16.
 */
@Service
public class PlusInfoService {

    private Logger logger = LoggerFactory.getLogger(PlusInfoService.class);
    @Autowired
    private PlusInfoMapper plusInfoMapper;

    @Autowired
    private RegisterUserMapper registerUserMapper;

    @Autowired
    private WebSiteUserBalanceMapper webSiteUserBalanceMapper;

    @Resource(name="bttxSqlSessionFactory")
    SqlSessionFactory bttxSqlSessionFactory;

    public List<Map<String,Object>> getLastSundryCoinOfPrice(Map<String,Object> map){
        return plusInfoMapper.getLastSundryCoinOfPrice(map);
    }

    public void insertUserBalance(Map<String,Object> param){
        SqlSession sqlSession = bttxSqlSessionFactory.openSession();
        //得修改
        Cursor<Map<String,Object>> cursor =  sqlSession.selectCursor("getUserBalance",param);
        Iterator<Map<String,Object>> iterator = cursor.iterator();
        List<Map<String,Object>> result = new ArrayList<>();
        while (iterator.hasNext()){
            Map<String,Object> map = iterator.next();
            if(!MathUtils.isNumeric(map.get("user_id").toString())) continue;
            map.put("type",4);
            map.put("dateline", DateUtils.dateFormat(new Date(),DateUtils.DATE_SMALL_STR));
            result.add(map);
            if(result.size() >= ReportConstant.rowNum){
                webSiteUserBalanceMapper.insertWebSiteUserBalance(result);
                result.clear();
            }
        }
        if (result.size() > 0) {
            webSiteUserBalanceMapper.insertWebSiteUserBalance(result);
            result.clear();
        }

        try {
            cursor.close();
        }catch (IOException e){
            logger.error("",e);
        }


    }

    public List<Map<String,Object>> getPlusPlatformOfStock(Map<String,Object> map){
        return plusInfoMapper.getPlusPlatformOfStock(map);
    }

    public Integer getRegisterCountUserId(Map<String,Object> param){
        return registerUserMapper.getRegisterCountUserId(param);
    }
}
