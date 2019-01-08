package com.cui.code.test.leetcode;

import org.junit.Test;

/**
 * 从排序数组中删除重复项
 * https://leetcode-cn.com/explore/featured/card/top-interview-questions-easy/1/array/21/
 *
 * @author cuishixiang
 * @date 2019-01-08
 */
public class RemoveDuplicatesTest {

    public int removeDuplicates(int[] nums) {
        int start = 0;
        int end = 0;

        while (end < nums.length) {
            if (nums[start] != nums[end]) {
                start++;
                nums[start] = nums[end];
            }
            end++;
        }
        return start + 1;
    }

    @Test
    public void testRemoveDuplicates() {
        int[] nums = new int[]{1, 1, 2};
        int len = removeDuplicates(nums);
        for (int i = 0; i < len; i++) {
            System.out.println(nums[i]);
        }
    }

}
