package com.cui.code.test.leetcode;

import org.junit.Test;

/**
 * 买卖股票的最佳时机 II
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/22/
 *
 * @author cuishixiang
 * @date 2019-01-08
 */
public class MaxProfitTest {

    public int maxProfit(int[] prices) {
        int start = 0;
        int end = 0;

        int sum = 0;
        while (end < prices.length) {
            if (prices[end] < prices[start]) {
                start = end;
            } else if (prices[end] > prices[start]) {
                sum += (prices[end] - prices[start]);
                start = end;
            }
            end++;
        }
        return sum;
    }

    @Test
    public void testMaxProfit() {
        int[] prices = new int[]{7, 6, 4, 3, 1};
        System.out.println(maxProfit(prices));
    }
}
