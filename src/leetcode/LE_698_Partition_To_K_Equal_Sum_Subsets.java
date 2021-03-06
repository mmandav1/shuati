package leetcode;

import java.util.Arrays;

public class LE_698_Partition_To_K_Equal_Sum_Subsets {
    /**
     * Given an array of integers nums and a positive integer k, find whether it's
     * possible to divide this array into k non-empty subsets whose sums are all equal.
     *
     * Example 1:
     *
     * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * Output: True
     * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
     *
     *
     * Note:
     *
     * 1 <= k <= len(nums) <= 16.
     * 0 < nums[i] < 10000.(!!!)
     *
     * Medium
     */

    /**
     * Same logic as LE_473_Matchsticks_To_Square
     *
     * We can use the same solution because " 0 < nums[i] < 10000", the numbers
     * are all positive
     *
     * Time  : O(k ^ N) ?
     * Space : O(N)
     */
    class Solution_DFS {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            if (null == nums || nums.length < k || k <= 0) return false;

            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            if (sum % k != 0) return false;

            Arrays.sort(nums);

            return helper(nums, new boolean[nums.length], k, sum / k, 0, 0, 1);
        }

        private boolean helper(int[] nums, boolean[] visited, int k, int target, int pos, int cur, int groupId) {
            if (groupId == k) return true;
            if (cur == target) {
                return helper(nums, visited, k, target, 0, 0, groupId + 1);
            }

            if (cur > target) return false;

            for (int i = pos; i < nums.length; i++) {
                if (visited[i]) continue;

                if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) continue;

                visited[i] = true;
                if (helper(nums, visited, k, target, i + 1, cur + nums[i], groupId)) {
                    return true;
                }
                visited[i] = false;
            }

            return false;
        }
    }

    /**
     * Top-Down DFS Search
     *
     * Time Complexity: O(k ^ (N - k) * k!)
     * where N is the length of nums, and k is as given.
     * As we skip additional zeroes in groups, naively we will make O(k!) calls to search,
     * then an additional O(k ^ (N - k)) calls after every element of groups is nonzero.
     *
     * Space Complexity: O(N), the space used by recursive calls to search in our call stack.
     */
    class Solution1 {
        public boolean search(int[] groups, int row, int[] nums, int target) {
            if (row < 0) {
                return true;
            }

            int v = nums[row--];

            for (int i = 0; i < groups.length; i++) {
                if (groups[i] + v <= target) {
                    groups[i] += v;
                    if (search(groups, row, nums, target)) {
                        return true;
                    }
                    groups[i] -= v;
                }

                if (groups[i] == 0) {
                    break;
                }
            }
            return false;
        }

        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = Arrays.stream(nums).sum();
            if (sum % k > 0) return false;
            int target = sum / k;

            Arrays.sort(nums);
            int row = nums.length - 1;

            if (nums[row] > target) {
                return false;
            }

            while (row >= 0 && nums[row] == target) {
                row--;
                k--;
            }

            return search(new int[k], row, nums, target);
        }
    }

    /**
     * !!!
     * Same DFS search solution from leetcode discussion which works if given set
     * has negative values.
     *
     * Time : O(k * 2 ^ n)
     * Space : O(n)
     *
     * I think the time complexity is O(k * 2^n), at least it's an upper bound.
     * Because it takes the inner recursion 2^n time to find a good subset.
     * Once the 1st subset is found, we go on to find the second, which would
     * take 2^n roughly (because some numbers have been marked as visited).
     * So T = 2^n + 2^n + 2^n + ... = k * 2^n.
     *
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108730/JavaC%2B%2BStraightforward-dfs-solution
     */
    class Solution2 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }

            if (k <= 0 || sum % k != 0) {
                return false;
            }

            int[] visited = new int[nums.length];

            return canPartition(nums, visited, 0, k, 0, 0, sum / k);
        }

        public boolean canPartition(int[] nums, int[] visited, int start_index, int k,
                                    int cur_sum, int count, int target) {
            /**
             * !!!
             * The condition we can start canParitition() is : sum % k == 0
             * So here we have a final group and the sum of the
             * rest of the numbers must be target.
             */
            if (k == 1) {
                return true;
            }

            if(cur_sum > target) {
                return false;
            }

            /**
             * We find a group, start to find the next group,
             *
             * count variable saves the number of elements in current set.
             *
             * 如题目给定，"0 < nums[i] < 10000", 就是说，所有的数都是正整数。
             * 所以，we don't need to have the "count" param here.
             *
             * 如果给定的数组中有负数，consider the case when target = 0 and
             * some numbers are negative. Without count > 0, the algorithm
             * will consider empty set as a possible result. Therefore,
             * we must use count in condition "cur_sum == target && count > 0"
             * to tell a valid set is formed.
             */
            if (cur_sum == target && count > 0) {
                return canPartition(nums, visited, 0, k - 1,
                        0, 0, target);
            }

            for (int i = start_index; i < nums.length; i++) {
                if (visited[i] == 0) {
                    visited[i] = 1;
                    if (canPartition(nums, visited, i + 1, k, cur_sum + nums[i], count++, target)) {
                        return true;
                    }
                    visited[i] = 0;
                }
            }
            return false;
        }
    }

    /**
     * DP Solution
     *
     * Time Complexity: O(N * 2^N), where N is the length of nums. There are 2^N
     * states of used (or state in our bottom-up variant), and each state performs
     * O(N) work searching through nums.
     *
     * Space Complexity: O(2^N),the space used by memo (or dp, total in our bottom-up variant).
     */
    class Solution3 {
        public boolean canPartitionKSubsets(int[] nums, int k) {
            int N = nums.length;
            Arrays.sort(nums);
            int sum = Arrays.stream(nums).sum();
            int target = sum / k;
            if (sum % k > 0 || nums[N - 1] > target) return false;

            boolean[] dp = new boolean[1 << N];
            dp[0] = true;
            int[] total = new int[1 << N];

            for (int state = 0; state < (1 << N); state++) {
                if (!dp[state]) continue;
                for (int i = 0; i < N; i++) {
                    int future = state | (1 << i);
                    if (state != future && !dp[future]) {
                        if (nums[i] <= target - (total[state] % target)) {
                            dp[future] = true;
                            total[future] = total[state] + nums[i];
                        } else {
                            break;
                        }
                    }
                }
            }
            return dp[(1 << N) - 1];
        }
    }
}
