import com.google.common.base.Preconditions;

import java.util.Objects;

public class Palindromic {
    public String longestPalindrome(String s) {
        if(s.isEmpty()) {
            return "";
        }
        int maxLength = 1;
        int index = 0;
        int end = s.length() - 1;
        for(int i=0; i<end; ++i) {
            int p = i;
            int j = i + 1;
            for(; j<s.length(); ++j) {
                if(s.charAt(i) != s.charAt(j)) {
                    break;
                }
                ++i;
            }
            int q = i;
            while(true) {
                --p;
                ++q;
                if(p < 0 || q == s.length() || s.charAt(p) != s.charAt(q)) {
                    break;
                }
            }
            int len = q - (++p);
            if (maxLength < len) {
                maxLength = len;
                end = s.length() - maxLength/2;
                index = p;
            }
        }

        return s.substring(index, index + maxLength);
    }

    public String longestPalindrome3(String s) {
        if(s.isEmpty()) {
            return "";
        }
        int maxLength = 1;
        int index = 0;
        for(int i=1; i<s.length() - maxLength/2; ++i) {
            int p = i;
            int q = i - 1;
            while(--p >= 0 && ++q < s.length()) {
                if(s.charAt(p) == s.charAt(q)) {
                    int len = q - p + 1;
                    if (maxLength < len) {
                        maxLength = len;
                        index = p;
                    }
                } else {
                    break;
                }
            }
            p = q = i;
            while(--p >= 0 && ++q < s.length()) {
                if(s.charAt(p) == s.charAt(q)) {
                    int len = q - p + 1;
                    if (maxLength < len) {
                        maxLength = len;
                        index = p;
                    }
                } else {
                    break;
                }
            }
        }

        return s.substring(index, index + maxLength);
    }

    public String longestPalindrome2(String s) {
        if(s.isEmpty()) {
            return "";
        }
        int maxLength = 1;
        int index = 0;
        for(int i=0; i<s.length() - 1; ++i) {
            int p = i;
            int q = p;
            if (s.charAt(p) == s.charAt(p + 1)) {
                q = p + 1;
                while(true) {
                    if(p < 1 || q + 1 == s.length() || s.charAt(p - 1) != s.charAt(q + 1)) {
                        if (maxLength < q - p + 1) {
                            maxLength = q - p + 1;
                            index = p;
                        }
                        break;
                    }
                    --p;
                    ++q;
                }
            }
            p = i;
            q = p;
            while(true) {
                if(p < 1 || q + 1 == s.length() || s.charAt(p - 1) != s.charAt(q + 1)) {
                    if (maxLength < q - p + 1) {
                        maxLength = q - p + 1;
                        index = p;
                    }
                    break;
                }
                --p;
                ++q;
            }
        }

        return s.substring(index, index + maxLength);
    }

    public String longestPalindrome1(String s) {
        if(s.isEmpty()) {
            return "";
        }
        int maxLength = 1;
        int fi = 0;
        int fj = 1;
        for(int i=0; maxLength < s.length() - i; ++i) {
            for (int j = s.length(); maxLength < j - i; --j) {
                int k = i;
                int mid = (i + j) / 2;
                for(; k < mid; ++k) {
                    if(s.charAt(k) != s.charAt(j - (k - i) - 1)) {
                        break;
                    }
                }
                if(k == mid) {
                    if(j - i > maxLength) {
                        maxLength = j - i;
                        fi = i;
                        fj = j;
                        break;
                    }
                }
            }
        }
        return s.substring(fi, fj);
    }

    public static void main(String[] args) {
        Palindromic p = new Palindromic();
        Preconditions.checkState(Objects.equals("a", p.longestPalindrome("ab")), String.format("%s!=%s", "a", p.longestPalindrome("ab")));
        Preconditions.checkState(Objects.equals("aa", p.longestPalindrome("aa")), String.format("%s!=%s", "aa", p.longestPalindrome("aa")));
        Preconditions.checkState(Objects.equals("aaa", p.longestPalindrome("aaa")), String.format("%s!=%s", "aaa", p.longestPalindrome("aaa")));
        Preconditions.checkState(Objects.equals("a", p.longestPalindrome("abc")), String.format("%s!=%s", "a", p.longestPalindrome("abc")));
        Preconditions.checkState(Objects.equals("aba", p.longestPalindrome("aba")), String.format("%s!=%s", "aba", p.longestPalindrome("aba")));
        Preconditions.checkState(Objects.equals("aa", p.longestPalindrome("aacdefcaa")), String.format("%s!=%s", "aa", p.longestPalindrome("aacdefcaa")));
        Preconditions.checkState(Objects.equals("abcba", p.longestPalindrome("abcba123")), String.format("%s!=%s", "aa", p.longestPalindrome("abcba123")));
        Preconditions.checkState(Objects.equals("abcba", p.longestPalindrome("123abcba")), String.format("%s!=%s", "aa", p.longestPalindrome("123abcba")));
        Preconditions.checkState(Objects.equals("abcba", p.longestPalindrome("123abcba456")), String.format("%s!=%s", "aa", p.longestPalindrome("123abcba456")));
        Preconditions.checkState(Objects.equals("bacab", p.longestPalindrome("abacab")), String.format("%s!=%s", "bacab", p.longestPalindrome("abacab")));
    }
}
