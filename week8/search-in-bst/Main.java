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
    // Search in a BST: use the BST property to skip half of the tree each step
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;          // not found
        if (root.val == val) return root;       // found
        if (val < root.val) {
            return searchBST(root.left, val);   // smaller -> only the left side can contain it
        }
        return searchBST(root.right, val);      // bigger -> only the right side can contain it
    }

    // My first (brute force) idea: ignore the BST property and check every node. O(n)
    public TreeNode searchBruteForce(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        TreeNode left = searchBruteForce(root.left, val);
        if (left != null) return left;
        return searchBruteForce(root.right, val);
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

    static void test(Integer[] arr, int val) {
        TreeNode root = build(arr);
        List<Integer> out = new ArrayList<>();
        preorder(new Solution().searchBST(root, val), out);
        List<Integer> out2 = new ArrayList<>();
        preorder(new Solution().searchBruteForce(root, val), out2);
        System.out.println("val = " + val + " -> " + out + " (brute force: " + out2 + ")");
    }

    public static void main(String[] args) {
        test(new Integer[]{4, 2, 7, 1, 3}, 2);  // expected [2, 1, 3]
        test(new Integer[]{4, 2, 7, 1, 3}, 5);  // expected [] (null)
        test(new Integer[]{4, 2, 7, 1, 3}, 4);  // expected [4, 2, 1, 3, 7]
    }
}
