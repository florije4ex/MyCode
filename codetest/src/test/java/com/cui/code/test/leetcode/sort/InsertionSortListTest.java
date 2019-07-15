package com.cui.code.test.leetcode.sort;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 147. 对链表进行插入排序：
 * <p>
 * 插入排序算法：
 * <p>
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 * <p>
 * 链接：https://leetcode-cn.com/problems/insertion-sort-list
 *
 * @author cuiswing
 * @date 2019-07-15
 */
public class InsertionSortListTest {

    public static ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode insertNode = head.next;
        head.next = null;

        while (insertNode != null) {
            ListNode nextNode = insertNode.next;

            ListNode tempNode = head;
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
        return head;
    }


    @Test
    public void testReverseList() {
        ListNode head = new ListNode(4);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(3);
        head.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        ListNode sortList = insertionSortList(head);
        System.out.println(sortList);
    }
}
