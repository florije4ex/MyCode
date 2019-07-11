package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 冒泡排序：流行但低效的排序算法。反复交换相邻的未按次序排序的元素
 * 跑了27min
 *
 * @author cuiswing
 * @date 2019-07-11
 */
public class BubbleSortTest {

    // 空间复杂度：O(0)
    // 时间复杂度O(n²)
    public void bubbleSort(int[] arrays) {
        for (int i = 0; i <= arrays.length - 2; i++) {
            for (int j = arrays.length - 1; j > i; j--) {
                if (arrays[j - 1] > arrays[j]) {
                    SortUtil.swap(arrays, j - 1, j);
                }
            }
        }
    }

    @Test
    public void testBubbleSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        bubbleSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
