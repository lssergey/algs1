public class Subset {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        String word;
        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            rq.enqueue(word);
        }

        for (int i = 0; i < N; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}