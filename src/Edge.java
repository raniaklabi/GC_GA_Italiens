class Edge {
    int src, dest; 
    double weight;
    Edge(int src, int dest,double[]pi) {
            this.src = src;
            this.dest = dest;
          //this.weight =pi[this.src]+pi[this.dest];
          this.weight =pi[this.dest];
        }
   
 
}