# Range Sum of BST (LeetCode 938)

## 1. Problem
I am given a BST and two numbers `low` and `high`. I need to add up the values of all nodes whose value is between `low` and `high` (including both ends) and return that sum.

## 2. Approach

**First idea (brute force).** Visit every node of the tree. If the value is in `[low, high]`, add it to the sum. This is correct, but it also visits nodes that obviously cannot be in the range.

**Final idea (using the BST rule).** For the current node:
- `root == null` -> return 0
- `root.val < low` -> this node and its whole left subtree are too small, so I only go right
- `root.val > high` -> this node and its whole right subtree are too big, so I only go left
- otherwise the node is in the range: I add `root.val` and check both sides

**Tracing, example 1:** `root = [10,5,15,3,7,null,18]`, `low = 7`, `high = 15`
1. Node 10: in the range, sum = 10. Check both sides
2. Left, node 5: 5 < 7, too small. Skip 5 and its left child 3, go right
3. Node 7: in the range, sum = 17. Both children are `null`
4. Right, node 15: in the range, sum = 32
5. Node 15 has no left child. Right child 18: 18 > 15, too big, go left -> `null`
6. Result: **32** (7 + 10 + 15). Nodes 3 and 18 were never added.

**Tracing, example 2:** `root = [10,5,15,3,7,13,18,1,null,6]`, `low = 6`, `high = 10`
1. Node 10: in range, sum = 10
2. Node 5 is too small, go right to 7: in range, sum = 17. Then 7's left child 6: in range, sum = 23
3. Node 15 is too big, go left to 13: too big, go left -> `null`
4. Result: **23**

**Challenges.** My first version was the brute force one. Then I tried to also use the BST rule but I was confused about which side to skip. I made it clear for myself: if a node is *smaller* than `low`, then everything on its *left* is even smaller, so only the right side is useful. If a node is *bigger* than `high`, only the left side is useful.

## 3. Time Complexity
**Time: O(n)** in the worst case, where `n` is the number of nodes. If the range is wide (for example it covers all values), every node must be added, so every node is visited. In the best case, if the range is small, I skip many subtrees and visit only about O(h + k) nodes, where `h` is the height and `k` is the number of nodes inside the range.

**Space: O(h)**, because of the recursion stack. For a balanced tree it is O(log n), for a chain it is O(n).

The brute force version is always O(n) time (it visits every node, even when the range is small).

## 4. Reflection / Improvement
- The BST version is faster than brute force in many cases because it skips whole subtrees, but the worst case is still O(n), because in the worst case all nodes are really inside the range and must be summed.
- Space can be improved to O(1) extra with an iterative approach that does not use the recursion stack (for example Morris traversal), but it is more complicated.
- If the same tree gets many range queries, I could keep prefix sums / subtree sums in each node, so that a query takes O(h) time.
