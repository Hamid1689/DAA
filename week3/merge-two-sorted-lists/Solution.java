import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ArrayList<Integer> numbers = new ArrayList<>();

        while (list1 != null) {
            numbers.add(list1.val);
            list1 = list1.next;
        }

        while (list2 != null) {
            numbers.add(list2.val);
            list2 = list2.next;
        }

        Collections.sort(numbers);

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int number : numbers) {
            current.next = new ListNode(number);
            current = current.next;
        }

        return dummy.next;
    }
}