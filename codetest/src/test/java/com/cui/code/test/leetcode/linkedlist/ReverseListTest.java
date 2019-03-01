package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 反转链表
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/43/
 *
 * @author cuishixiang
 * @date 2019-02-28
 */
public class ReverseListTest {

    public ListNode reverseList(ListNode head) {
        ListNode reverseList = null;
        while (head != null) {
            ListNode newNode = new ListNode(head.val);
            newNode.next = reverseList;
            reverseList = newNode;
            head = head.next;
        }
        return reverseList;
    }

    @Test
    public void testReverseList() {
        ListNode head = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        head.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;

        ListNode listNode = reverseList(head);
        System.out.println(listNode);
    }
}
