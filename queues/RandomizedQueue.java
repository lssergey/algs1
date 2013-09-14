import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private int N;

    public RandomizedQueue() {
        elements = (Item[]) new Object[2];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private int current;

        private int[] order; 

        public RandomizedQueueIterator() {
            current = 0;
            order = new int[N];
            for (int i = 0; i < N; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current < N;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return elements[order[current++]];
        }
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        int i = 0;

        for (Item element : this) {
            temp[i++] = element;
        }
        elements = temp;
    }

    private int last() {
        return N - 1;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (N == elements.length) resize(2 * N);
        
        elements[N++] = item;
    }

    public Item dequeue() {
        // delete and return a random item
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(N);
        Item item = elements[random];

        if (random != last()) {
            elements[random] = elements[last()];
        }
        elements[last()] = null;

        N--;

        if (N > 0 && N == elements.length / 4) {
            resize(elements.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(N);
        Item item = elements[random];
        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] argv) {
        
    }
}
