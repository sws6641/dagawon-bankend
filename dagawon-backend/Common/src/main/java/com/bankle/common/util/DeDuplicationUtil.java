package com.bankle.common.util;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 리스트의 특정 키값으로 중복 제거를 진행한다.
 *
 * @author sh.lee
 * @date 2023-11-30
 **/
public class DeDuplicationUtil {

    public static <T> List<T> deduplication(final List<T> list, Function<? super T, ?> key) {
        return list.stream().filter(deduplication(key))
                .collect(Collectors.toList());
    }

    private static <T> Predicate<T> deduplication(Function<? super T, ?> key) {
        final Set<Object> set = ConcurrentHashMap.newKeySet();
        return predicate -> set.add(key.apply(predicate));
    }
}
