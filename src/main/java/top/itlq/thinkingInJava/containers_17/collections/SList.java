package top.itlq.thinkingInJava.containers_17.collections;

import java.util.Iterator;

/**
 * 自定义单向链表
 */
public class SList<E> implements Iterable<E>{

    private Node<E> first;

    private Node<E> last;

    public SList(){
        first = null;
        last = null;
    }

    private static class Node<E>{
         E item;
         Node<E> next;
         Node(E item, Node<E> next){
             this.item = item;
             this.next = next;
        }
    }

    public class SListIterator implements Iterator<E> {

        // 上一个next访问过的元素
        private Node<E> current;

        SListIterator(){
            current = new Node<>(null, first);
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public E next() {
            current = current.next;
            return current.item;
        }

        /**
         * remove下个位置
         */
        @Override
        public void remove() {
            if(current == first){
                first = first.next;
                current = first;
                return;
            }
            Node<E> oper = first;
            while (oper != null){
                if(oper.next == current){
                    oper.next = current.next;
                    // 移除后，光标移向下一个；
                    current = oper.next;
                    break;
                }
                oper = oper.next;
            }
        }

        /**
         * 只能在最后add
         * @param item
         */
        public void add(E item) {
            Node<E> n = new Node<>(item,null);
            if(first == null){
                first = last = n;
            }else{
                last = last.next = n;
            }
        }
    }

    @Override
    public SListIterator iterator(){
        return new SListIterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;
        while (current != null){
            sb.append(current.item).append(",");
            current = current.next;
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String...args){
        SList<String> sList = new SList<>();
        SList.SListIterator sListIterator = sList.iterator();
        sListIterator.add("1");
        sListIterator.add("2");
        sListIterator.add("3");
        for(String s:sList){
            System.out.println(s);
        }
        System.out.println(sList);

        SList.SListIterator sListIterator2 = sList.iterator();
        sListIterator2.next();
        sListIterator2.next();
        sListIterator2.remove();
        System.out.println(sList);
    }

}
