package Interviews.Karat;

import java.util. *;

public class Student_And_Class {
    /**
     * First question:
     * You are a developer for a university. Your current project is to develop a system for students
     * to find courses they share with friends. The university has a system for querying courses students
     * are enrolled in, returned as a list of (ID, course) pairs.
     *
     * Write a function that takes in a list of (student ID number, course name) pairs and returns,
     * for every pair of students, a list of all courses they share.
     *
     * Sample Input:
     *
     * student_course_pairs_1 = [
     *   ["58", "Software Design"],
     *   ["58", "Linear Algebra"],
     *   ["94", "Art History"],
     *   ["94", "Operating Systems"],
     *   ["17", "Software Design"],
     *   ["58", "Mechanics"],
     *   ["58", "Economics"],
     *   ["17", "Linear Algebra"],
     *   ["17", "Political Science"],
     *   ["94", "Economics"],
     *   ["25", "Economics"],
     * ]
     *
     * Sample Output (pseudocode, in any order):
     *
     * find_pairs(student_course_pairs_1) =>
     * {
     *   [58, 17]: ["Software Design", "Linear Algebra"]
     *   [58, 94]: ["Economics"]
     *   [58, 25]: ["Economics"]
     *   [94, 25]: ["Economics"]
     *   [17, 94]: []
     *   [17, 25]: []
     * }
     *
     * Additional test cases:
     *
     * Sample Input:
     *
     * student_course_pairs_2 = [
     *   ["42", "Software Design"],
     *   ["0", "Advanced Mechanics"],
     *   ["9", "Art History"],
     * ]
     *
     * Sample output:
     *
     * find_pairs(student_course_pairs_2) =>
     * {
     *   [0, 42]: []
     *   [0, 9]: []
     *   [9, 42]: []
     * }
     *
     * Second question:
     * Tell you that a is a prerequisite for b and b is a prerequisite for c, and ask you what the mid course is. And the condition is that there is only one order in which to complete the course, here is a -> b -> c
     * So mid course is b
     *
     * #
     * 1. An array of student IDs and course names. It is necessary to output the common courses of any two students.
     * 2. A set of course names, an array of course names, representing the pre course, to ensure that one line can be completed, and output the course at position n/2
     * 3. The input is the same as before, each course may have multiple pre and multiple follow courses, and output all n/2 courses
     *
     * It took me 30 minutes to finish the first question... Mainly because I wanted to write with lst at the beginning, halfway through the writing, I found that using set can save more time, and then changed the name and changed the mess, various syntax errors , It's actually typo too shameful.
     * The first question is a hash table, and then remember all the student ids, and then the respective Set of the double loop intersect will do.
     * Then the second question was written for 5 minutes. Hash table + a set, and then use a course that continuously remove appears in the second position so that the beginning course is left (anyway, it's all about n complexity).
     * For the third question, he said that you don't have much time, just talk about your ideas. I babbled a bit, and then it's over.
     *
     * #
     * 3. The first question, a list, [[A, math,....],[B, math, yuwen,...],[C, yuwen,tiyu...]],
     * Find the same courses for each pair of students, and output a list. I use hash map, then set(A)&set(B) to do this.
     *
     * 4. The second question: is the prerequisites course and course topics, let you connect into a string (assume is just connected into a string)
     *, similar to the question of JFK airpot Itinerary in leco, but did not tell you the starting point, you have to find it yourself, and then return to the middle class.
     *
     * 5. The third question: The follow up of the second question can not only be connected in a series, but can be connected in several series, and then find the middle class of each series.
     *
     * Not difficult, each question has three cases for you to run, no need to consider the corner case. Do it fast, I guess there should be endless questions, the more you do, the better.
     * It's just that I only did three courses, and the third course stopped as soon as I finished the first draft. I don't know whether it's right or wrong. .
     *
     * The first question, for example, a and b have the same math class, c and d have the same two classes, math and pe, a and e have no class the same, then our output is {(a,b):[math ], (a,e):[], (c,d):[PE,Math]}
     *
     * The second question: For example, a is the pre-course of b, and b is the pre-course of c, and the connection is abc, then the middle class is b
     *
     * I feel that the first question can only be a double loop, there is no easy way. There are not necessarily only three lessons in the second question, right? Yes not necessarily many
     *
     *
     *  #
     * 1. The courses chosen for each student, find the intersection of the courses of every two students
     * 2. Followup gives a pair of prerequisite courses. You need to sort out the chain to find out the courses of n//2
     * Very similar to the second essentials
     *
     * #
     * Q1. Give a student id => list of taking courses such an input, and find the commonly taking courses between each pair of students.
     *
     * Q2. Give a vector of [prerequisite course, next course], ensure that there is only one starting course and ending course, and all non-starting courses have only one precursor,
     * All non-ending courses have only one successor (ie the relationship of prerequisite is a simple path that includes all courses). Then the middle course is defined as the middle of this path,
     * If there is an even number of courses, then the two in the middle are in front. Seek middle course.
     *
     * Q3. The relationship between follow up and prerequisite of Q2 is a directed acyclic graph. Find all possible middle courses. My approach is to directly do dfs for each starting course.
     *
     * Students may decide to take different "tracks" or sequences of courses in the Computer Science curriculum. There may be more than one track that includes the same course, but each student follows a single linear track from a "root" node to a "leaf" node. In the graph below, their path always moves left to right.
     *
     * Write a function that takes a list of (source, destination) pairs, and returns the name of all of the courses that the students could be taking when they are halfway through their track of courses.
     *
     * Sample input:
     * all_courses = [
     *     ["Logic", "COBOL"],
     *     ["Data Structures", "Algorithms"],
     *     ["Creative Writing", "Data Structures"],
     *     ["Algorithms", "COBOL"],
     *     ["Intro to Computer Science", "Data Structures"],
     *     ["Logic", "Compilers"],
     *     ["Data Structures", "Logic"],
     *     ["Creative Writing", "System Administration"],
     *     ["Databases", "System Administration"],
     *     ["Creative Writing", "Databases"],
     * ]
     *
     * Sample output (in any order):
     *      ["Creative Writing", "Databases", "Data Structures"]
     *
     * Visual representation:
     *                                           ______________
     * ____________                              |            |
     * |          |        ______________     -->| Algorithms |--\     _____________
     * | Intro to |        |            |    /   |____________|   \    |           |
     * | C.S.     |---\    | Data       |   /                      >-->| COBOL     |
     * |__________|    \   | Structures |--+     ______________   /    |___________|
     *                  >->|____________|   \    |            |  /
     * ____________    /                     \-->| Logic      |-+      _____________
     * |          |   /    ______________        |____________|  \     |           |
     * | Creative |  /     |            |                         \--->| Compilers |
     * | Writing  |-+----->| Databases  |                              |___________|
     * |__________|  \     |____________|-\     _________________________
     *                \                    \    |                       |
     *                 \--------------------+-->| System Administration |
     *                                          |_______________________|
     *
     *
     * '''
     *
     * all_courses = [
     *     ["Logic", "COBOL"],
     *     ["Data Structures", "Algorithms"],
     *     ["Creative Writing", "Data Structures"],
     *     ["Algorithms", "COBOL"],
     *     ["Intro to Computer Science", "Data Structures"],
     *     ["Logic", "Compilers"],
     *     ["Data Structures", "Logic"],
     *     ["Creative Writing", "System Administration"],
     *     ["Databases", "System Administration"],
     *     ["Creative Writing", "Databases"],
     * ]
     *
     * !!!
     * If the path length is an even number, take the left one as the middle
     *
     *
     * ###
     * 1. Which courses are shared by the two people, and each person maintains a set and does the intersection.
     * 2. Find the middle of a path, build a dict maintain linked list relation, find the first half of the path length and finish. Note that the path length is even and odd.
     * 3. Find the middle for multiple paths, just use the BFS version of topological sorting, and reuse the code in the second question of the part.
     */

