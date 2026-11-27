# Search in a Binary Search Tree (LeetCode 700)

## 1. Problem
I am given a binary search tree (BST) and a number `val`. I need to find the node whose value is equal to `val` and return that node (together with everything below it). If there is no such node, I return `null`.

## 2. Approach

**First idea (brute force).** I did not use the BST rule at first. I just checked every node: if the current node is `val`, return it; otherwise search the left subtree, and if nothing is found there, search the right subtree. It works, but it is slow because it can visit all `n` nodes.

**Final idea (using the BST rule).** In a BST, everything on the left of a node is smaller and everything on the right is bigger. So at each node I can decide where to go:
- `root == null` -> the value is not in the tree, return `null`
- `root.val == val` -> found, return `root`
- `val < root.val` -> the answer can only be on the left, go left
- `val > root.val` -> the answer can only be on the right, go right

So every step throws away a whole half of the tree.

**Tracing, example 1:** `root = [4,2,7,1,3]`, `val = 2`
1. Node 4: 2 != 4 and 2 < 4, go left
2. Node 2: 2 == 2, found. Return the subtree `[2,1,3]`

**Tracing, example 2:** `root = [4,2,7,1,3]`, `val = 5`
1. Node 4: 5 > 4, go right
2. Node 7: 5 < 7, go left
3. `null`: not found, return `null`

**Challenges.** At first I wrote the brute force version and did not understand why the problem says "BST". Then I noticed that a normal tree needs a full search, but a BST lets me skip one side every time. Also I had to remember to check `root == null` first, otherwise the code crashes on a missing branch.

## 3. Time Complexity
**Time: O(h)**, where `h` is the height of the tree. At each step I move one level down and never come back, so I make at most `h` steps. For a balanced tree `h = log n`, so it is O(log n). For a tree that is just a chain `h = n`, so the worst case is O(n).

**Space: O(h)**, because of the recursion stack (each call waits for the next one, up to `h` calls).

The brute force version is O(n) time, because it may visit every node, and O(h) space.

## 4. Reflection / Improvement
- Compared to brute force, the BST version is already better (O(h) instead of O(n)).
- The space can be improved to **O(1)** by writing the same idea with a `while` loop instead of recursion (just move `root` left or right until it is `null` or equals `val`).
- The time cannot become better than O(h) for a normal BST; it would need a balanced tree (like AVL or red-black) to guarantee O(log n).
