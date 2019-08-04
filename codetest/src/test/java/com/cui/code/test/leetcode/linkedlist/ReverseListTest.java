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

    /**
     * 遍历法：将当前节点的下一个节点缓存后更改当前节点指针
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode reverseList = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = reverseList;
            reverseList = head;
            head = next;
        }
        return reverseList;
    }

    /**
     * 递归法
     *
     * @param head
     * @return
     */
    public ListNode reverseListLoop(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode revList = reverseListLoop(head.next);
        head.next.next = head;
        head.next = null;
        return revList;
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

        ListNode listNode = reverseListLoop(head);
        System.out.println(listNode);
    }
}