    public static Map<String, List<String>> sharedClasses(String[] records) {
        Map<String, List<String>> res = new HashMap<>();

        Map<String, List<String>> map = new HashMap<>();
        for (String record : records) {
            String[] parts = record.split(",");
            String name = parts[0];
            String className = parts[1];

            if(!map.containsKey(name)) {
                map.put(name, new ArrayList<>());
            }
            map.get(name).add(className);
        }

        List<String> names = new ArrayList<>();
        List<List<String>> classes = new ArrayList<>();

        for (String key : map.keySet()) {
            names.add(key);
            List<String> l = map.get(key);

            Collections.sort(l, (a, b) -> a.compareTo(b));

            classes.add(l);
        }

        for (int i = 0; i < names.size() - 1; i++) {
            String s1 = names.get(i);
            List<String> l1 = classes.get(i);

            for (int j = i + 1; j < names.size(); j++) {
                String s2 = names.get(j);
                List<String> l2 = classes.get(j);

                List<String> shared = findShared(l1, l2);
                String pair = s1 + "," + s2;
                res.put(pair,shared);
            }
        }

        return res;
    }

    public static List<String> findShared(List<String> l1, List<String> l2) {
        List<String> res = new ArrayList<>();
        if (l1.size() == 0 || l2.size() == 0) return res;

//        Collections.sort(l1, (a, b) -> a.compareTo(b));
//        Collections.sort(l2, (a, b) -> a.compareTo(b));

        int m = l1.size();
        int n = l2.size();
        int p1 = 0, p2 = 0;

        while (p1 < m && p2 < n) {
            if (l1.get(p1).compareTo(l2.get(p2)) < 0) {
                p1++;
            } else if (l1.get(p1).compareTo(l2.get(p2)) > 0) {
                p2++;
            } else {
                res.add(l1.get(p1));
                p1++;
                p2++;
            }
        }

        return res;
    }

