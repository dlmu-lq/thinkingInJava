package top.itlq.thinkingInJava.containers_17.collections;

import java.util.Iterator;

/**
 * 自定义单向链表
 */
public class SList<E> implements Iterable<E>{

    private Node<E> first;

    private Node<E> last;

    private static class Node<E>{
         E item;
         Node<E> next;
         Node(E item, Node<E> next){
             this.item = item;
             this.next = next;
        }
    }

    public class SListIterator implements Iterator<E> {

        private Node<E> current;

        SListIterator(){
            current = first;
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
            Node<E> cNext = current.next;
            current.next = cNext!=null?cNext.next:null;
        }

        /**
         * 只能在第一个位置add
         * @param item
         */
        public void add(E item) {
            if(current == null){
                first = current = new Node<>(item, null);
            }else{
                current.next = new Node<>(item,current.next);
                current = current.next;
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
        System.out.println(sList);

        sList.iterator().remove();
        System.out.println(sList);
    }

}
