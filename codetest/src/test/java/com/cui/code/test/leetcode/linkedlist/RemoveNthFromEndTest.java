package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除链表的倒数第N个节点
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/42/
 *
 * @author cuishixiang
 * @date 2019-02-28
 */
public class RemoveNthFromEndTest {

    //使用一趟扫描实现：以空间换时间
    // 这个算法太慢且消耗了空间，网上有很经典的解法
    public ListNode removeNthFromEnd(ListNode head, int n) {
        Map<Integer, ListNode> map = new HashMap<>();

        int index = 0;
        while (head != null) {
            map.put(++index, head);
            head = head.next;
        }

        // 如果删除的是第一个节点
        if (n == index) {
            return map.get(1).next;
        } else {
            int preIndex = index - n;
            ListNode listNode = map.get(preIndex);
            listNode.next = listNode.next.next;
            return map.get(1);
        }
    }

    @Test
    public void testRemoveNthFromEnd() {
        ListNode head = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        head.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;

        ListNode listNode = removeNthFromEnd(head, 5);
        System.out.println(listNode);
    }
}
