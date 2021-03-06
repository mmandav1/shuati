package leetcode;

/**
 * Created by yuank on 3/20/18.
 */
public class LE_161_One_Edit_Distance {
    /**
        Given two strings S and T, determine if they are both one edit dirs apart.
     */

    /**
     *  One edit distanc:
        1.substution:
          abcd
          abfd

        2.deletion (at any position, not just at the end):
          abcd    abdc
          abc     abc

        3.addition (at any position, not just at the end):
          abc     abc
          abcd    abdc
    */

    public boolean isOneEditDistance(String s, String t) {
        if (s == null || t == null) return false;

        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (s.length() == t.length()) {//substution
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else if (s.length() > t.length()) {//deletion
                    return s.substring(i + 1).equals(t.substring(i));
                } else {
                    return t.substring(i + 1).equals(s.substring(i));
                }
            }
        }

        /**
         * !!!
         * can't be "<=" here. If we get here, it means all chars in s and t are the same
         * from index 0 to index min(s.length(), t.length()) - 1, then they must have length
         * difference as 1. If it's more than 1, then it's more than 1 edit dirs. If it's 0,
         * then s and t are equal.
         * **/
        return Math.abs(s.length() - t.length()) == 1;
    }

    public boolean isOneEditDistance_practice(String s, String t) {
        if (s == null || t == null) return false;

        int l1 = s.length();
        int l2 = t.length();

        for (int i = 0; i < Math.min(l1, l2); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (l1 == l2) {
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else if (l1 > l2) {
                    return s.substring(i + 1).equals(t.substring(i));
                } else {
                    return s.substring(i).equals(t.substring(i + 1));
                }
            }
        }

        return Math.abs(l1 - l2) == 1;
    }
}
