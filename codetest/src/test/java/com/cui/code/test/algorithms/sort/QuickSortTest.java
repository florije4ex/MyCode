package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 快速排序
 * 可以用于topN问题
 * 很快，114ms
 *
 * @author cuiswing
 * @date 2019-05-17
 */
public class QuickSortTest {

    public static void quickSort(int[] arrays, int low, int high) {
        if (low < high) {
            int mid = partition(arrays, low, high);
            quickSort(arrays, low, mid - 1);
            quickSort(arrays, mid + 1, high);
        }
    }

    private static int partition(int[] arrays, int low, int high) {
        int midValue = arrays[low];
        while (low < high) {
            while (low < high && arrays[high] >= midValue) {
                high--;
            }
            SortUtil.swap(arrays, low, high);

            while (low < high && arrays[low] < midValue) {
                low++;
            }
            SortUtil.swap(arrays, low, high);
        }
        return low;
    }

    @Test
    public void testQuickSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        quickSort(arrays, 0, arrays.length - 1);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");

        // Arrays.stream(arrays).forEach(System.out::println);
    }

}
