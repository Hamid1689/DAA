import java.util.HashSet;

public class Solution {
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> visited = new HashSet<>();  // notebook of visited nodes
        ListNode current = head;                      // start at the first node

        while (current != null) {
            if (visited.contains(current)) {   // have I been here before?
                return true;                   // yes -> there is a cycle
            }
            visited.add(current);              // no -> write it down
            current = current.next;            // move to the next node
        }

        return false;  // reached null -> no cycle
    }
}ч