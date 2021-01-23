package Interviews.Karat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Basic_Calculator {
    /**
     * 1. Give a string including numbers and + -, and find the result. (Simple calculator)
     * 2.follow up: what to do if brackets are included.
     * 3.follow up: not only numbers and operators, but also some variables, some of these variables can be expressed as a number,
     * Need to get the value of this variable from the given map. Then some variables cannot be converted to numbers, so the result should include this
     * Some words that cannot be turned into numbers and other digital parts are the results obtained by calculator.
     *
     * Question 3 was finished at the time, but the interviewer only gave input, and the map he gave me used the interface of immutableMap.
     * The first time I saw this thing in lz Taicai, I didn�t know what java package to import. I asked the interviewer to let me google it myself. I played around for a long time.
     * I kept getting an error saying that I could not find the class, so I asked if I could replace it with a hashmap and put it in one by one. The interviewer said no . .
     * It's time when I am changing, and it is very embarrassing to come out without running. .
     */

    static int calculator(String s) {
        int res = 0;
        int sign = 1;
        int cur = 0;
        char[] sc = s.toCharArray();

        for(int i = 0; i < sc.length; i++) {
            if(sc[i] >= '0' && sc[i] <= '9') {
                cur = 0;
                while(i < sc.length && sc[i] >= '0' && sc[i] <= '9') {
                    cur = cur * 10 + (sc[i] - '0');
                    i++;
                }
                i--;
            } else if(sc[i] == '+' || sc[i] == '-'){
                res += cur * sign;
                sign = sc[i] == '+'? 1: -1;
                cur = 0;
            }
        }
        res += cur * sign;
        System.out.println(s + " " + res);
        return res;
    }

    /**
     * LE_224_Basic_Calculator
     */
    static int calculator2(String s) {
        int res = 0;
        int sign = 1;
        int cur = 0;
        char[] sc = s.toCharArray();
        for(int i = 0; i < sc.length; i++) {
            if(sc[i] >= '0' && sc[i] <= '9') {
                cur = 0;
                while(i < sc.length && sc[i] >= '0' && sc[i] <= '9') {
                    cur = cur * 10 + (sc[i] - '0');
                    i++;
                }
                i--;
            } else if(sc[i] == '+' || sc[i] == '-'){
                res += cur * sign;
                sign = sc[i] == '+'? 1: -1;
                cur = 0;
            } else if (sc[i] == '(') {
                int count = 1, start = i++;
                while(i < sc.length && count != 0) {
                    if(sc[i] == '(') {
                        count++;
                    } else if(sc[i] == ')') {
                        count--;
                    }
                    i++;
                }
                res += calculator2(s.substring(start+1, i-1)) * sign;
                i--;
            }
        }
        res += cur * sign;
        System.out.println(s + " " + res);
        return res;
    }

    static class Cell {
        int val;
        String s;

        public Cell(int v, String ss) {
            val = v;
            s = ss;
        }
    }

/*
    import java.util. *;
import com.google.common.collect.ImmutableMap;

class GfG {
    public static void main(String args[])
    {
        // non-empty immutable set
        ImmutableMap<Integer, String> imap =
                        ImmutableMap.<Integer, String>builder()
                                                .put(1, "Geeks")
                                                .put(2, "For")
                                                .put(3, "Geeks")
                                                .build();

        // Let's print the set
        System.out.println(imap);
    }
}
*/

    static String simplify(String s, Map<String, Integer> map) {
        char[] sc = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sc.length; i++) {
            if(Character.isAlphabetic(sc[i])) {
                int start = i;
                while(i < sc.length && Character.isAlphabetic(sc[i])) {
                    i++;
                }
                String temp = s.substring(start, i);
                if(map.containsKey(temp)) {
                    sb.append(map.get(temp));
                } else {
                    sb.append(temp);
                }
                i--;
            } else {
                sb.append(sc[i]);
            }
        }
        return sb.toString();
    }

    static String calculator3 (String s, Map<String, Integer> map) {
        String ss = simplify(s, map);
        System.out.println("simplified: " + s + " --> " + ss);
        Cell res = calculator3Helper(ss);
        StringBuilder sb = new StringBuilder();
        sb.append(res.val);
        sb.append(res.s);
        System.out.println("res: " + sb.toString());
        return sb.toString();
    }

    static String update(String s) {
        char[] sc = s.toCharArray();
        for(int i = 0; i < sc.length; i++) {
            if(sc[i] == '+') {
                sc[i] = '-';
            } else if (sc[i] == '-') {
                sc[i] = '+';
            }
        }
        return new String(sc);
    }

    static Cell calculator3Helper(String s) {
        int res = 0;
        int sign = 1;
        int cur = 0;
        StringBuilder sb = new StringBuilder();
        char[] sc = s.toCharArray();
        for(int i = 0; i < sc.length; i++) {
            if(sc[i] >= '0' && sc[i] <= '9') {
                cur = 0;
                while(i < sc.length && sc[i] >= '0' && sc[i] <= '9') {
                    cur = cur * 10 + (sc[i] - '0');
                    i++;
                }
                i--;
            } else if(sc[i] == '+' || sc[i] == '-'){
                res += cur * sign;
                sign = sc[i] == '+'? 1: -1;
                cur = 0;
            } else if (sc[i] == '(') {
                int count = 1, start = i++;
                while(i < sc.length && count != 0) {
                    if(sc[i] == '(') {
                        count++;
                    } else if(sc[i] == ')') {
                        count--;
                    }
                    i++;
                }
                Cell temp = calculator3Helper(s.substring(start+1, i-1));
                res += temp.val * sign;
                if(temp.s != null) {
                    if(sign == 1) {
                        sb.append(temp.s);
                    } else {
                        sb.append(update(temp.s));
                    }
                }
                i--;
            } else if(Character.isAlphabetic(sc[i])) {
                int start = i;
                while(i < sc.length && Character.isAlphabetic(sc[i])) {
                    i++;
                }
                if(sign == 1) {
                    sb.append('+');
                } else {
                    sb.append('-');
                }
                sb.append(s.substring(start, i));
                i--;
            }
        }
        res += cur * sign;
        System.out.println("String: " + s + " val: " + res + " string: " + sb.toString());
        return new Cell(res, sb.toString());
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for(int i: array){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for(String i: set){
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for(int[] i: list){
            printArray("",i);
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for(Integer i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        // calculator("3+5-4");
        // calculator2("3+(5-(4+9)-(3-5))+2");
        Map<String, Integer> map = new HashMap<>();
        map.put("temperature", 5);
        map.put("e", 1);
        map.put("y", 2);
        calculator3("(e + 8) - (pressure + 3 - (temperature + 8) + y) +yy", map);
    }
}