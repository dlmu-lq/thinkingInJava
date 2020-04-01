package top.itlq.thinkingInJava.test;

public class LinkNodesLink {
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode a = headA, b = headB;
        int diff = 0;
        do{
            a = a.next;
            b = b.next;
        } while(a != null && b != null);
        if(b != null){
            ListNode temp = a;
            a = b;
            b = temp;
            temp = headA;
            headA = headB;
            headB = temp;
        }
        do{
            diff++;
            a = a.next;
        } while(a != null);
        while(diff-- > 0){
            headA = headA.next;
        }
        do{
            if(headA == headB){
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }while(headA != null);
        return null;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(0);
        ListNode a0 = new ListNode(9);
        a.next = a0;
        ListNode a1 = new ListNode(1);
        a0.next = a1;
        ListNode a2 = new ListNode(2);
        a1.next = a2;
        ListNode a3 = new ListNode(4);
        a2.next = a3;
        ListNode b = new ListNode(3);
        b.next = a2;
        System.out.println(getIntersectionNode(a, b));
    }
}