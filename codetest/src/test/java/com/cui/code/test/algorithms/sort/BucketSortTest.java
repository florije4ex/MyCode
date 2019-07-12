package com.cui.code.test.algorithms.sort;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

import java.util.Random;

/**
 * 桶排序：95ms
 *
 * @author cuiswing
 * @date 2019-07-12
 */
public class BucketSortTest {

    // 每个桶链接多少个元素, 设为1时：95ms，设为2时：116ms，3：123ms，4：128ms，100：173ms
    int BUCKET_LIST_LENGTH = 1;

    // 设置桶的默认数量，这个参数很重要，当设为SortUtil.ORDER_NUMBERS时速度最快，就是桶的数量和待排序的元素总数一样，但是很费内存
    int DEFAULT_BUCKET_SIZE = SortUtil.ORDER_NUMBERS / BUCKET_LIST_LENGTH;

    // 每个桶的数据范围
    int BUCKET_RANGE = (SortUtil.MAX_NUM - SortUtil.MIN_NUM) / DEFAULT_BUCKET_SIZE;

    public void bucketSort(int[] arrays) {
        ListNode[] listNodes = new ListNode[DEFAULT_BUCKET_SIZE];
        for (int array : arrays) {
            ListNode listNode = new ListNode(array);
            listNode.next = listNodes[array / BUCKET_RANGE];
            listNodes[(array - SortUtil.MIN_NUM) / BUCKET_RANGE] = listNode;
        }
        for (ListNode listNode : listNodes) {
            insertSort(listNode);
        }

        int index = 0;
        for (int i = 0; i < listNodes.length; i++) {
            ListNode tempNode = listNodes[i];
            while (tempNode != null) {
                arrays[index] = tempNode.val;
                tempNode = tempNode.next;
                index++;
            }
        }
    }

    private void insertSort(ListNode listNode) {
        if (listNode == null || listNode.next == null) {
            return;
        }

        ListNode headNode = listNode;
        ListNode insertNode = headNode.next;
        headNode.next = null;

        while (insertNode != null) {
            ListNode nextNode = insertNode.next;

            ListNode tempNode = headNode;
            while (true) {
                if (insertNode.val < tempNode.val) {
                    ListNode newInsertNode = new ListNode(tempNode.val);
                    newInsertNode.next = tempNode.next;
                    tempNode.val = insertNode.val;
                    tempNode.next = newInsertNode;
                    break;
                } else if (tempNode.next == null) {
                    tempNode.next = insertNode;
                    insertNode.next = null;
                    break;
                } else {
                    tempNode = tempNode.next;
                }
            }

            insertNode = nextNode;
        }
    }

    @Test
    public void testCountingSort() {
        int[] arrays = new Random().ints(SortUtil.ORDER_NUMBERS, SortUtil.MIN_NUM, SortUtil.MAX_NUM).toArray();
        // Arrays.stream(arrays).forEach(System.out::println);

        long startTimes = System.currentTimeMillis();
        bucketSort(arrays);
        long durationsMillis = System.currentTimeMillis() - startTimes;
        System.out.println("耗时：" + durationsMillis + "ms");
        System.out.println("----------");
        // Arrays.stream(arrays).forEach(System.out::println);
    }
}
