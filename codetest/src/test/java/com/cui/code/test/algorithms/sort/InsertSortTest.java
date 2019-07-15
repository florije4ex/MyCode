package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 插入排序：扑克牌，摸牌，比较，插入合适的位置
 * 比冒泡快多了，2min
 *
 * @author cuiswing
 * @date 2019-05-17
 */
public class InsertSortTest {

    // 空间复杂度：O(0)
    // 时间复杂度O(n²)
    public void insertSort(int[] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            int temp = arrays[i];
            int j = i - 1;
            while (j >= 0 && temp < arrays[j]) {
                arrays[j + 1] = arrays[j];
                j--;
            }
            arrays[j + 1] = temp;
        }
    }

    // 二维数组仅对第一位数进行排序
    public static void insertSort(int[][] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            int[] temp = arrays[i];
            int j = i - 1;
            while (j >= 0 && temp[0] < arrays[j][0]) {
                arrays[j + 1] = arrays[j];
                j--;
            }
            arrays[j + 1] = temp;
        }
    }

    @Test
    public void testInsertSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        insertSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
