package com.bankle.common.asis.component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bankle.common.util.MapUtil;
import lombok.extern.slf4j.Slf4j;


/*
 * Object that hold all parameters from query string, form, and login session
 */
@Slf4j
public class Box {

    protected Map<String, Object> map = new HashMap<String, Object>();

    public Box(){

    }

    public Box(Map<? extends String, ? extends Object> m){
        map.putAll(m);
    }

    public Object get(String key){
        return map.get(key);
    }

    public void put(String key, Object value){
        map.put(key, value);
    }

    public Object remove(String key){
        return map.remove(key);
    }

    public boolean containsKey(String key){
        return map.containsKey(key);
    }

    public boolean containsValue(Object value){
        return map.containsValue(value);
    }

    public void clear(){
        map.clear();
    }

    public Set<Entry<String, Object>> entrySet(){
        return map.entrySet();
    }

    public Set<String> keySet(){
        return map.keySet();
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public void putAll(Map<? extends String, ?extends Object> m){
        map.putAll(m);
    }

    public Map<String,Object> getMap(){
        return map;
    }

    public String getString(String key) {

        return (String)map.get(key);
    }

    public int getInt(String key) {
        return MapUtil.getInt(map, key);
    }

    public double getDouble(String key) {
        
        return MapUtil.getDouble(map, key);
    }

    public BigDecimal getBigDecimal(String key) {
        
        return MapUtil.getBigDecimal(map, key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=').append('"');
            sb.append(entry.getValue());
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();

    }
}
