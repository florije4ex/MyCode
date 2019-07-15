package com.cui.code.test.leetcode.sort;

import com.cui.code.test.algorithms.sort.BucketSortTest;
import org.junit.Test;

/**
 * 164. 最大间距
 * <p>
 * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
 * <p>
 * 如果数组元素个数小于 2，则返回 0。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 * <p>
 * 说明:
 * <p>
 * 你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
 * 请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
 * <p>
 * 链接：https://leetcode-cn.com/problems/maximum-gap
 *
 * @author cuiswing
 * @date 2019-07-16
 */
public class MaximumGapTest {

    // 线性时间只能用非比较排序：计数、基数、

    public int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        BucketSortTest.bucketSort(nums, 0, Integer.MAX_VALUE);

        int maxGap = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] > maxGap) {
                maxGap = nums[i] - nums[i - 1];
            }
        }
        return maxGap;
    }

    @Test
    public void testMaximumGap() {
        int[] nums = new int[]{3, 6, 9, 1};
        int maximumGap = maximumGap(nums);
        System.out.println(maximumGap);
    }
}
