package com.cui.code.test.leetcode.sort;

import com.cui.code.test.algorithms.sort.QuickSortTest;
import org.junit.Test;

import java.util.Arrays;

/**
 * 75. 颜色分类
 * <p>
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * <p>
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * <p>
 * 注意:
 * 不能使用代码库中的排序函数来解决这道题。
 * <p>
 * 示例:
 * <p>
 * 输入: [2,0,2,1,1,0]
 * 输出: [0,0,1,1,2,2]
 * <p>
 * <p>
 * 进阶：
 * <p>
 * 一个直观的解决方案是使用计数排序的两趟扫描算法。
 * 首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
 * 你能想出一个仅使用常数空间的一趟扫描算法吗？
 * <p>
 * 链接：https://leetcode-cn.com/problems/sort-colors
 *
 * @author cuiswing
 * @date 2019-07-15
 */
public class SortColorsTest {

    // 最简单的思路是计数排序，可是题目要求是原地排序，不能使用额外的内存空间，然后想到的就是快速排序了
    public void sortColors(int[] nums) {
        int[] result = new int[nums.length];
        int[] count = new int[3];
        for (int array : nums) {
            count[array] += 1;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            count[nums[i]]--;
            result[count[nums[i]]] = nums[i];
        }
        System.arraycopy(result, 0, nums, 0, result.length);
    }

    public void sortColors2(int[] nums) {
        QuickSortTest.quickSort(nums, 0, nums.length - 1);
    }


    @Test
    public void testSortColors() {
        int[] nums = new int[]{2, 0, 2, 1, 1, 0};
        sortColors2(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }

}
