import java.util.Arrays;

class Solution {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;          // 1 element is already sorted
        int mid = (left + right) / 2;
        mergeSort(nums, left, mid);         // sort left half
        mergeSort(nums, mid + 1, right);    // sort right half
        merge(nums, left, mid, right);      // merge two sorted halves
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;       // pointer in the left part
        int j = mid + 1;    // pointer in the right part
        int k = 0;          // pointer in temp

        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid) temp[k++] = nums[i++];     // rest of the left part
        while (j <= right) temp[k++] = nums[j++];   // rest of the right part

        for (int t = 0; t < temp.length; t++) {
            nums[left + t] = temp[t];               // copy back to nums
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {5, 2, 3, 1};
        System.out.println(Arrays.toString(s.sortArray(nums)));
        // expected: [1, 2, 3, 5]
    }
}
