package com.btcc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/1.
 */
public class EntityPropertyUtils {
    private static Logger logger = LoggerFactory.getLogger(EntityPropertyUtils.class);
    public static Object getPropertyByMap(Map<String,Object> params,Object obj){
        if(params == null) return obj;
        Class cla = obj.getClass();
        for(String key : params.keySet()){
            if(key.equals("type")) continue; //此处的type与查询结果中的type不是同一个意思， 但是名字一样，所以这里先排除
            try {
                if(params.get(key)==null) continue;
                String methodName = getMethodName(key);
                Method[] methods = cla.getMethods();
                boolean flag = false;
                for(Method method:methods){
                    if(method.getName().equals("get"+methodName)){
                        flag = true;
                        break;
                    }
                }
                if(flag) {
                    Method getMethod = cla.getDeclaredMethod("get" + methodName);
                    if (getMethod == null) continue;
                    Class typeClass = getMethod.getReturnType();
                    Method setMethod = cla.getDeclaredMethod("set" + methodName, typeClass);
                    setMethod.invoke(obj, convertType(typeClass, params.get(key)));
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        return obj;
    }

    private static String getMethodName(String name) throws Exception {
        if(name != null && name.length() > 1){
            name = name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        }else{
            throw new Exception("map中的字段长度不够！请检查！");
        }
        return name;
    }

    private static Object convertType(Class typeClass,Object obj){
        if(typeClass.getName().equals(BigDecimal.class.getName())){
            return MathUtils.getBigDecimal(obj);
        }else if(typeClass.getName().equals(Integer.class.getName())){
            return MathUtils.getInteger(obj);
        }else if(typeClass.getName().equals(String.class.getName())){
            return MathUtils.getString(obj);
        }else if(typeClass.getName().equals(Double.class.getName())){
            return MathUtils.getDouble(obj);
        }
        return null;
    }

    /*public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("btcPayAmount",3212.33435444);
        BusinessReportDay day = new BusinessReportDay();
        getPropertyByMap(map,day);
        System.out.println(day.getBtcPayAmount());
    }*/
}
