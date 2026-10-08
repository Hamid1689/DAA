# 141. Linked List Cycle

## Problem
I am given the first node (head) of a linked list. I need to find out
whether the list has a cycle, meaning that if I keep following the `next`
pointers, I can come back to a node I have already visited.
Return `true` if there is a cycle, otherwise `false`.

## Approach
I walk through the list and keep a "notebook" (a HashSet) of nodes I have
already visited.
For each node:
1. Check: is this node already in the notebook? If yes, there is a cycle -> return true.
2. If not, write it into the notebook and go to the next node.

If I reach `null`, the list ended, so there is no cycle -> return false.

I store the node itself, not its value, because different nodes can have
the same value (for example [3, 2, 3]).

### Tracing
Example 1: no cycle, list [1, 2]  (1 -> 2 -> null)

| Step | current | Notebook before | Action |
|------|---------|-----------------|--------|
| 1 | 1 | {} | not in notebook, add it |
| 2 | 2 | {1} | not in notebook, add it |
| 3 | null | {1, 2} | loop ends, return false |

Example 2: with cycle, list [1, 2], node 2 points back to node 1

| Step | current | Notebook before | Action |
|------|---------|-----------------|--------|
| 1 | 1 | {} | not in notebook, add it |
| 2 | 2 | {1} | not in notebook, add it |
| 3 | 1 | {1, 2} | already in notebook, return true |

## Time Complexity
**O(n)**, where n is the number of nodes.
In the worst case (no cycle) the loop visits every node once.
Inside the loop, `contains` and `add` of a HashSet take O(1) on average.
So n nodes * O(1) = O(n).

## Space Complexity
**O(n)**.
In the worst case (no cycle) all n nodes are stored in the HashSet.

## Reflection / Improvement
Yes, there is a better approach: the "tortoise and hare" algorithm (Floyd).
I use two pointers: `slow` moves 1 step, `fast` moves 2 steps.
- If there is no cycle, `fast` reaches `null`.
- If there is a cycle, `fast` catches up with `slow` inside the cycle,
  like a faster runner on a circular track.

I would remove the HashSet and use only two variables.
Time stays O(n), but space becomes **O(1)**.