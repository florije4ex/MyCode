package com.cui.code.test.algorithms.sort;

/**
 * 排序工具类
 *
 * @author cuiswing
 * @date 2019-07-12
 */
public class SortUtil {
    // 需要排序的个数：100w
    public static final int ORDER_NUMBERS = 1_000_000;
    // 最小值（包含）：0
    public static final int MIN_NUM = 0;
    // 最大值（不包含）：10亿
    public static final int MAX_NUM = 1_000_000_000;


    // 交换数组中index：i和j位置的元素
    public static void swap(int[] arrays, int i, int j) {
        int temp = arrays[j];
        arrays[j] = arrays[i];
        arrays[i] = temp;
    }
}
