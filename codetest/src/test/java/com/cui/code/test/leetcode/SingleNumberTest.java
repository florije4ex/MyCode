package com.cui.code.test.leetcode;

import org.junit.Test;

/**
 * 只出现一次的数字
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/25/
 *
 * @author cuishixiang
 * @date 2019-01-12
 */
public class SingleNumberTest {

    // 难点在于控制线性时间复杂度 O(n)，不使用额外空间 O(1)
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single = single ^ num;
        }
        return single;
    }

    // 这题实在想不到了，除了排序或用set集合，这都不符合题意要求了，网上给出的答案是位运算：异或。这个就真的考基本功了……😅
    //思路：根据异或运算的特点，相同的数字经过异或运算后结果为0，除单独出现一次的数字外，其他数字都是出现两次的，那么这些数字经过异或运算后结果一定是0。而任何数字与0进行异或运算都是该数字本身。所以对数组所有元素进行异或运算，运算结果就是题目的答案。
    @Test
    public void testSingleNumber() {
        int[] nums = new int[]{4, 1, 3, 2, 1, 2, 3};
        System.out.println(singleNumber(nums));
    }
}
