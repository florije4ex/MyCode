package com.cui.code.test.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 两数相加
 * https://leetcode-cn.com/problems/add-two-numbers/description/
 *
 * @author cuishixiang
 * @date 2018-11-18
 */
public class AddTwoNumTest {


    //Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        long num1 = 0;
        long num2 = 0;
        long unit = 1;
        do {
            num1 += l1.val * unit;
            unit *= 10;
            l1 = l1.next;
        } while (l1 != null);

        unit = 1;
        do {
            num2 += l2.val * unit;
            unit *= 10;
            l2 = l2.next;
        } while (l2 != null);

        long sum = num1 + num2;
        ListNode first = null;
        ListNode tail = null;

        do {
            int num = (int) (sum % 10);
            if (first == null) {
                first = new ListNode(num);
                tail = first;
            } else {
                ListNode tailNode = new ListNode(num);
                tail.next = tailNode;
                tail = tailNode;
            }
            sum /= 10;
        } while (sum != 0);

        return first;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        List<Integer> nodes = new ArrayList<>();
        int sum = 0;
        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + sum;
            l1 = l1.next;
            l2 = l2.next;
            if (sum >= 10) {
                nodes.add(sum % 10);
                sum = 1;
            } else {
                nodes.add(sum);
                sum = 0;
            }
        }

        if (l1 != null) {
            while (l1 != null) {
                if (sum == 1) {
                    sum = l1.val + sum;
                    if (sum >= 10) {
                        nodes.add(0);
                        sum = 1;
                    } else {
                        nodes.add(sum);
                        sum = 0;
                    }
                } else {
                    nodes.add(l1.val);
                }
                l1 = l1.next;
            }
        } else if (l2 != null) {
            while (l2 != null) {
                if (sum == 1) {
                    sum = l2.val + sum;
                    if (sum == 10) {
                        nodes.add(0);
                        sum = 1;
                    } else {
                        nodes.add(sum);
                        sum = 0;
                    }
                } else {
                    nodes.add(l2.val);
                }
                l2 = l2.next;
            }
        }
        if (sum == 1) {
            nodes.add(1);
        }

        Integer[] integers = new Integer[nodes.size()];
        return createList(nodes.toArray(integers));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    @Test
    public void testCalc() {
        ListNode l1 = createList(2, 4, 3);
        ListNode l2 = createList(5, 6, 4);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    @Test
    public void test2() {
        ListNode l1 = createList(0);
        ListNode l2 = createList(0);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    @Test
    public void test3() {
        ListNode l1 = createList(9);
        ListNode l2 = createList(1, 9, 9, 9, 9, 9, 9, 9, 9, 9);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    @Test
    public void test4() {
        ListNode l1 = createList(0);
        ListNode l2 = createList(7, 3);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    @Test
    public void test5() {
        ListNode l1 = createList(5);
        ListNode l2 = createList(5);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    @Test
    public void test6() {
        ListNode l1 = createList(8, 3, 6, 9, 0, 9, 9, 0, 1, 2);
        ListNode l2 = createList(6, 7, 9, 2, 1, 5, 2);

        ListNode result = addTwoNumbers2(l1, l2);
        printList(result);
    }

    public ListNode createList(Integer... nums) {
        ListNode listNode = new ListNode(0);
        ListNode tail = listNode;
        for (Integer num : nums) {
            tail.next = new ListNode(num);
            tail = tail.next;
        }
        return listNode.next;
    }

    public void printList(ListNode listNode) {
        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

}
