# 21. Merge Two Sorted Lists

## Problem
I am given the heads of two linked lists, and both are already sorted.
I need to combine them into one sorted linked list and return its head.

## Approach
1. Walk through the first list and put all values into an ArrayList.
2. Walk through the second list and put all values into the same ArrayList.
3. Sort the ArrayList.
4. Build a new linked list from the sorted values, node by node.
   A helper "dummy" node is used so I do not need a special case for the first node.
   At the end I return `dummy.next`.

### Tracing
Example 1: list1 = [1, 3], list2 = [2, 4]

| Step | Action | numbers |
|------|--------|---------|
| 1 | read list1 | [1, 3] |
| 2 | read list2 | [1, 3, 2, 4] |
| 3 | sort | [1, 2, 3, 4] |
| 4 | build new list | dummy -> 1 -> 2 -> 3 -> 4 |
| 5 | return dummy.next | 1 -> 2 -> 3 -> 4 |

Example 2: list1 = [], list2 = []

| Step | Action | numbers |
|------|--------|---------|
| 1 | read list1 (empty) | [] |
| 2 | read list2 (empty) | [] |
| 3 | sort | [] |
| 4 | build list: loop does not run | dummy |
| 5 | return dummy.next | null (empty list) |

## Time Complexity
**O(N log N)**, where N = n + m is the total number of nodes in both lists.
- Reading both lists: O(N), each node is visited once.
- Sorting the ArrayList: O(N log N).
- Building the new list: O(N).

The sorting is the slowest part, so the total is O(N log N).

## Space Complexity
**O(N)**.
The ArrayList stores all N values, and the new list has N new nodes.

## Reflection / Improvement
Yes, there is a faster approach. My solution does not use the fact that
both lists are already sorted.

Better idea: use two pointers, one for each list. Compare the current nodes,
attach the smaller one to the result, and move the pointer of that list forward.
When one list ends, attach the rest of the other list.

Nodes are only re-linked, not copied, and no sorting is needed.
- Time: **O(n + m)**
- Space: **O(1)**