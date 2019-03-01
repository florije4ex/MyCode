package com.cui.code.test.leetcode.linkedlist;

import com.cui.code.test.leetcode.ListNode;
import org.junit.Test;

/**
 * 删除链表中的节点
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/6/linked-list/41/
 *
 * @author cuishixiang
 * @date 2019-02-28
 */
public class DeleteNodeTest {

    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = next.val;
        node.next = next.next;
    }

    @Test
    public void testDeleteNode() {
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(5);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(9);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        deleteNode(listNode3);
        System.out.println(listNode3);
    }

}
