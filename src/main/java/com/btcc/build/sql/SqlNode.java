package com.btcc.build.sql;

import java.util.Map;

/**
 * Created by peiyou on 2016/11/18.
 */
public interface SqlNode {

    void apply(SqlContext sqlContext);

    int getEndIndex();
}
