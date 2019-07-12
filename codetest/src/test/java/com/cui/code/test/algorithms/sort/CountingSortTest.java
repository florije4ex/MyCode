package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 计数排序：非比较排序算法，就是需要额外的空间
 * 136ms，超级快，就是太耗内存了
 *
 * @author cuiswing
 * @date 2019-07-12
 */
public class CountingSortTest {

    int max_num = 10_000_000;

    public void countingSort(int[] arrays) {
        int[] result = new int[arrays.length];
        int[] count = new int[max_num];
        for (int array : arrays) {
            count[array] += 1;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arrays.length - 1; i >= 0; i--) {
            count[arrays[i]] --;
            result[count[arrays[i]]] = arrays[i];
        }
        arrays = result;
    }

    @Test
    public void testCountingSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, max_num).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        countingSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }

}
