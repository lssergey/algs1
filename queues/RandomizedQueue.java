import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] elements;
    private int N;
    private int first;
    private int last;

    private class RandomizedQueueIterator implements Iterator<Item> {
		
		private int i;

		private int[] order; 

		public RandomizedQueueIterator() {}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return 
		}
		
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();

			return elements[i];
		}
    }

    private void resize(int capacity) {

    }

	public RandomizedQueue() {
		// construct an empty randomized queue
		elements = (Item[]) new object[2];
	}

	public boolean isEmpty() {
		// is the queue empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the queue
		return N;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		// add the item
	}

	public Item dequeue() {
		// delete and return a random item
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	public Item sample() {
		// return (but do not delete) a random item
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomizedQueueIterator();
	}
}