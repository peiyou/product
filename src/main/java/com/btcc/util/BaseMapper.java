package com.btcc.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by peiyou on 10/19/16.
 */
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
