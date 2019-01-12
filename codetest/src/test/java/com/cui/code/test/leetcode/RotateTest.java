package com.cui.code.test.leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * 旋转数组
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/23/
 *
 * @author cuishixiang
 * @date 2019-01-08
 */
public class RotateTest {

    /* 要求使用空间复杂度为 O(1) 的原地算法。 */
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        if (k == 0) {
            return;
        }

        int index = 0;
        int temp = nums[index];

        int count = 0;
        int currentIndex = index;
        int nextIndex;
        while (count < nums.length) {
            nextIndex = currentIndex - k;
            if (nextIndex < 0) {
                nextIndex = nextIndex + nums.length;
            }
            if (nextIndex == index) {
                nums[currentIndex] = temp;
                index++;
                temp = nums[index];
                currentIndex = index;
            } else {
                nums[currentIndex] = nums[nextIndex];
                currentIndex = nextIndex;
            }
            count++;
        }
    }

    @Test
    public void testRotate() {
        int[] nums = new int[]{1, 1, 2, 4};
        rotate(nums, 2);
        Arrays.stream(nums).forEach(System.out::println);
    }
}
