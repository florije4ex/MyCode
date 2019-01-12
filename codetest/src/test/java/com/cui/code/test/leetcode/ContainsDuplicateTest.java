package com.cui.code.test.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 存在重复
 * https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/24/
 *
 * @author cuishixiang
 * @date 2019-01-12
 */
public class ContainsDuplicateTest {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        return false;
    }

    @Test
    public void testContainsDuplicate() {
        int[] nums = new int[]{1, 2, 3, 1};
        System.out.println(containsDuplicate(nums));
    }
}
