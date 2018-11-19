package com.cui.code.test.leetcode;

import org.junit.Test;

/**
 * 无重复字符的最长子串
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/description/
 *
 * @author cuishixiang
 * @date 2018-11-18
 */
public class LengthOfLongestSubstringTest {

    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        String sub = "";
        int strartIndex = 0;
        int endIndex = 1;
        int length = s.length();
        while (strartIndex <= length - 1 && endIndex <= length) {

            char item = s.charAt(endIndex - 1);
            int indexOf = sub.indexOf(item);
            if (indexOf == -1) {
                sub += item;
                if (sub.length() > maxLength) {
                    maxLength = sub.length();
                }
            } else {
                strartIndex = strartIndex + indexOf + 1;
                sub = s.substring(strartIndex, endIndex);
            }
            endIndex++;
        }

        return maxLength;
    }

    @Test
    public void test() {
        System.out.println(lengthOfLongestSubstring("a"));
        System.out.println(lengthOfLongestSubstring("abcabcda"));
        System.out.println(lengthOfLongestSubstring("aaaaa"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring(""));
    }
}
