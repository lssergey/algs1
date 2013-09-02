import java.util.Arrays;

public class Percolation {
    
    private int N;
    private boolean[] opened;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF connections_double;
    
    public Percolation(int N) {        
        this.N = N;
        this.opened = new boolean[N * N + 2];
        this.connections = new WeightedQuickUnionUF(N * N + 2);
        this.connections_double = new WeightedQuickUnionUF(N * N + 1);
    }
    
    private int xyTo1D(int i, int j) { 
        return (i - 1) * N + (j - 1) + 1;
    }
    
    private boolean validIndex(int x) {
        return 0 < x && x <= N;
    }
    
    private boolean validIndeces(int i, int j) {
        return validIndex(i) && validIndex(j);
    }
    
    private void validateIndeces(int i, int j) {
        if (!validIndex(i)) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        
        if (!validIndex(j)) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }
    }
    
    private int topNode() {
        return 0;
    }
    
    private int bottomNode() {
        return N * N + 1;
    }
    
    public void open(int i, int j) {
        validateIndeces(i, j);
        
        opened[xyTo1D(i, j)] = true;
        if (i == 1) {
            connections.union(xyTo1D(i, j), topNode());
            connections_double.union(xyTo1D(i, j), topNode());
        }
        if (i == N) {
            connections.union(xyTo1D(i, j), bottomNode());            
        }
        if (validIndeces(i, j - 1) && isOpen(i, j - 1)) {
            connections.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            connections_double.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        }
        if (validIndeces(i, j + 1) && isOpen(i, j + 1)) {
            connections.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            connections_double.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        }
        if (validIndeces(i - 1, j) && isOpen(i - 1, j)) {
            connections.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            connections_double.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        }
        if (validIndeces(i + 1, j) && isOpen(i + 1, j)) {
            connections.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            connections_double.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        }
    }
    
    public boolean isOpen(int i, int j) {
        validateIndeces(i, j);
        return opened[xyTo1D(i, j)];
    }
    
    public boolean isFull(int i, int j) {
        validateIndeces(i, j);
        return connections_double.connected(topNode(), xyTo1D(i, j));
    }
    
    public boolean percolates() {
        return connections.connected(topNode(), bottomNode());
    }
    
    public static void main(String[] args) {
        
    }
}
    