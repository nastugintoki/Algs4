import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites; // all sites
    private WeightedQuickUnionUF ufP; //for test if percolation
    private WeightedQuickUnionUF ufF; //for test if full
    private int count; // count of open sites
    private int size; //size of sites
    private int n; 
    
    
    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if(n <= 0)
            throw new java.lang.IllegalArgumentException();
        
        sites = new boolean[n][n];
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < n; ++j) {
            sites[i][j] = false;
        }
        
        this.n = n;
        count = 0;
        size = n*n;
        
        ufP = new WeightedQuickUnionUF(size + 2);
        ufF = new WeightedQuickUnionUF(size + 1);
        
        for(int i = 1;i <= n;++i) {
            ufP.union(0, i);
            ufP.union(size + 1, size + 1 - i);
            ufF.union(0, i);
        }
            
        
    }
    
    public void open(int row, int col){
        validArgs(row, col);
        if(!isOpen(row, col)) {
            sites[row - 1][col - 1] = true;
            int index = posToIndex(row, col);
            
            if(row > 1 && isOpen(row - 1, col)) {
                ufF.union(posToIndex(row - 1, col), index);
                ufP.union(posToIndex(row - 1, col), index);
            }
            
            if(row < n && isOpen(row + 1, col)) {
                ufF.union(posToIndex(row + 1, col), index);
                ufP.union(posToIndex(row + 1, col), index);
            }
            
            if(col > 1 && isOpen(row, col - 1)) {
                ufF.union(posToIndex(row, col - 1), index);
                ufP.union(posToIndex(row, col - 1), index);
            }
            
            if(col < n && isOpen(row, col + 1)) {
                ufF.union(posToIndex(row, col + 1), index);
                ufP.union(posToIndex(row, col + 1), index);
            }
            
            count++;
 
        }
    }
    // open site (row, col) if it is not open already
    
    private int posToIndex(int row, int col){ //transfer position in grid to index 
        return (row-1) * n + col;
    }
    
    private void validArgs(int i, int j) {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new java.lang.IndexOutOfBoundsException();
    }
    
    public boolean isOpen(int row, int col){ // is site (row, col) open?
        return sites[row - 1][col - 1];
    }  
    
    
    public boolean isFull(int row, int col){  // is site (row, col) full?
        validArgs(row, col);
        return isOpen(row, col) && ufF.connected(0, posToIndex(row, col));
    }
    
    public int numberOfOpenSites(){  // number of open sites
        return count;
    }
    
    public boolean percolates(){ // does the system percolate?
        if(n == 1)
            return isOpen(1, 1);
        return ufP.connected(0, size + 1);
    }              

    public static void main(String[] args){}   // test client (optional)
}