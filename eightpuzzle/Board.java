public class Board {

    private int N;
    private int[] blocks;

    private int getPosition(int i, int j) {
        return i * N + j;
    }

    private int getNumberOfBlocks() {
        return N * N;
    }

    private boolean hasCorrectValue(int i) {
        int value = blocks[i];
        return getCorrectPosition(value) == i;
    }

    private int getCorrectPosition(int value) {
        if (value == 0) return getNumberOfBlocks() - 1;
        else return value - 1;
    }

    private int getRow(int position) {
        return position / N;
    }

    private int getColumn(int position) {
        return position - getRow(position) * N;
    }

    private int getDistance(int i, int j) {
        int value = blocks[getPosition(i, j)];
        int correctPosition = getCorrectPosition(value);
        int correctRow = getRow(correctPosition);
        int correctColumn = getColumn(correctPosition);

        return Math.abs(i - correctRow) + Math.abs(j - correctColumn);
    }

    private int getEmptyPosition() {
        for (int i = 0; i < getNumberOfBlocks(); i++) {
            if (blocks[i] == 0) return i;
        }
        return -1;
    }

    private int[][] getMatrix() {
        return getMatrix(this.blocks);
    }

    private int[][] getMatrix(int[] blocks) {
        int[][] matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = blocks[getPosition(i, j)];
            }
        }

        return matrix;
    }

    private Board changePosition(int from, int to) {
        int[] blocks = this.blocks;
        int t = blocks[to];
        blocks[to] = blocks[from];
        blocks[from] = t;
        return new Board(getMatrix(blocks));
    }

    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks[0].length;
        this.blocks = new int[getNumberOfBlocks()];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[getPosition(i, j)] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        // board dimension N
        return N;
    }

    public int hamming() {
        // number of blocks out of place
        int countWrongBlocks = 0;
        for (int i = 0; i < getNumberOfBlocks(); i++) {
            if (!hasCorrectValue(i)) countWrongBlocks++;
        }
        return countWrongBlocks;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int sumOfDistances = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int distance = getDistance(i, j);
                sumOfDistances += distance;
            }
        }
        return sumOfDistances;
    }

    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < getNumberOfBlocks(); i++) {
            if (!hasCorrectValue(i)) return false;
        }
        return true;
    }

    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        int[][] blocks = getMatrix();

        boolean twin = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                int a = blocks[i][j];
                int b = blocks[i][j + 1];

                if (a > 0 && b > 0) {
                    blocks[i][j] = b;
                    blocks[i][j + 1] = a;
                    twin = true;
                    break;
                }
            }
            if (twin) break;
        }

        return new Board(blocks);

    }

    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;

        for (int i = 0; i < getNumberOfBlocks(); i++) {
            if (that.blocks[i] != this.blocks[i]) return false;
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> boards = new Queue<Board>();

        int emptyPosition = getEmptyPosition();
        int row = getRow(emptyPosition);
        int column = getColumn(emptyPosition);

        // Up
        if (row > 0) boards.enqueue(
            changePosition(emptyPosition, emptyPosition - N)
        );

        // Down
        if (row < N - 1) boards.enqueue(
            changePosition(emptyPosition, emptyPosition + N)
        );

        // Left
        if (column > 0) boards.enqueue(
                changePosition(emptyPosition, emptyPosition - 1)
        );

        // Right
        if (column < N - 1) boards.enqueue(
            changePosition(emptyPosition, emptyPosition + 1)
        );

        return boards;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(
                    String.format("%2d ", blocks[getPosition(i, j)])
                );
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] blocks1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board1 = new Board(blocks1);
        assert board1.isGoal();
        assert board1.hamming() == 0;
        assert board1.manhattan() == 0;

        int[][] blocks2 = {
            {1, 2, 3},
            {4, 0, 8},
            {7, 6, 5}
        };
        Board board2 = new Board(blocks2);
        assert !board2.isGoal();
        assert board2.hamming() == 4;
        assert board2.manhattan() == 8;

        assert !board1.equals(board2);

        StdOut.println(board1);
        Queue<Board> queue = (Queue<Board>) board1.neighbors();
        StdOut.println(board1);
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
        StdOut.println(board1);
    }
}
