package kr.co.anbu.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//@Slf4j
public class JsonCustUtils {

    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> toMap(JSONObject object) {
        
        HashMap<String, Object> map     = new HashMap<String, Object>();
        Iterator<String>        keysItr = object.keySet().iterator();
        
        while(keysItr.hasNext()) {
            String key   = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
    
    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
 
            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> toLMap(JSONObject object) {
        
        LinkedHashMap<String, Object> map     = new LinkedHashMap<String, Object>();
        Iterator<String>              keysItr = object.keySet().iterator();
        
        while(keysItr.hasNext()) {
            String key   = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }    
    
    public static List<Object> toLList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
 
            else if(value instanceof JSONObject) {
                value = toLMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }    
    
}

