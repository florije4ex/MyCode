package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * 归并排序：202ms
 * 需要额外的空间O(n)，先分解成小数组，在小数组长度小于一定值时可以采用插入排序，
 *
 * @author cuiswing
 * @date 2019-07-11
 */
public class MergeSortTest {

    public void mergeSort(int[] arrays, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arrays, low, mid);
            mergeSort(arrays, mid + 1, high);
            merge(arrays, low, mid, high);
        }
    }

    private void merge(int[] arrays, int low, int mid, int high) {
        int[] leftArrays = Arrays.copyOfRange(arrays, low, mid + 1);
        int[] rightArrays = Arrays.copyOfRange(arrays, mid + 1, high + 1);
        int l = 0;
        int r = 0;
        int index = low;
        while (l < leftArrays.length && r < rightArrays.length) {
            if (leftArrays[l] <= rightArrays[r]) {
                arrays[index] = leftArrays[l];
                l++;
            } else {
                arrays[index] = rightArrays[r];
                r++;
            }
            index++;
        }

        while (l < leftArrays.length) {
            arrays[index] = leftArrays[l];
            index++;
            l++;
        }

        while (r < rightArrays.length) {
            arrays[index] = rightArrays[r];
            index++;
            r++;
        }
    }


    @Test
    public void testMergeSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        mergeSort(arrays, 0, arrays.length - 1);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }

}