    public static String midClass(String[] classes) {
        Map<String, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (String c : classes) {
            String[] parts = c.split(",");
            map.put(parts[0], parts[1]);
            set.add(parts[0]);
            set.add(parts[1]);
        }

        for (String key : map.keySet()) {
            set.remove(map.get(key));
        }

        String start = set.iterator().next();
        System.out.println("start:" + start);
        int size = map.size();

        String cur = start;
        for (int i = 0; i < size / 2; i++) {
           String next =  map.get(cur);
           cur = next;
        }

        return cur;
    }

    /**
     * Question 3
     * Multiple class paths, there are possible multiple start classes.
     *
     * For each start class, dfs to find all possible paths, each path
     * is saved in a list, we can get mid one use index size / 2
     */
    public static List<String> midClass1(String[] classes) {
        List<String> res = new ArrayList<>();

        Map<String, List<String>> map = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (String c : classes) {
            String[] parts = c.split(",");

            if (!map.containsKey(parts[0])) {
                map.put(parts[0], new ArrayList<>());
            }
            map.get(parts[0]).add(parts[1]);

            set.add(parts[0]);
            set.add(parts[1]);
        }

        for (String key : map.keySet()) {
            List<String> l = map.get(key);
            for (String s : l) {
                set.remove(s);
            }
        }

        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String start = it.next();
            res.addAll(findMid(start, map));
        }

        return res;
    }

    private static List<String> findMid(String start, Map<String, List<String>> map) {
        List<String> res = new ArrayList<>();

        List<List<String>> paths = new ArrayList<>();
        dfs(paths, new ArrayList<>(), start, map);

        for (List<String> path : paths) {
            int size = path.size();
            int mid = size / 2;
            int idx = mid % 2 == 0 ? mid - 1 : mid;
            res.add(path.get(idx));
        }

        return res;
    }

    private static void dfs(List<List<String>> paths, List<String> temp, String cur, Map<String, List<String>> map) {
        if (!map.containsKey(cur)) {
            temp.add(cur);
            paths.add(new ArrayList<>(temp));
            temp.remove(temp.size() - 1);
            return;
        }

        temp.add(cur);
        List<String> l = map.get(cur);

        for (String next : l) {
            dfs(paths, temp, next, map);
        }
        temp.remove(temp.size() - 1);
    }


    private static void printRes1(Map<String, List<String>> map) {
        for (String key : map.keySet()) {
            List<String> l = map.get(key);
            System.out.println(key + " : " + Arrays.toString(l.toArray()));
        }
    }

    public static void main(String[] args) {
        String[] records1 = {
                "58,Software Design",
                "58,Linear Algebra",
                "94,Art History",
                "94,Operating Systems",
                "17,Software Design",
                "58,Mechanics",
                "58,Economics",
                "17,Linear Algebra",
                "17,Political Science",
                "94,Economics",
                "25,Economics"};

        String[] records2 = {
                "42,Software Design",
                "0,Advanced Mechanics",
                "9,Art History"
        };

        printRes1(sharedClasses(records1));
        printRes1(sharedClasses(records2));

        String[] records3 = {
                "1,2",
                "2,3",
                "3,4",
                "4,5"
        };

        System.out.println(midClass(records3));

    }
}