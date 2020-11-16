package com.overrideeg.apps.opass.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtils {


    @SafeVarargs
    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }


    public static <T> boolean listEqualsNoOrder ( List<T> list1, List<T> list2 ) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

//    public static <T> boolean areUnique(final List<T> list) {
//        final Set<T> unRepeats = new HashSet<>();
//        return list.stream().allMatch(unRepeats::add);
//    }

    @SafeVarargs
    public static <T> boolean areUnique ( final List<T> list, Function<? super T, ?>... keyExtractors ) {

        List<T> collect = list.stream().filter(distinctByKeys(keyExtractors)).collect(Collectors.toList());

        return collect.size() == list.size();
    }


    public static <T> Predicate<T> distinctByKey ( Function<? super T, ?> keyExtractor ) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


}
