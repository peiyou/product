package com.btcc;

import com.btcc.util.ParameterUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peiyou on 2016/11/4.
 */
public class Parameter extends HashMap<String,Object> {

    public Parameter(){
        super();
    }

    public Parameter(Object obj){
        super();
        this.putAll(ParameterUtils.getParameter(obj));
    }

    public boolean setObject(Object obj){

        return true;
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        super.putAll(m);
    }

    @Override
    public Object remove(Object key) {
        return super.remove(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }
}
