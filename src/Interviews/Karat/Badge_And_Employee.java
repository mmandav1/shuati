package Interviews.Karat;

import java.util. *;

public class Badge_And_Employee {
    /**
     * #1
     * We are working on a security system for a badged-access room in our company's building.
     * Given an ordered list of employees who used their badge to enter or exit the room, write
     * a function that returns two collections:
     *
     * 1. All employees who didn't use their badge while exiting the room
     * – they recorded an enter without a matching exit.
     * 2. All employees who didn't use their badge while entering the room  
     * – they recorded an exit without a matching enter.
     *
     * #2
     * We want to find employees who badged into our secured room unusually often.
     * We have an unordered list of names and access times over a single day. Access
     * times are given as three or four-digit numbers using 24-hour time, such as "800"
     * or "2250".
     *
     * Write a function that finds anyone who badged into the room 3 or more times in a
     * 1-hour period, and returns each time that they badged in during that period.
     * (If there are multiple 1-hour periods where this was true, just return the first one.)
     *
     * #3
     * NEW!!!
     * Given a list of people who enter and exit, find the maximal group of people who were inside
     * together at least 2 times and output the group and the times they were in together.
     *
     * All enters/exits are valid.
     *
     * ###
     * 1. A company records the log of the access control system to check whether anyone has entered illegally. The log is a list, the list has an order, and each item is a pair.
     * The name of the person in front of the string, followed by the string "enter" or "exit", a person’s entry and exit records are considered legal if they are entered first and then exit
     *
     * input: the list described above
     * return: Two lists record people who enter and leave illegally, the first list record has only enter records but no exit records, and the second list record only has exit
     * People who remember yes but have no record of enter
     * Note: 1. For the same person, enter must appear before exit. If a person exits first and then enters, there is a problem. This person should appear once in both return lists
     * 2. Everyone can enter and exit multiple times, as long as there is one violation of the above requirements, they need to be added to the returned list according to the situation
     *
     *
     * 2. The background is the same as the first question, except that the content of the log record is different. Each item in the list is still a pair. The name of the string is in the front, which will not be repeated. The back is another list.
     * Record the time log of the person entering the door in the list (no longer consider going out)
     *
     * input: the list described above
     * return: a list, each item is a pair, the former item is the name of the person, the latter item is a list, the content of the list is to filter the person’s time log,
     * If there are three or more consecutive records appearing within one hour, these records need to appear in the returned list, all of which do not overlap
     * This case must be recorded
     * Note: 1. The time display method 1350 means 1:50, 1400 means two o'clock and the time is 60 rounds, but the number given is normal 100 before the rounds, and the time difference should be considered when calculating the time difference.
     * 2. If overlap is recorded in the time log ([1350, 1400, 1450, 1500] the first three records and the last three records are within one hour),
     * Just return the first three records, the interviewer told me that this will not happen in the testcase
     *
     * The third channel is probably the largest group in the room at the same time, and requires the group to appear twice. Output group and the period of time when the group is together.
     */

    static void getGroup(String[][] records) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> exitWithoutBadge = new HashSet<>();
        Set<String> enterWithoutBadge = new HashSet<>();

        for (String[] record : records) {
            Integer prev = map.get(record[0]);
            if (record[1].equals("enter")) {
                if (prev != null && prev == 1) {
                    exitWithoutBadge.add(record[0]);
                }
                prev = 1;
            } else {
                if (prev == null || prev == 0) {
                    enterWithoutBadge.add(record[0]);
                }
                prev = 0;
            }
            map.put(record[0], prev);
        }

        for (String person : map.keySet()) {
            if (map.get(person) > 0) {
                exitWithoutBadge.add(person);
            }
        }

        printSet("enter without badge ", enterWithoutBadge);
        printSet("exit without badge ", exitWithoutBadge);
    }

    static Map<String, List<Integer>> security(String[][] records) {
        Map<String, List<Integer>> map = new HashMap<>();
        for (String[] record : records) {
            if (!map.containsKey(record[0])) {
                map.put(record[0], new ArrayList<>());
            }
            map.get(record[0]).add(Integer.parseInt(record[1]));

        }
        Map<String, List<Integer>> res = new HashMap<>();
        for (String person : map.keySet()) {
            if (map.get(person).size() >= 3) {
                List<Integer> cur = map.get(person);
                Collections.sort(cur);
                for (int i = 0; i < cur.size(); i++) {
                    int index = oneHour(cur, i);
                    if (index - i >= 3) {
                        List<Integer> temp = new ArrayList<>();
                        while (i < index) {
                            temp.add(cur.get(i));
                            i++;
                        }
                        res.put(person, temp);
                        break;
                    }
                }
            }
        }
        printMap("secure ", res);
        return res;
    }

    static int oneHour(List<Integer> list, int startIndex) {
        int endVal = list.get(startIndex) + 100;
        int endPos = startIndex;
        while (endPos < list.size()) {
            if (list.get(endPos) <= endVal) {
                endPos ++;
            } else {
                break;
            }
        }
        return endPos;
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for (String i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[]) {
        String[][] records = new String[][]{
                {"Martha", "exit"},
                {"Paul", "enter"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "enter"},
                {"Paul", "enter"},
                {"Curtis", "enter"},
                {"Paul", "exit"},
                {"Martha", "enter"},
                {"Martha", "exit"},
                {"Jennifer", "exit"},
        };

        String[][] records2 = new String[][]{
                {"Paul", "1355"},
                {"Jennifer", "1910"},
                {"John", "830"},
                {"Paul", "1315"},
                {"John", "835"},
                {"Paul", "1405"},
                {"Paul", "1630"},

                {"John", "855"},

                {"John", "915"},

                {"John", "930"},

                {"Jennifer", "1335"},

                {"Jennifer", "730"},

                {"John", "1630"},

        };
        getGroup(records);
        security(records2);
    }
}
