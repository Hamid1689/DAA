# 912. Sort an Array

## 1. Problem

I get an array of integers and I have to return it sorted from the smallest number to the biggest. I can't use built-in sort functions. The array can have up to 50,000 numbers, and numbers can repeat, so the solution must be fast (O(n log n)).

## 2. Approach

I used **merge sort**. The idea has three steps:

1. Split the array into two halves.
2. Sort each half (using the same method, recursively).
3. Merge the two sorted halves into one sorted array.

An array with 1 element is already sorted, so this is where the recursion stops.

**How merge works.** I have two sorted parts. I put one pointer `i` at the start of the left part and one pointer `j` at the start of the right part. I compare `nums[i]` and `nums[j]`, take the smaller one and put it into a temporary array `temp`, then move that pointer forward. When one part is finished, I copy the rest of the other part. At the end I copy `temp` back into `nums`.

I use `<=` when comparing, so equal numbers keep their original order.

### Tracing for `nums = [5, 2, 3, 1]`

```
[5, 2, 3, 1]
   split
[5, 2]      [3, 1]
   split       split
[5] [2]     [3] [1]
   merge       merge
[2, 5]      [1, 3]
        merge
   [1, 2, 3, 5]
```

Last merge in detail (`left = [2, 5]`, `right = [1, 3]`):

| step | i points to | j points to | compare | temp after step |
|------|-------------|-------------|---------|-----------------|
| 1 | 2 | 1 | 1 < 2, take 1 | [1] |
| 2 | 2 | 3 | 2 < 3, take 2 | [1, 2] |
| 3 | 5 | 3 | 3 < 5, take 3 | [1, 2, 3] |
| 4 | 5 | (end) | right part is finished, copy the rest: 5 | [1, 2, 3, 5] |

### Tracing with duplicates: `[5, 1, 1, 2, 0, 0]`

```
[5,1,1,2,0,0] -> [5,1,1] and [2,0,0]
[5,1,1] -> [5] and [1,1] -> [1,1] -> merge -> [1,1,5]
[2,0,0] -> [2] and [0,0] -> [0,0] -> merge -> [0,0,2]
merge [1,1,5] and [0,0,2] -> [0,0,1,1,2,5]
```

### Challenges I faced

- My first idea was bubble sort. It is easy to write, but it is O(n²). With n = 50,000 that is about 2.5 billion operations, so it is too slow for this problem (Time Limit Exceeded).
- In `merge` it was hard for me to keep the borders right (`left`, `mid`, `right`). The left part is `left..mid` and the right part is `mid+1..right`. When I mixed them up, I got wrong results or an index error.
- I forgot at first that after one part finishes, the other part still has numbers left. I had to add two extra `while` loops to copy the rest.
- At the end I need to copy `temp` back to `nums` at position `left + t`, not at `t`, otherwise only the first part of the array changes.

## 3. Time Complexity

**Time Complexity: O(n log n)**

Why:
- Every time I split the array in half. An array of size n can be halved about log₂(n) times until each piece has 1 element. So the recursion has about log n levels.
- On each level, `merge` goes through all elements once (every element is taken from a part and put into `temp` one time). So the work on one level is O(n).
- Total: log n levels × n work = O(n log n).

This is the same for the best, average and worst case, because I always split in half and always merge.

**Space Complexity: O(n)**

Why:
- `merge` creates a temporary array `temp`. In the biggest merge it has n elements.
- The recursion also uses O(log n) for the call stack, but O(n) is bigger, so the total is O(n).

## 4. Reflection / Improvement

- **Is there a more efficient approach?** In time, O(n log n) is already the best possible for sorting by comparing elements. But in memory, I can do better than O(n).
- **What to change?** I can use **heap sort**. It sorts the array in place (no `temp` array), so extra space is **O(1)**, and time is still O(n log n). The downside is that heap sort is not stable (equal numbers can change their order) and it is a bit harder to write.
- Another option is **counting sort**, because the numbers are in a small range (from -50,000 to 50,000). It would take O(n + k) time, where k = 100,001 is the size of the range, but it needs O(k) extra memory.
- I could also make merge sort faster in practice by creating the `temp` array only once and reusing it, instead of creating a new array in every `merge` call.

## Result

![LeetCode Accepted](accepted.png)
