import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

class Solution {
    // Range Sum of BST: add the node if it is in [low, high],
    // and visit only the sides of the tree that can still contain such values
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) return 0;

        if (root.val < low) {
            // root and everything on its left are too small
            return rangeSumBST(root.right, low, high);
        }
        if (root.val > high) {
            // root and everything on its right are too big
            return rangeSumBST(root.left, low, high);
        }
        // root is inside the range: count it and check both sides
        return root.val
                + rangeSumBST(root.left, low, high)
                + rangeSumBST(root.right, low, high);
    }

    // My first (brute force) idea: visit every node and check the range. O(n)
    public int rangeSumBruteForce(TreeNode root, int low, int high) {
        if (root == null) return 0;
        int sum = 0;
        if (root.val >= low && root.val <= high) {
            sum += root.val;
        }
        sum += rangeSumBruteForce(root.left, low, high);
        sum += rangeSumBruteForce(root.right, low, high);
        return sum;
    }
}

public class Main {

    // Builds a tree from a level-order array (null = no node), like LeetCode does
    static TreeNode build(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null) return null;
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode cur = queue.poll();
            if (i < arr.length && arr[i] != null) {
                cur.left = new TreeNode(arr[i]);
                queue.add(cur.left);
            }
            i++;
            if (i < arr.length && arr[i] != null) {
                cur.right = new TreeNode(arr[i]);
                queue.add(cur.right);
            }
            i++;
        }
        return root;
    }

    // Prints the tree in preorder (root, left, right)
    static void preorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);
        preorder(node.left, out);
        preorder(node.right, out);
    }

    static void test(Integer[] arr, int low, int high) {
        TreeNode root = build(arr);
        Solution s = new Solution();
        System.out.println("[" + low + ", " + high + "] -> " + s.rangeSumBST(root, low, high)
                + " (brute force: " + s.rangeSumBruteForce(root, low, high) + ")");
    }

    public static void main(String[] args) {
        test(new Integer[]{10, 5, 15, 3, 7, null, 18}, 7, 15);               // expected 32
        test(new Integer[]{10, 5, 15, 3, 7, 13, 18, 1, null, 6}, 6, 10);     // expected 23
    }
}
