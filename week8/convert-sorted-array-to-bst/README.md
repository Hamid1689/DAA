# Convert Sorted Array to Binary Search Tree (LeetCode 108)

## 1. Problem
I am given an array of numbers sorted in increasing order. I need to build a binary search tree from it. The tree must be **height-balanced**: for every node, the heights of the left and right subtrees differ by at most 1.

## 2. Approach

**First idea (did not work well).** Take the numbers one by one and insert each of them into a BST. The problem is that the array is sorted: every new number is bigger than all the previous ones, so it always goes to the right. For `[1,2,3,4,5]` I get 1 -> 2 -> 3 -> 4 -> 5, a long chain (like a linked list) with height `n`. That is a valid BST but it is **not balanced**, and building it takes O(n^2), because every insert walks down the whole chain.

**Final idea (middle element as the root).** To keep the tree balanced, the root should have about the same number of elements on both sides. In a sorted array the middle element does exactly that:
1. Take the middle element of the current part of the array, it becomes the root
2. Everything to the left of it (smaller numbers) goes to the left subtree. I build it the same way
3. Everything to the right of it (bigger numbers) goes to the right subtree. I build it the same way
4. If the part is empty (`left > right`), return `null`

I use two indices `left` and `right` instead of cutting the array into new arrays, so nothing is copied.

**Tracing:** `nums = [-10, -3, 0, 5, 9]`
1. Part `[0..4]`, mid = 2 -> root is **0**
2. Left part `[0..1]` (`-10, -3`), mid = 0 -> node **-10**
   - its left part is empty -> `null`
   - its right part `[1..1]`, mid = 1 -> node **-3**
3. Right part `[3..4]` (`5, 9`), mid = 3 -> node **5**
   - its left part is empty -> `null`
   - its right part `[4..4]` -> node **9**

Result tree:
```
        0
       / \
    -10   5
      \    \
      -3    9
```
Heights: left side 3, right side 3. It is balanced. (`[0,-3,9,-10,null,5]` from LeetCode is another correct answer, because with an even number of elements there are two possible middles.)

**Challenges.** I first tried the "insert one by one" approach and saw the chain problem. Then I thought about what makes a tree balanced: the same number of nodes on both sides, so I started to choose the middle. Another challenge was whether to copy parts of the array (`Arrays.copyOfRange`) on every call. I understood that copying costs time, so I pass indices.

## 3. Time Complexity
**Time: O(n).** Each element of the array becomes exactly one node, and each node is created exactly once. Every recursive call does only O(1) work (find the middle, create a node), and there are about 2n calls in total (n real nodes plus the `null` ends).

**Space: O(log n)** extra for the recursion stack, because the tree is balanced, so the recursion depth is about log n. (The output tree itself has n nodes, but that is the answer, not extra space.)

If I copied sub-arrays on every call, it would be O(n log n) time, because at each of the log n levels I would copy about n elements in total.

## 4. Reflection / Improvement
- The index version is already optimal in time: any solution must create n nodes, so O(n) cannot be beaten.
- The version with array slices/copies is simpler to write but slower (O(n log n)). The improvement is to use indices, as I did.
- The "insert one by one" idea can be improved only by using a self-balancing tree (AVL / red-black), but this is more work than needed here because the array is already sorted.
