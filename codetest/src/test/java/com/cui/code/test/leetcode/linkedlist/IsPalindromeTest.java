package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 回文链表
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/45/
 *
 * @author cuishixiang
 * @date 2019-02-28
 */
public class IsPalindromeTest {

    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        ListNode first = head;
        ListNode second = head.next;

        while (second != null) {
            first = first.next;
            second = second.next;
            if (second != null) {
                second = second.next;
            }
        }

        ListNode reverseNode = first;
        ListNode moveNode = first.next;
        reverseNode.next = null;
        while (moveNode != null) {
            ListNode temp = moveNode;
            moveNode = moveNode.next;
            temp.next = reverseNode;
            reverseNode = temp;
        }

        while (reverseNode != null) {
            if (head.val != reverseNode.val) {
                return false;
            }
            head = head.next;
            reverseNode = reverseNode.next;
        }
        return true;
    }

    // 这个答案不靠谱啊
    public boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.val == head.next.val;
        }
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast.next != null) {
            if (slow.val == fast.next.val) {
                if (fast.next.next != null) {
                    return false;
                }
                fast.next = null;
                slow = slow.next;
                fast = slow.next;
                if (fast == null || slow.val == fast.val) {
                    return true;
                }
            } else {
                fast = fast.next;
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
        boolean palindrome = isPalindrome(head);
        System.out.println(palindrome);
    }
}
