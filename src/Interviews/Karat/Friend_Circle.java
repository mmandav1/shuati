package Interviews.Karat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Friend_Circle {
    /**
     * 1st Question: Output the friendlist of all employees -> just save it with a map and print it
     * (This is an undirected graph, eg: 1 and 2 are friends, there must be 1 in the list of 2)
     * 2nd Question: Output how many friends of each department belong to other departments
     * -> Just traverse it once
     * 3rd Question: Output whether all employees are in a social circle
     * -> What I thought was to find a point at random and traverse it with DFS. If all the points are traversed, return true, otherwise it will be false.
     *
     * There is an employList
     * String[] employeesInput = {
     * "1,Richard,Engineering",
     * "2,Erlich,HR",
     * "3,Monica,Business",
     * "4,Dinesh,Engineering",
     * "6,Carla,Engineering",
     * "9,Laurie,Directors"
     * };
     * A friendshipList, the friendship relationship is two-way
     * String[] friendshipsInput = {
     * "1,2",
     * "1,3",
     * "1,6",
     * "2,4"
     * };
     * 1. Write a function to return the adjacency list of everyone's friend
     * For example, return in this example
     * 1: 2 3 6
     * 2: 1 4
     * 3: 1
     * 4: 2
     * 6:1
     * 9:
     *
     * ###
     * The topic is the expansion of the original OA, the employee problem. For two lists, the first list stores the id, name, and company name of each person.
     * The second list saves friendship tuples.
     *
     * Then there are two questions. The first question is to return everyone's friendlist.
     * The second question is to return how many employees each company has, and how many of these employees have friends from outside companies.
     * Then both questions must give time and space complexity. The graph problem, the second question is the time complexity is O(E+V)
     */

    public static Map<Integer, Set<Integer>> generateMap(String[] employees, String[] friendships) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (String employee: employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);
            map.put(employeeId, new HashSet<>());
        }

        for (String friendship: friendships) {
            String[] parts = friendship.split(",");
            int id1 = Integer.parseInt(parts[0]);
            int id2 = Integer.parseInt(parts[1]);
            map.get(id1).add(id2);
            map.get(id2).add(id1);
        }

        return map;
    }

    public static Map<String, int[]> getDepartmentStat(String[] employees, String[] friendships) {
        Map<String, int[]> res = new HashMap<>();
        Map<Integer, Set<Integer>> friendsMap = generateMap(employees, friendships);

        Map<String, Set<Integer>> departmentsMap = new HashMap<>();
        for (String employee: employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);

            if (!departmentsMap.containsKey(parts[2])) {
                departmentsMap.put(parts[2], new HashSet<>());
                res.put(parts[2], new int[]{0, 0});
            }

            departmentsMap.get(parts[2]).add(employeeId);
            res.get(parts[2])[0]++;
        }

        for (String employee: employees) {
            String[] parts = employee.split(",");
            int employeeId = Integer.parseInt(parts[0]);
            String department = parts[2];
            Set<Integer> employeeSet = departmentsMap.get(department);
            Set<Integer> friendsList = friendsMap.get(employeeId);

            for (int friend: friendsList) {
                if (!employeeSet.contains(friend)) {
                    res.get(department)[1]++;
                    break;
                }
            }
        }

        return res;
    }
}