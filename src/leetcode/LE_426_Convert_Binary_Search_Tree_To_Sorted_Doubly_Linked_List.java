package leetcode;

import common.TreeNode;

import java.util.Stack;

public class LE_426_Convert_Binary_Search_Tree_To_Sorted_Doubly_Linked_List {
    /**
     * Convert a BST to a sorted circular doubly-linked list in-place.
     * Think of the left and right pointers as synonymous to the previous
     * and next pointers in a doubly-linked list.
     *
     * Specifically, we want to do the transformation in place. After the
     * transformation, the left pointer of the tree node should point to its
     * predecessor, and the right pointer should point to its successor.
     * We should return the pointer to the first element of the linked list.
     *
     * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
     */

    class Solution1_Practice {
        TreeNode head;
        TreeNode tail;

        public TreeNode treeToDoublyList(TreeNode root) {
            if (root == null) {
                return null;
            }

            helper(root);

            head.left = tail;
            tail.right = head;

            return head;
        }

        private void helper(TreeNode node) {
            if (node == null) return;

            helper(node.left);

            if (tail != null) {
                tail.right = node;
                node.left = tail;
            } else {
                head = node;
            }

            tail = node;//!!!

            helper(node.right);
        }

    }

    /**
     * Recursion
     * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/solution/
     */
    class Solution1 {
        /**
         * !!!
         * The smallest (first) and the largest (last) nodes
\        */
        TreeNode first = null;
        TreeNode last = null;

        public TreeNode treeToDoublyList(TreeNode root) {
            if (root == null) return null;

            helper(root);

            /**
             * make DLl circular, as required
             */
            last.right = first;
            first.left = last;

            /**
             * return first, NOT root
             */
            return first;
        }

        /**
         * Key Insights :
         * Still using BST property - its inorder traversal sequence is sorted.
         *
         * Keep tracking the min and max value with first and last, link nodes together
         */
        public void helper(TreeNode node) {
            if (node != null) {
                // left
                helper(node.left);

                /**
                 * After the last line, first and last should point to the
                 * min value node and max value node in current node's left
                 * sub-tree, link accordingly. Here "right" acts as "next"
                 * and "left" acts as "pre" in DLL.
                 */
                if (last != null) {
                    // link the previous node (last)
                    // with the current one (node)
                    last.right = node;
                    node.left = last;
                } else {
                    // keep the smallest node
                    // to close DLL later on
                    first = node;
                }
                /**
                 * !!!
                 * This is where we update last.
                 * Before going into right subtree, last node is current node.
                 */
                last = node;

                // right
                helper(node.right);
            }
        }

    }

    /**
     * Iterative with Stack
     *
     * Just modify the inorder iterative procedure
     */
    class Solution2 {
        public TreeNode treeToDoublyList(TreeNode root) {
            if (root == null) {
                return null;
            }

            TreeNode start = root;
            TreeNode prev = null;

            Stack<TreeNode> stack = new Stack<TreeNode>();

            while (start.left != null) {
                stack.push(start);
                start = start.left;
            }

            /**
             * !!! now cur should be start
             */
            TreeNode cur = start;

            while (!stack.isEmpty() || cur != null) {
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }

                cur = stack.pop();
                if (prev != null) {
                    prev.right = cur;
                    cur.left = prev;
                }

                prev = cur;
                cur = cur.right;
            }

            start.left = prev;
            prev.right = start;
            return start;
        }
    }
}
