/**
 * eBay.com Inc.
 * Copyright (c) 1995-2020 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

/**
 * Accepted.
 * 
 * @author Tony, Zhao
 * @version $Id: LongestPalindromicString.java, v 0.1 2020-08-28 9:26 PM Tony, Zhao Exp $$
 */
public class LongestPalindromicString {

    public static void main(String[] args) {
        LongestPalindromicString lp = new LongestPalindromicString();

        String test1 = "babad";
        String test2 = "cbbd";
        String test3 = "ac";
        String test4 = "abcba";
        String test5 = "ccc";
        String test6 = "azwdzwmwcqzgcobeeiphemqbjtxzwkhiqpbrprocbppbxrnsxnwgikiaqutwpftbiinlnpyqstkiqzbggcsdzzjbrkfmhgtnbujzszxsycmvipjtktpebaafycngqasbbhxaeawwmkjcziybxowkaibqnndcjbsoehtamhspnidjylyisiaewmypfyiqtwlmejkpzlieolfdjnxntonnzfgcqlcfpoxcwqctalwrgwhvqvtrpwemxhirpgizjffqgntsmvzldpjfijdncexbwtxnmbnoykxshkqbounzrewkpqjxocvaufnhunsmsazgibxedtopnccriwcfzeomsrrangufkjfzipkmwfbmkarnyyrgdsooosgqlkzvorrrsaveuoxjeajvbdpgxlcrtqomliphnlehgrzgwujogxteyulphhuhwyoyvcxqatfkboahfqhjgujcaapoyqtsdqfwnijlkknuralezqmcryvkankszmzpgqutojoyzsnyfwsyeqqzrlhzbc";

        String validate1 = "bab";
        String validate2 = "bb";
        String validate3 = "a";
        String validate4 = "abcba";
        String validate5 = "ccc";
        String validate6 = "sooos";

        String result1 = lp.longestPalindrome(test1);
        String result2 = lp.longestPalindrome(test2);
        String result3 = lp.longestPalindrome(test3);
        String result4 = lp.longestPalindrome(test4);
        String result5 = lp.longestPalindrome(test5);
        String result6 = lp.longestPalindrome(test6);

        System.out.println(validate1.equals(result1));
        System.out.println(validate2.equals(result2));
        System.out.println(validate3.equals(result3));
        System.out.println(validate4.equals(result4));
        System.out.println(validate5.equals(result5));
        System.out.println(validate6.equals(result6));
    }

    public String longestPalindrome(String s) {
        int length = s.length();
        if (length == 0) {
            return "";
        }

        // search max Palindromic
        int maxLength = 1;
        String maxResult = getKeyByPos(0, s);

        for (int i = 1; i < length; i++) {
            // first check single
            String searchResult = singleCharSearch(i, s);
            if (searchResult.length() > maxLength) {
                maxResult = searchResult;
                maxLength = searchResult.length();
            }

            // if repeated, search double again
            if (getKeyByPos(i, s).equals(getKeyByPos(i - 1, s))) {
                // equals left double compare
                searchResult = doubleCharSearch(i - 1, i, s);
            }
            if (searchResult.length() > maxLength) {
                maxResult = searchResult;
                maxLength = searchResult.length();
            }
        }

        return maxResult;
    }

    public String doubleCharSearch(int left, int right, String s) {
        int length = s.length();
        if (length == 0) {
            return "";
        }

        if (!getKeyByPos(left, s).equals(getKeyByPos(right, s))) {
            return "";
        }

        // confirm pos
        while (left > 0 && right < length - 1) {
            left -= 1;
            right += 1;

            if (!getKeyByPos(left, s).equals(getKeyByPos(right, s))) {
                left += 1;
                right -= 1;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = left; i <= right; i++) {
            sb.append(getKeyByPos(i, s));
        }
        return sb.toString();
    }

    public String singleCharSearch(int current, String s) {
        int length = s.length();
        if (length == 0) {
            return "";
        }

        int low = current - 1;
        int high = current + 1;

        while (low >= 0 && high <= length - 1) {
            if (!getKeyByPos(low, s).equals(getKeyByPos(high, s))) {
                low += 1;
                high -= 1;
                break;
            }
            low -= 1;
            high += 1;
        }

        if (low < 0 || high > length - 1) {
            low += 1;
            high -= 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = low; i <= high; i++) {
            sb.append(getKeyByPos(i, s));
        }
        return sb.toString();
    }

    public String getKeyByPos(int pos, String s) {
        int length = s.length();
        if (pos < 0 || pos >= length) {
            return "";
        }
        return String.valueOf(new char[] { s.charAt(pos) });
    }

}