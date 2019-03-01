package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 环形链表
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/46/
 *
 * @author cuishixiang
 * @date 2019-03-01
 */
public class HasCycleTest {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null) {
            if (fast == slow) {
                return true;
            } else {
                slow = slow.next;
                fast = fast.next;
                if (fast != null) {
                    fast = fast.next;
                }
            }
        }
        return false;
    }


    @Test
    public void testReverseList() {
        ListNode head = new ListNode(1);
        ListNode listNode2 = new ListNode(3);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(3);
        ListNode listNode5 = new ListNode(1);
        head.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode3;
        boolean hasCycle = hasCycle(head);
        System.out.println(hasCycle);
    }

}
