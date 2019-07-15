package com.cui.code.test.leetcode.sort;

import com.cui.code.test.algorithms.sort.InsertSortTest;
import org.junit.Test;

/**
 * 合并区间：给出一个区间的集合，请合并所有重叠的区间。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 链接：https://leetcode-cn.com/problems/merge-intervals
 *
 * @author cuiswing
 * @date 2019-07-15
 */
public class MergeIntervalTest {

    public int[][] mergeInterval(int[][] intervals) {
        int[][] temp = new int[intervals.length][];

        if (intervals.length <= 1) {
            return intervals;
        }

        InsertSortTest.insertSort(intervals);
        int count = 0;
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= end) {
                if (intervals[i][1] > end) {
                    end = intervals[i][1];
                }
            } else {
                temp[count] = new int[]{start, end};
                count++;
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        temp[count++] = new int[]{start, end};

        int[][] result = new int[count][];
        System.arraycopy(temp, 0, result, 0, count);

        return result;
    }

    @Test
    public void testMergeInterval() {
        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}};

        int[][] mergeIntervals = mergeInterval(intervals);
        for (int[] mergeInterval : mergeIntervals) {
            for (int interval : mergeInterval) {
                System.out.print(interval + ",");
            }
            System.out.println();
        }
    }
}
