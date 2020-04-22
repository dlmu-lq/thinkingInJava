package top.itlq.thinkingInJava.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    public static List<Integer> getRow(int rowIndex) {
        int arraySize = rowIndex + 1;
        if(arraySize == 1){
            return Collections.singletonList(1);
        }
        Integer[] reArray = new Integer[arraySize];
        reArray[0] = 1;
        if(rowIndex > 1){
            reArray[1] = 1;
        }
        reArray[arraySize - 1] = 1;
        int noRepeat = arraySize / 2 + arraySize % 2;
        for(int i=2;i<=rowIndex;i++){
            int num = i > noRepeat ? noRepeat : i;
            if(i % 2 == 1){
                for(int j=1;j<num;j++){
                    reArray[j] = reArray[arraySize - j] + reArray[arraySize - j - 1];
                }
                reArray[num] = 1;
            }else{
                for(int j = arraySize - 2, end = arraySize - num;j>=end;j--){
                    reArray[j] = reArray[arraySize - 1 - j] + reArray[arraySize - 2 - j];
                }
                reArray[arraySize - num - 1] = 1;
            }
        }
        if(arraySize % 2 == 0){
            for(int i = arraySize/2;i<arraySize;i++){
                reArray[i] = reArray[arraySize - i - 1];
            }
        }else{
            for(int i = 0;i<arraySize/2;i++){
                reArray[i] = reArray[arraySize - i - 1];
            }
        }
        return Arrays.asList(reArray);
    }

    public static void main(String[] args) {
        System.out.println(getRow(4));
    }
}

class MyLinkedList {

    static class Node{
        Node(int val){
            this.val = val;
        }
        Node next;
        int val;
    }

    private Node head;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = new Node(0);
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index < 0){
            return -1;
        }
        Node aim = head;
        do{
            aim = aim.next;
        }while(aim != null && index-- > 0);
        return index < 0 ? aim.val : -1;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node old = head.next;
        head.next = new Node(val);
        head.next.next = old;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node oldTail = head;
        while(oldTail.next != null){
            oldTail = oldTail.next;
        }
        oldTail.next = new Node(val);
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        Node prev = head, pp = head;
        do{
            pp = prev;
            prev = pp.next;
        }while(prev != null && index-- > 0);
        if(index < 0){
            pp.next = new Node(val);
            pp.next.next = prev;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        Node prev = head, pp = head;
        do{
            pp = prev;
            prev = pp.next;
        }while(prev != null && index-- > 0);
        if(index < 0){
            pp.next = prev.next;
        }
    }

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(7);
        myLinkedList.addAtHead(2);
        myLinkedList.addAtHead(1);
        myLinkedList.addAtIndex(3, 0);
        myLinkedList.deleteAtIndex(2);
        myLinkedList.addAtHead(6);
        myLinkedList.addAtTail(4);
        System.out.println(myLinkedList.get(4));
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */