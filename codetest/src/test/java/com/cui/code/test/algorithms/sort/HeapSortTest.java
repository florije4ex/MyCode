package com.cui.code.test.algorithms.sort;

import org.junit.Test;

import java.util.Random;

/**
 * 堆排序：192ms
 *
 * @author cuiswing
 * @date 2019-07-11
 */
public class HeapSortTest {


    public void heapSort(int[] arrays) {
        // 先构建一个最大堆
        buildMaxHeap(arrays);
        int heapSize = arrays.length;
        for (int i = arrays.length - 1; i > 0; i--) {
            swap(arrays, 0, i);
            heapSize--;
            maxHeapIfy(arrays, heapSize, 0);
        }
    }

    private void buildMaxHeap(int[] arrays) {
        for (int i = arrays.length / 2; i >= 0; i--) {
            maxHeapIfy(arrays, arrays.length, i);
        }
    }

    // 最大堆调整
    private void maxHeapIfy(int[] arrays, int heapSize, int i) {
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        int largest = i;
        if (leftChild < heapSize && arrays[leftChild] > arrays[i]) {
            largest = leftChild;
        }
        if (rightChild < heapSize && arrays[rightChild] > arrays[largest]) {
            largest = rightChild;
        }
        if (largest != i) {
            swap(arrays, i, largest);
            maxHeapIfy(arrays, heapSize, largest);
        }
    }


    private void swap(int[] arrays, int low, int high) {
        int temp = arrays[high];
        arrays[high] = arrays[low];
        arrays[low] = temp;
    }

    @Test
    public void testHeapSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        heapSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
