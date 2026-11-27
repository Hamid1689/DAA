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
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    // Builds a balanced BST from nums[left..right] (indices, no array copying)
    private TreeNode build(int[] nums, int left, int right) {
        if (left > right) return null;          // empty part -> no node

        int mid = (left + right) / 2;           // middle element becomes the root
        TreeNode node = new TreeNode(nums[mid]);
        node.left = build(nums, left, mid - 1);     // left half -> left subtree
        node.right = build(nums, mid + 1, right);   // right half -> right subtree
        return node;
    }

    // My first idea (does NOT work well): insert numbers one by one into a BST.
    // For a sorted array every number is bigger than the previous one,
    // so the tree turns into a long chain to the right (height n). O(n^2) in total.
    public TreeNode insertOneByOne(int[] nums) {
        TreeNode root = null;
        for (int x : nums) {
            root = insert(root, x);
        }
        return root;
    }

    private TreeNode insert(TreeNode node, int x) {
        if (node == null) return new TreeNode(x);
        if (x < node.val) node.left = insert(node.left, x);
        else node.right = insert(node.right, x);
        return node;
    }
}

public class Main {
    static void preorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);
        preorder(node.left, out);
        preorder(node.right, out);
    }

    static int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static void test(int[] nums) {
        Solution s = new Solution();
        TreeNode balanced = s.sortedArrayToBST(nums);
        TreeNode chain = s.insertOneByOne(nums);
        List<Integer> out = new ArrayList<>();
        preorder(balanced, out);
        System.out.println(Arrays.toString(nums) + " -> preorder " + out
                + ", height " + height(balanced)
                + " (insert one by one: height " + height(chain) + ")");
    }

    public static void main(String[] args) {
        test(new int[]{-10, -3, 0, 5, 9});   // preorder [0, -10, -3, 5, 9], height 3
        test(new int[]{1, 3});               // preorder [1, 3], height 2
        test(new int[]{0, 1, 2, 3, 4, 5, 6, 7}); // balanced height 4, chain height 8
    }
}
