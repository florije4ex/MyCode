package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 合并两个有序链表
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/44/
 *
 * @author cuishixiang
 * @date 2019-02-28
 */
public class MergeTwoListsTest {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = new ListNode(l1.val);
                tail = tail.next;
                l1 = l1.next;
            } else {
                tail.next = new ListNode(l2.val);
                tail = tail.next;
                l2 = l2.next;
            }
        }
        while (l1 != null) {
            tail.next = new ListNode(l1.val);
            tail = tail.next;
            l1 = l1.next;
        }
        while (l2 != null) {
            tail.next = new ListNode(l2.val);
            tail = tail.next;
            l2 = l2.next;
        }
        return head.next;
    }

    @Test
    public void testReverseList() {
        ListNode l11 = new ListNode(1);
        ListNode l12 = new ListNode(2);
        ListNode l13 = new ListNode(4);
        l11.next = l12;
        l12.next = l13;

        ListNode l21 = new ListNode(1);
        ListNode l22 = new ListNode(3);
        ListNode l23 = new ListNode(4);
        l21.next = l22;
        l22.next = l23;

        ListNode listNode = mergeTwoLists(l11, l21);
        System.out.println(listNode);
    }
}
