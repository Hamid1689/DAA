# 148. Sort List

## 1. Problem

I get the head of a singly linked list and I have to return the list sorted in ascending order. In a linked list I can't jump to an element by index, I can only move from one node to the next. So I need a sorting method that works well with `next` pointers. The list can have up to 50,000 nodes.

## 2. Approach

I used **merge sort for a linked list**. The steps:

1. **Base case:** if the list is empty or has 1 node, it is already sorted, return it.
2. **Find the middle** with two pointers: `slow` moves 1 step, `fast` moves 2 steps. When `fast` reaches the end, `slow` is in the middle.
3. **Cut the list in two parts:** `second = slow.next`, then `slow.next = null`.
4. **Sort both parts** with the same function (recursion).
5. **Merge** the two sorted lists. I make a `dummy` node and a `tail` pointer. I compare the first nodes of the two lists, attach the smaller one to `tail`, and move forward. When one list ends, I attach the rest of the other list.

Merging a linked list does not need a temporary array. I only change `next` links, so nodes are not copied.

### Tracing for `head = [4, 2, 1, 3]`

```
[4 -> 2 -> 1 -> 3]
```

Finding the middle: `slow = 4`, `fast = 2`. Fast has 2 steps possible, so `slow = 2`, `fast = 3`. Now `fast.next == null`, stop. The middle is `2`.

Cut after `2`:

```
[4 -> 2]     [1 -> 3]
```

Sort the left part `[4 -> 2]`: middle is `4`, cut into `[4]` and `[2]`, merge: `2 < 4`, so the result is `[2 -> 4]`.

Sort the right part `[1 -> 3]`: cut into `[1]` and `[3]`, merge: `[1 -> 3]`.

Merge `[2 -> 4]` and `[1 -> 3]`:

| step | a | b | action | result list |
|------|---|---|--------|-------------|
| 1 | 2 | 1 | 1 < 2, take 1 | 1 |
| 2 | 2 | 3 | 2 < 3, take 2 | 1 -> 2 |
| 3 | 4 | 3 | 3 < 4, take 3 | 1 -> 2 -> 3 |
| 4 | 4 | null | b is finished, attach the rest of a | 1 -> 2 -> 3 -> 4 |

Final answer: `[1 -> 2 -> 3 -> 4]`.

### Challenges I faced

- At first I thought about copying the values into an array, sorting the array, and writing the values back. It works, but it uses O(n) extra memory, and the task is about sorting the list itself (the follow-up asks for O(1) memory), so I chose to work with the nodes directly.
- I also thought about bubble sort / insertion sort on the list. They are O(n²), which is too slow for 50,000 nodes.
- I forgot `slow.next = null` at first. Without it, the left part still points to the right part, so the "left list" never ends, and recursion goes into an infinite loop (StackOverflowError).
- For finding the middle, I tried `fast = head`. For a list with 2 nodes the middle came out wrong, and the list was not cut properly (recursion never got smaller). With `fast = head.next` the left half is never empty and the recursion always gets smaller.
- The `dummy` node was not obvious to me at first. Without it, I had to write a special case for the first node of the merged list. With `dummy`, the code is simpler, and the answer is `dummy.next`.

## 3. Time Complexity

**Time Complexity: O(n log n)**

Why:
- Every time I cut the list in half, so there are about log n levels of recursion.
- On each level, finding the middle and merging go through all n nodes in total, so the work per level is O(n).
- Total: log n levels × n work = O(n log n).

**Space Complexity: O(log n)**

Why:
- I do not create new nodes or arrays (only a `dummy` node and a few pointers, which is O(1)).
- But the recursion uses the call stack. The depth of the recursion is log n, so the stack takes O(log n) memory.

## 4. Reflection / Improvement

- **Is there a more efficient approach?** The time O(n log n) is already good. But the follow-up of the problem asks for **O(1)** memory, and my solution uses O(log n) because of recursion.
- **What to change?** Use **bottom-up merge sort** (no recursion). First merge lists of size 1 into sorted pieces of size 2, then pieces of size 2 into size 4, then 4 into 8, and so on, until the piece size is bigger than n. For this I need a helper that cuts the first `size` nodes from the list, and I reuse the same `merge`.
- **What complexity could it achieve?** Time stays **O(n log n)**, and space becomes **O(1)**, because there is no recursion stack.

## Result

![LeetCode Accepted](accepted.png)
