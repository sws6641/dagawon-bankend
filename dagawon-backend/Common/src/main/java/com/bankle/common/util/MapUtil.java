package com.bankle.common.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * MapUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
public class MapUtil {

    private MapUtil() {
    }

    public static String getString(Map<?, ?> map, Object key) {
        return StringUtil.toString(map.get(key));
    }

    public static int getInt(Map<?, ?> map, Object key) {
        return NumberUtil.toInt(map.get(key));
    }

    public static double getDouble(Map<?, ?> map, Object key) {
        return NumberUtil.toDouble(map.get(key));
    }

    public static BigDecimal getBigDecimal(Map<?, ?> map, Object key) {
        return NumberUtil.toBigDecimal(map.get(key));
    }


}
