package com.cui.code.test.leetcode.array;

import org.junit.Test;

/**
 * 接雨水
 * https://leetcode-cn.com/problems/trapping-rain-water/description/
 *
 * @author cuishixiang
 * @date 2019-01-16
 */
public class WaterCollectTest {

    // O(n)
    public int getMaxIndex(int[] nums, int startIndex, int endIndex) {
        int maxIndex = startIndex;
        for (int i = startIndex + 1; i < endIndex; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // 时间复杂度：O(n²) = O(n) + O(n) * 左边元素个数 + O(n) * 右边元素个数
    public int waterCollect(int[] height) {
        // O(n)
        int maxIndex = getMaxIndex(height, 0, height.length);
        int collectCount = 0;

        // 左边的遍历
        int leftMaxIndex = maxIndex;
        // O(n) * 左边元素个数
        while (leftMaxIndex > 1) {
            int leftSecondIndex = getMaxIndex(height, 0, leftMaxIndex);
            int temp = leftSecondIndex;
            while (++temp < leftMaxIndex) {
                collectCount = collectCount + height[leftSecondIndex] - height[temp];
            }
            leftMaxIndex = leftSecondIndex;
        }

        // 右边的遍历
        int rightMaxIndex = maxIndex;
        // O(n) * 右边元素个数
        while (rightMaxIndex < height.length - 2) {
            int rightSecondIndex = getMaxIndex(height, rightMaxIndex + 1, height.length);
            int temp = rightMaxIndex;
            while (++temp < rightSecondIndex) {
                collectCount = collectCount + height[rightSecondIndex] - height[temp];
            }
            rightMaxIndex = rightSecondIndex;
        }
        return collectCount;
    }

    @Test
    public void testWaterCollect() {
        int[] nums = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] nums2 = new int[]{3, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int[] nums3 = new int[]{4, 2, 0, 3, 2, 5};
        System.out.println(waterCollect(nums));
        System.out.println(waterCollect(nums2));
        System.out.println(waterCollect(nums3));
    }
}
