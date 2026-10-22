class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // 1. Find the middle
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Cut the list into two parts
        ListNode second = slow.next;
        slow.next = null;

        // 3. Sort each part
        ListNode left = sortList(head);
        ListNode right = sortList(second);

        // 4. Merge
        return merge(left, right);
    }

    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        if (a != null) tail.next = a;
        else tail.next = b;

        return dummy.next;
    }

    public static void main(String[] args) {
        // build list 4 -> 2 -> 1 -> 3
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        Solution s = new Solution();
        ListNode sorted = s.sortList(head);

        while (sorted != null) {
            System.out.print(sorted.val + " ");
            sorted = sorted.next;
        }
        // expected: 1 2 3 4
    }
}
