package com.btcc.web.report.website.mapper;

import com.btcc.datasource.BttxDataSource;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;
import java.util.Map;

/**
 * Created by peiyou on 2017/8/15.
 */
@BttxDataSource
public interface PlusInfoMapper {

    /**
     * 获取时间内的各币种最后成交价格
     * @param map
     * @return
     */
    public List<Map<String,Object>> getLastSundryCoinOfPrice(Map<String,Object> map);

    /**
     * 获取用户的余额
     * 得到每个用户的余额
     * @param map
     * @return
     */
    public Cursor<Map<String,Object>> getUserBalance(Map<String,Object> map);

    /**
     * 获取plus平台各币种的存量
     * @param map
     * @return
     */
    public List<Map<String,Object>> getPlusPlatformOfStock(Map<String,Object> map);


}
