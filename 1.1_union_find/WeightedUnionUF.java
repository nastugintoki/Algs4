public class WeightedUnionUF {
    private int[] size; //size of components include node i
    private int[] parents; // parents of node i
    private int count;  //number of connected components

    public WeightedUnionUF(int N) {
        count = N;
        size = new int[N];
        parents = new int[N];
        for(int i = 0; i < N; ++i) {
            parents[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }
    
    public void union(int p, int q) {
        int rootp = root(p);
        int rootq = root(q);
        if (size[rootp] > size[rootq]) {
            parents[q] = rootp;
            size[rootp] += size[rootq]; 
        } 
        else {
            parents[p] = rootq;
            size[rootq] += size[rootp]; 
        }
        count--;
    }

    public int find(int p) {
        return 0;
    }

    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    private int root(int p) {
        int i = p;
        while(i != parents[i]) {
            parents[i] = parents[parents[i]]; //path compression
            i = parents[i];
        }
        return i;
    } 


} 
