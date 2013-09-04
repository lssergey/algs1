import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public Deque() {}

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        N++;
        Node node = new Node();
        node.item = item;
        node.prev = null;
        node.next = first;
        first = node;
        if (N == 1) {
            last = node;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        N++;
        Node node = new Node();
        node.item = item;
        node.prev = last;
        node.next = null;
        last = node;
        if (N == 1) {
            first = node;
        }
    }

    public Item removeFirst() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        N--;
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        N--;
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String argv[]) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(5);
        StdOut.println(deque.size());
        deque.removeLast();
        StdOut.println(deque.size());
    }
}