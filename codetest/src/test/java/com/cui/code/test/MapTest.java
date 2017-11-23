package com.cui.code.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Map测试
 * Created by cuishixiang on 2017-10-13.
 */
public class MapTest {

    @Test
    public void testHashMap() {
        HashMap<Integer, String> map = new HashMap<>();

        String value1 = map.put(1234, "test");
        String value2 = map.put(1234, "测试观察");
        String value3 = map.put(1234, "乱码");
        System.out.println(value1);
        System.out.println(value2);
        System.out.println(value3);

    }


    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }

    }

}
