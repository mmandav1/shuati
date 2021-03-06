package leetcode;

import java.util.*;

public class LE_815_Bus_Routes {
    /**
         We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever.
         For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels
         in the sequence 1->5->7->1->5->7->1->... forever.

         We start at bus stop S (initially not on a bus), and we want to go to bus stop T.
         Travelling by buses only, what is the least number of buses we must take to reach our
         destination? Return -1 if it is not possible.

         Example:
         Input:
         routes = [[1, 2, 7], [3, 6, 7]]

         S = 1
         T = 6
         Output: 2

         Explanation:
         The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
         Note:

         1 <= routes.length <= 500.
         1 <= routes[i].length <= 500.
         0 <= routes[i][j] < 10 ^ 6.

         Hard
     **/

    /**
     * BFS
     * http://zxi.mytechroad.com/blog/graph/leetcode-815-bus-routes/
     *
     * 1.Build Graph in HashMap, Stop -> All bus numbers that pass current stop (!!!)
     *
     * 2.Stop -> all stops that can be reached by taking one bus that passes current stop.
     *
     * 3.Record stop/bus, never stop at the same location or take the same bus twice.
     *
     * Time  : O(m * n),  m: # of buses, n: # of routes
     * Space : O(m * n + m)
     *
     * Example :
     *
     * [
     *  [1, 2, 7], //bus #0
     *  [3, 6, 7]  //bus #1
     * ]
     *
     * map :
     * stop number -> list of bus number
     * 1 -> 0
     * 2 -> 0
     * 3 -> 1
     * 6 -> 1
     * 7 -> 0, 1
     *
     * S = 1, T =6
     *
     * init
     * q : 1
     *
     * buses = 1
     * curStop : 1 -> busNum : 0, set visited[0] to true, check all stops of bus #0, none of them is T,
     *                put all stops of bus #0 into q, q : 1, 2, 7
     *
     * buses = 2
     * curStop : 1 -> busNum : 0, visited[0] = true, continue;
     *           2 -> busNum : 0, visited[0] = true, continue;
     *           7 -> busNum : 0, visited[0] = true, continue;
     *             -> busNum : 1, visited[1] = false, set visited[1] = true,
     *                check all stops of bus # 1 :  3, not T, put 3 into q
     *                                              6, equals T, return buses : 2
     *
     *
     * BFS levels :
     *                   stop 1
     *                     |
     *           Bus #0 {stop 2, stop 7}
     *           Bus #0 marked as visited
     *           mapping stop 2, 7 to bus number: {0, 1}
     *                     / \
     *               Bus #0   Bus #1
     *              visited   not visited
     *                        {3, 6, 7}
     *                        stop 3, stop 6 == T, return
     *
     * So the key to understand it : for each BFS level, q saves stop numbers, after we pop out a stop number,
     * we use the map which maps stop number to all stops that can be reached from current stop, then check if
     * destination is in any of the bus routes.
     *
     * Notice, we need to get size of q first then pop out #size of stop numbers from q, this is one level
     * in BFS.
     *
     * For each BFS level, since we don't take bus that is already visited, we only check stops of the buses that
     * we haven't taken, in other words, all the stops that we put into q for the next level are stops belong to
     * new buses, this guarantees that we can increase bus count for each level, or for each level, we are taking
     * a new bus.
     *
     * 1.BFS level: all the bus stop numbers that can be reached from the given stop (by taking all buses that
     *   have a stop at this given stop)
     * 2.Visited : we only take a bus once, so the visited is marked on buses.
     *
     */
    class Solution {
        public int numBusesToDestination(int[][] routes, int S, int T) {
            if (S == T || null == routes || routes.length == 0) {
                return 0;
            }

            /**
             * key   : stop number
             * value : list of buses that stop at this stop number
             */
            Map<Integer, List<Integer>> map = new HashMap<>();

            int m = routes.length;

            for (int i = 0; i < routes.length; i++) {
                for (int j = 0; j < routes[i].length; j++) {
                    int stop = routes[i][j];
                    if (!map.containsKey(stop)) {
                        map.put(stop, new ArrayList<>());
                    }
                    map.get(stop).add(i);//i is bus number
                }
            }

            /**
             * !!!
             * m is number of buses, visited is to record which buses have been taken.
             */
            boolean[] visited = new boolean[m];
            Queue<Integer> q = new LinkedList<>();
            q.offer(S);
            int buses = 0;

            while (!q.isEmpty()) {
                int size = q.size();//!!!
                buses++; //!!!

                for(int i = 0; i < size; i++) {//!!!
                    int curStop = q.poll();

                    /**
                     * 这个循环检查所有通过当前站的bus所经过的站
                     */
                    for (int busNum : map.get(curStop)) {
                        if (visited[busNum]) {
                            continue;
                        }

                        visited[busNum] = true;
                        for (int s : routes[busNum]) {
                            if (s == T) {
                                return buses;
                            }

                            q.offer(s);
                        }
                    }
                }
            }

            return -1;
        }
    }

    class Solution_Practice {
        public int numBusesToDestination(int[][] routes, int S, int T) {
            if (S == T) return 0;

            int m = routes.length;

            /**
             * what bus routes pass through current station
             */
            Map<Integer, Set<Integer>> map = new HashMap<>();
            for (int i = 0; i < m; i++) {
                /**
                 * 变长数组，每个子数组的长度是不同的。
                 */
                for (int j = 0; j < routes[i].length; j++) {
                    int stop = routes[i][j];
                    map.putIfAbsent(stop, new HashSet<>());
                    map.get(stop).add(i);
                }
            }

            // for (Map.Entry<Integer,Set<Integer>> entry : dist.entrySet()) {
            //     System.out.println(entry.getKey() + " -> " + Arrays.toString(entry.getValue().toArray()));
            // }

            Queue<Integer> q = new LinkedList<>();
            boolean[] visited = new boolean[m];

            q.offer(S);
            for (int bus : map.get(S)) {
                visited[bus] = true;
                for (int stop : routes[bus]) {
                    q.offer(stop);
                }
            }

            int res = 1;

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    int cur = q.poll();

                    if (cur == T) return res;

                    for (int bus : map.get(cur)) {
                        if (visited[bus]) continue;

                        visited[bus] = true;
                        for (int stop : routes[bus]) {
                            q.offer(stop);
                        }
                    }
                }
                res++;
            }

            return -1;
        }
    }
}