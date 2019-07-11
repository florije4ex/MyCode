package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 选择排序：411690ms
 *
 * @author cuiswing
 * @date 2019-07-11
 */
public class SelectionSortTest {

    // 空间复杂度：O(0)
    // 时间复杂度O(n²)
    public void selectionSort(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arrays.length; j++) {
                if (arrays[j] < arrays[minIndex]) {
                    minIndex = j;
                }
            }
            SortUtil.swap(arrays, i, minIndex);
        }
    }

    private void swap(int[] arrays, int low, int high) {
        int temp = arrays[high];
        arrays[high] = arrays[low];
        arrays[low] = temp;
    }

    @Test
    public void testSelectionSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        selectionSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
