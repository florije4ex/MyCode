package com.cui.code.test.leetcode.sort;

import org.junit.Test;

/**
 * 57. 插入区间
 * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 * <p>
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 * <p>
 * 示例 1:
 * <p>
 * 输入: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出: [[1,5],[6,9]]
 * <p>
 * 示例 2:
 * <p>
 * 输入: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出: [[1,2],[3,10],[12,16]]
 * 解释: 这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
 * <p>
 * 链接：https://leetcode-cn.com/problems/insert-interval
 *
 * @author cuiswing
 * @date 2019-07-15
 */
public class InsertIntervalTest {

    public int[][] insertInterval(int[][] intervals, int[] newInterval) {
        int minIndex = intervals.length;
        for (int i = intervals.length - 1; i >= 0; i--) {
            if (newInterval[0] > intervals[i][0]) {
                break;
            }
            minIndex--;
        }

        int maxIndex = minIndex;
        for (int i = minIndex; i < intervals.length; i++) {
            if (newInterval[1] < intervals[i][1]) {
                break;
            }
            maxIndex++;
        }

        int[][] insertTemp = new int[minIndex + 1 + intervals.length - maxIndex][];
        System.arraycopy(intervals, 0, insertTemp, 0, minIndex);
        insertTemp[minIndex] = newInterval;
        if (intervals.length - maxIndex > 0) {
            System.arraycopy(intervals, maxIndex, insertTemp, minIndex + 1, intervals.length - maxIndex);
        }

        int[][] temp = new int[insertTemp.length][];

        int count = 0;
        int start = insertTemp[0][0];
        int end = insertTemp[0][1];
        for (int i = 1; i < insertTemp.length; i++) {
            if (insertTemp[i][0] <= end) {
                if (insertTemp[i][1] > end) {
                    end = insertTemp[i][1];
                }
            } else {
                temp[count] = new int[]{start, end};
                count++;
                start = insertTemp[i][0];
                end = insertTemp[i][1];
            }
        }
        temp[count++] = new int[]{start, end};

        int[][] result = new int[count][];
        System.arraycopy(temp, 0, result, 0, count);

        return result;
    }

    @Test
    public void testInsertInterval() {
        int[][] intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = {4, 17};

        int[][] mergeIntervals = insertInterval(intervals, newInterval);
        for (int[] mergeInterval : mergeIntervals) {
            for (int interval : mergeInterval) {
                System.out.print(interval + ",");
            }
            System.out.println();
        }
    }
}
