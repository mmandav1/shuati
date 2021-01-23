package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rectangles_In_Matrix {
    /**
     * 1. Given a matrix, each element in the matrix is ​​1, but there is a rectangular area distributed in it, and the elements in this rectangular area are 0.
     * Knowing that the 0 inside can form a rectangle, it is required to return the coordinates of the upper left corner and the lower right corner of the rectangle
     *
     * 2. If there are multiple cuboids composed of 0 in the Matrix, please return multiple sets of values ​​(provided that every two cuboids will not be connected (!!!), so rest assured).
     * It is required to output the position of each rectangle (expressed by the coordinates of the upper left corner element and the lower right corner element of the rectangle, the input must be valid,
     * Ensure that there is a rectangle that meets the requirements).
     *
     * It is required not to change the input method (!!!)
     *
     * example：
     * input:
     * [
     * [1,1,1,1,1,1],
     * [0,0,1,0,1,1],
     * [0,0,1,0,1,0],
     * [1,1,1,0,1,0],
     * [1,0,0,1,1,1]
     * ]
     * output:
     * [
     * [1,0,2,1],
     * [1,3,3,3],
     * [2,5,3,5],
     * [4,1,4,2]
     * ]
     *
     * 3. But there is a third question, which is connected components
     * The third question is basically leetcode connected components, it just returns a list of lists, each list is all the point coordinates of a component
     * That graph is a matrix composed of 1,0, and 0 composed of various graphs.
     *
     * It is really not related to the previous one. If there are multiple irregular shapes in the matrix, return these shapes. Here you need to think about and define what it means to "return these shapes"
     *
     * #
     * The question is to give a matrix composed of 01, in which there is a rectangle composed of 0, and the rest are 1, find the upper left and lower right coordinates of this rectangle.
     * Traverse the matrix to find the first zero (?) in the four directions up, down, left, and right, and break after getting the upper left, lower right boundary.
     * Space complexity when asking questions. Testing all conceivable corner cases is this step which takes a lot of time.
     *
     * follow up: There is more than one 0 matrix in the matrix, find the upper left, lower right coordinates of all matrices and return. . visit 1point3acres for more.
     * The idea is to use boolean[][] color to mark the matrix elements, dfs finds all adjacent 0s of the unmarked 0 elements, and then color is set to true to avoid repeated searches
     */

    static int[] rectangle1(int[][] matrix) {
        int[] res = new int[4];
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    int iRight = i, jDown = j;
                    while (iRight < row) {
                        if (matrix[iRight][j] == 0) {
                            iRight++;
                        } else {
                            break;
                        }
                    }

                    while (jDown < col) {
                        if (matrix[i][jDown] == 0) {
                            jDown++;
                        } else {
                            break;
                        }
                    }

                    return new int[]{i, j, iRight - 1, jDown - 1};
                }
            }
        }

        return res;
    }

    static List<int[]> rectangleMulti(int[][] matrix) {
        List<int[]> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    int iRight = i, jDown = j;
                    while (iRight < row) {
                        if (matrix[iRight][j] == 0) {
                            iRight++;
                        } else {
                            break;
                        }
                    }

                    while (jDown < col) {
                        if (matrix[i][jDown] == 0) {
                            jDown++;
                        } else {
                            break;
                        }
                    }

                    res.add(new int[]{i, j, iRight - 1, jDown - 1});

                    /**
                     * This is the algorithm to change the input, if you don’t change the input, you need to use another one
                     * visited[][] records the units that have been visited.
                     */
                    fill(i, j, iRight - 1, jDown - 1, matrix);
                }
            }
        }
        printListArray("", res);
        return res;
    }

    static List<List<int[]>> irregular(int[][] matrix) {
        List<List<int[]>> res = new ArrayList<>();
        int row = matrix.length, col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    List<int[]> cur = new ArrayList<>();
                    dfs(matrix, i, j, row, col, cur);
                    res.add(cur);
                }
            }
        }
        for (List<int[]> r : res) {
            printListArray("", r);
        }
        return res;
    }

    static void dfs(int[][] matrix, int i, int j, int row, int col, List<int[]> cur) {
        int [] [] dir = new int [] [] {{- 1, 0}, {1, 0}, {0, -1}, {0, 1}};
        if (i < 0 || i >= row || j < 0 || j >= col || matrix[i][j] == 1) {
            return;
        }
        cur.add(new int[]{i, j});
        matrix[i][j] = 1;
        for (int[] k : dir) {
            int i2 = i + k[0], j2 = j + k[1];
            dfs(matrix, i2, j2, row, col, cur);
        }
    }

    static void fill(int i1, int j1, int i2, int j2, int[][] matrix) {
        for (int i = i1; i <= i2; i++) {
            for (int j = j1; j <= j2; j++) {
                matrix[i][j] = 1;
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

    // Driver Code
    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 1, 1},
                {0, 0, 1, 0, 1, 0},
                {1, 1, 1, 0, 1, 0},
                {1, 0, 0, 1, 1, 1}
        };

        int[][] matrix2 = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 1, 0},
                {0, 1, 0, 0, 1, 0},
                {1, 0, 0, 1, 0, 1}
        };

        printArray("only one ", rectangle1(matrix));
        rectangleMulti(matrix);
        irregular(matrix2);
    }
}