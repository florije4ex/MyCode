package com.cui.code.test;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存测试
 *
 * @author cuishixiang
 * @date 2018-10-22
 */
public class LoadingCacheTest {


    LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build(key -> {
                if (key.equals("null")) {
                    return null;
                } else {
                    return new Object();
                }
            });

    LoadingCache<String, Optional<Object>> loadingCache2 = Caffeine.newBuilder()
            .maximumSize(10)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> {
                if (key.equals("null")) {
                    return Optional.ofNullable(null);
                } else {
                    return Optional.of(new Object());
                }
            });

    @Test
    public void testCache() {
        for (int i = 0; i < 100; i++) {
            Object obj = loadingCache.get(String.valueOf(i));
            System.out.println(obj);
        }

        Object aNull = loadingCache.get("null");
        System.out.println(aNull);

        // 返回null时，不会被缓存起来，每次都会进入build
        Object bNull = loadingCache.get("null");
        System.out.println(bNull);

        Object o5 = loadingCache.get("5");
        System.out.println(o5);
    }

    @Test
    public void testCache2() {
        for (int i = 0; i < 100; i++) {
            Object obj = loadingCache2.get(String.valueOf(i));
            System.out.println(obj);
        }

        Object aNull = loadingCache2.get("null");
        System.out.println(aNull);

        // 返回null时，不会被缓存起来，每次都会进入build
        Object bNull = loadingCache2.get("null");
        System.out.println(bNull);

        Object o5 = loadingCache2.get("5");
        System.out.println(o5);
    }


}
