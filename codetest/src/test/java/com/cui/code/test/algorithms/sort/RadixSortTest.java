package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 基数排序：每个位数排序算法必须是稳定的
 * 153ms
 *
 * @author cuiswing
 * @date 2019-07-12
 */
public class RadixSortTest {

    int base = 10;

    public void radixSort(int[] arrays) {
        for (int k = 1; k <= SortUtil.MAX_NUM / 10; k *= 10) {
            int[] temp = new int[arrays.length];
            int[] count = new int[base];
            for (int array : arrays) {
                count[array / k % base]++;
            }
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            for (int i = arrays.length - 1; i >= 0; i--) {
                int index = arrays[i] / k % base;
                count[index]--;
                temp[count[index]] = arrays[i];
            }
            arrays = temp;
        }
    }

    @Test
    public void testRadixSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        radixSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
