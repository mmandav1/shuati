package leetcode;

/**
 * Created by yuank on 10/12/18.
 */
public class LE_685_Redundant_Connection_II {
    /**
         In this problem, a rooted tree is a DIRECTED graph such that, there is exactly one node (the root) for
         which all other nodes are descendants of this node, plus every node has exactly one parent, except
         for the root node which has no parents.

         The given input is a directed graph that started as a rooted tree with N nodes
         (with distinct values 1, 2, ..., N), with one additional directed edge added.
         The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.

         The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v]
         that represents a directed edge connecting nodes u and v, where u is a parent of child v.

         Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes.
         If there are multiple answers, return the answer that occurs last in the given 2D-array.

         Example 1:
         Input: [[1,2], [1,3], [2,3]]
         Output: [2,3]
         Explanation: The given directed graph will be like this:
           1
          / \
         v   v
         2-->3

         Example 2:
         Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
         Output: [4,1]
         Explanation: The given directed graph will be like this:
         5 <- 1 -> 2
         ^    |
         |    v
         4 <- 3

         Note:
         The size of the input 2D-array will be between 3 and 1000.
         Every integer represented in the 2D-array will be between 1 and N,
         where N is the size of the input array.

         Hard
     */

    /**
     * Difference from LE_684_Redundant_Connection, it is a DIRECTED GRAPH
     *
     https://leetcode.com/problems/redundant-connection-ii/discuss/108045/C++Java-Union-Find-with-explanation-O(n)
     https://leetcode.com/problems/redundant-connection-ii/discuss/108058/one-pass-disjoint-set-solution-with-explain

     Solution from Leetcode, Time : O(n)

     This problem is very similar to "Redundant Connection". But the description on the parent/child relationships
     is much better clarified.

     There are two cases for the tree structure to be invalid.
     Case 1 : A node having two parents;
             Case 1.a.There is a loop
             Case 1.b.No loop.

     Case 2 : A circle exists : all node have one parent
        1 -> 2 -> 3 -> 4
        ^               |
        |_______________|

     If we can remove exactly 1 edge to achieve the tree structure, a single node can have at most two parents
     (has at most 2 n-degrees).
     So my solution works in two steps.

     1) Check whether there is a node having two parents.
     If so, store them as candidates A and B, and set the second edge invalid.
     2) Perform normal union find.
         If the tree is now valid
            simply return candidate B
         else if candidates not existing
            we find a circle, return current edge;
         else
            remove candidate A instead of B.
     */

    class Solution {
        public int[] findRedundantDirectedConnection(int[][] edges) {
            int[] can1 = {-1, -1};
            int[] can2 = {-1, -1};
            int[] parent = new int[edges.length + 1];

            /**
             * find the two edges point to the same child
             */
            for (int i = 0; i < edges.length; i++) {
                if (parent[edges[i][1]] == 0) {
                    parent[edges[i][1]] = edges[i][0];
                } else {
                    /**
                     * Now we find the node with two parents
                     * record the two edges point to current node i
                     * in can1 and can2.
                     */
                    can2 = new int[] {edges[i][0], edges[i][1]};
                    can1 = new int[] {parent[edges[i][1]], edges[i][1]};

                    /**
                     * remove can2
                     */
                    edges[i][1] = 0;
                }
            }

            /**
             * init for Union Find
             */
            for (int i = 0; i < edges.length; i++) {
                parent[i] = i;
            }

            /**
             * Union find, check if the graph is valid now.
             */
            for (int i = 0; i < edges.length; i++) {
                if (edges[i][1] == 0) {
                    /**
                     * bypass edge can2
                     */
                    continue;
                }
                int child = edges[i][1], father = edges[i][0];
                if (root(parent, father) == child) {
                    /**
                     * There's still loop :
                     * 1."can1[0] == -1" : can1 does not exist.
                     *   No child has two parents (case 2).
                     *   remove current edge (the last edge to form the circle)
                     * 2.can1 exists, (case then can1 must be the one to be removed.
                     */
                    if (can1[0] == -1) {
                        return edges[i];
                    }

                    return can1;
                }
                parent[child] = father;
            }

            /**
             * Union Find finish, tree is valid, return can2.
             */
            return can2;
        }

        int root(int[] parent, int i) {
            while (i != parent[i]) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }
    }
}
