package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Treasures {
    /**
     *
     * 1. Implement findLegalMoves()
     * Legal move is where you can go next. It is very simple to return to the four points up, down, left and right if they are not walls.
     * List<int[]> steps = solution.findLegalMoves(board, start1[0], start1[1]);
     * //     int[][] board = new int[][] {
     * //       {0,  0,  0, -1, -1},
     * //       {0,  0, -1,  0,  0},
     * //       {0, -1,  0, -1,  0},
     * //       {0,  0, -1,  0,  0},
     * //       {0,  0,  0,  0,  0},
     * //       {0,  0,  0,  0,  0},
     * //       {0,  0,  0,  0,  0},
     * //     };
     *
     * 2. 实现boolean isReachable(int[][] board, int x, int y)
     * Still the same board, ask if all 0s can be reached
     *
     * Given an end point, ask whether all 0 points in the matrix can reach the end point. BFS solution
     *
     * Still this kind of board, the input is the board and a point in the board, write function to judge whether all other points can reach this point, and return true/false.
     * It's still very simple, bfs judges if all 0s are connected, I basically passed the test cases once. After the run, the interviewer asked about the complexity.
     *
     * 3.Now we have treasures, denoted by 1. Given a board and start and end positions for the player,
     * write a function to return the shortest simple path from start to end that includes all the treasures,
     * if one exists. A simple path is one that does not revisit any location.
     *
     * board3 = [
     * [  1,  0,  0, 0, 0 ],
     * [  0, -1, -1, 0, 0 ],
     * [  0, -1,  0, 1, 0 ],
     * [ -1,  0,  0, 0, 0 ],
     * [  0,  1, -1, 0, 0 ],
     * [  0,  0,  0, 0, 0 ],
     * ]
     *
     * treasure(board3, (5, 0), (0, 4)) -> None
     *
     * treasure(board3, (5, 1), (2, 0)) ->
     * [(5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2),
     * (0, 1), (0, 0), (1, 0), (2, 0)]
     *
     * treasure(board3, (0, 0), (4, 1)) ->
     *   [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (3, 2), (3, 1), (4, 1)]
     *   Or
     *   [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (4, 1)]
     *
     *
     *  #Another description:
     * This is the third question. The previous question is also very simple. I remember to judge whether we can go from A to B at two points. The first question is even simpler, give a point to return the neighbors that can pass through
     *  Now the board also includes treasures, denoted by 1.
     *
     *  Given a board and start and end positions for the player, write a function to return the shortest simple
     *  path from start to end that includes all the treasures, if one exists. If multiple shortest paths exist,
     *  return any of them. A simple path is one that does not revisit any location.
     *
     *  board3 = [
     *      [  1,  0,  0, 0, 0 ],
     *      [  0, -1, -1, 0, 0 ],
     *      [  0, -1,  0, 1, 0 ],
     *      [ -1,  0,  0, 0, 0 ],
     *      [  0,  1, -1, 0, 0 ],
     *      [  0,  0,  0, 0, 0 ],
     *  ]
     *
     *
     *  treasure(board3, (5, 0), (0, 4)) -> None
     *
     *  treasure(board3, (5, 2), (2, 0)) ->
     *    [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]
     *    Or
     *    [(5, 2), (5, 1), (4, 1), (3, 1), (3, 2), (3, 3), (2, 3), (1, 3), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (2, 0)]
     *
     *  treasure(board3, (0, 0), (4, 1)) ->
     *    [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (2, 2), (3, 2), (3, 1), (4, 1)]
     *    Or
     *    [(0, 0), (0, 1), (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 2), (3, 1), (4, 1)]
     *
     *  n: width of the input board
     *  m: height of the input board
     *
     */

    /**
     *
     * C++ code for question 1 and 2
     * #include <iostream>
     * #include <vector>
     * #include <utility>
     * #include <queue>
     * using namespace std;
     * int dp[4][5]={{0,0,0,-1,-1},{0,-1,0,-1,-1},{-1,-1,0,-1,-1},{0,0,-1,0,0}};
     * vector<pair<int,int>> dirctions{{-1,0},{1,0},{0,1},{0,-1}};
     *
     * vector<pair<int,int>> qustion1 (int row,int column,int x_size,int y_size) {
     *     vector<pair<int,int>> ans;
     *     for (auto tmp:dirctions) {
     *         int new_x=row+tmp.first;
     *         int new_y=column+tmp.second;
     *         if (new_x<0||new_x>=x_size||new_y<0||new_y>=y_size) {
     *            continue;
     *         }
     *         if (dp[new_x][new_y]==0) {
     *             ans.push_back(make_pair(new_x,new_y));
     *         }
     *     }
     *     for (auto it=ans.begin();it!=ans.end();it++) {
     *         std::cout<<it->first<<","<<it->second<<std::endl;
     *     }
     *     return ans;
     * }
     * void bfs (pair<int,int>index,int row_size,int column_size) {
     *     queue<pair<int,int>> q;
     *     q.push(index);
     *     while (!q.empty()) {
     *         int old_x=q.front().first;
     *         int old_y=q.front().second;
     *         dp[old_x][old_y]=-1;
     *         q.pop();
     *         for(auto it:dirctions) {
     *             int new_x=old_x+it.first;
     *             int new_y=old_y+it.second;
     *             if(new_x<0||new_x>=row_size||new_y<0||new_y>=column_size||dp[new_x][new_y]==-1) {
     *                 continue;
     *             }
     *             q.push(make_pair(new_x,new_y));
     *             dp[new_x][new_y]=-1;
     *         }
     *     }
     * }
     *
     * bool qustion2 (int end_row,int end_column) {
     *     //connected point is the count==1 works, because all the open area are connected
     *     int count=0;
     *     for (int i=0;i<4;i++) {
     *         for(int j=0;j<5;j++) {
     *             if(dp[j]==-1) {
     *                 continue;
     *             }
     *             bfs(make_pair(i,j),4,5);
     *             count++;
     *         }
     *     }
     *     std::cout<<count<<std::endl;
     *     if(count==1) {
     *         return true;
     *     }
     *     else {
     *         return false;
     *     }
     * }
     *
     *
     * int main ()
     * {
     *     qustion1(1,2,4,5);
     *     bool res=qustion2(2,1);
     *     return 0;
     * }
     */

    public static List<int[]> findLegalMoves(int[][] matrix, int x, int y) {
        List<int[]> res = new ArrayList<>();
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length) return res;
        if (matrix[x][y] == -1) return res;

        int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int nx = x + dir [0];
            int ny = x + dir [1];
            if (nx < 0 || nx >= matrix.length || ny < 0 || ny >= matrix[0].length) continue;
            if (matrix[nx][ny] == -1) continue;

            if (matrix[nx][ny] == 0) {
                res.add(new int[]{nx, ny});
            }
        }

        return res;
    }

    public static boolean isReachable(int[][] matrix, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || matrix[x][y] == -1) return false;

        int m = matrix.length;
        int n = matrix[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) count++;
            }
        }

        return helper(matrix, new boolean[m][n], count, x, y);
    }

    public static boolean helper(int[][] matrix, boolean[][] visited, int count, int x, int y) {
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || matrix[x][y] == -1) return false;

        if (visited[x][y]) {
            return false;
        }

        if (count == 0) {
            return true;
        }

        return helper(matrix, visited, count - 1, x + 1, y)
                || helper(matrix, visited, count - 1, x - 1, y)
                || helper(matrix, visited, count - 1, x, y + 1)
                || helper(matrix, visited, count - 1, x, y - 1);

    }



    static List <int []> treasure (int [] [] matrix, int starti, int startj, int endi, int endj) {
        int row = matrix.length, col = matrix[0].length, totalTreasure = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 1) {
                    totalTreasure ++;
                }
            }
        }

        List<List<int[]>> res = new ArrayList<>();

        dfs(matrix, starti, startj, 0, totalTreasure, endi, endj, res, new ArrayList<>());

        System.out.println(res.size());

        if (res.size() < 1) return new ArrayList<>();
        List<int[]> shortest = res.get(0);

        for (int i = 1; i < res.size(); i++) {
            if (res.get(i).size() < shortest.size()) {
                shortest = res.get(i);
            }
        }

        printListArray("", shortest);
        return shortest;
    }

    static void dfs(int[][] matrix, int i, int j, int count, int totalTreasure, int endi, int endj, List<List<int[]>> res, List<int[]> cur) {
        int row = matrix.length, col = matrix[0].length;
        int [] [] dir = new int [] [] {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

        if (i >= 0 && i < row && j >= 0 && j < col) {
            if (matrix[i][j] == 1 || matrix[i][j] == 0) {
                cur.add(new int[]{i, j});
                if (matrix[i][j] == 1) {
                    count++;
                }
                int temp = matrix[i][j];
                matrix[i][j] = 2;
                if (i == endi && j == endj && count == totalTreasure) {
                    res.add(new ArrayList<>(cur));
                    cur.remove(cur.size() - 1);
                    matrix[i][j] = temp;
                    return;
                }
                for (int[] d : dir) {
                    int newi = i + d [0], newj = j + d [1];
                    //   System.out.println(i + " " + j + " " + newi + " " + newj);
                    dfs(matrix, newi, newj, count, totalTreasure, endi, endj, res, cur);
                }
                matrix[i][j] = temp;
                cur.remove(cur.size() - 1);
            }
        }
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
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

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for (int[] i : list) {
            printArray("", i);
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

    static void printIntMap(String s, Map<Integer, Integer> map) {
        System.out.println(s);
        for (Integer i : map.keySet()) {
            System.out.println("key: " + i + " value: " + map.get(i));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {0, -1, -1, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
//        treasure(matrix, 0, 0, 2, 3);

        int[][] matrix1 = new int[][]{
                {1,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {0, -1,  0, 1, 0},
                {-1, 0,  0, 0, 0},
                {0,  1, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        int[][] matrix2 = new int[][]{
                {0,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {0, -1,  0, 1, 0},
                {0,  0,  0, 0, 0},
                {0,  0, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        treasure(matrix1, 5, 1, 2, 0);

    }

}