
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.print.attribute.standard.Sides;
import javax.swing.text.GapContent;

// Graph class
class Graph {
	static double I = Double.POSITIVE_INFINITY;
    // node of adjacency list 

 
// define adjacency list
Map<String, Node> nodes = new HashMap<String, Node>();
List<List<Node>> adj_list = new ArrayList<>();
double [][]cost;
double SPL[][];
int[][] predMatrix;
static void printSolution(int dist[][],int n) {
	System.out.println("Following matrix: ");
	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < n; ++j) {
			if (dist[i][j] == I)
				System.out.print("I   \t");
			else
				System.out.print(dist[i][j] + "   \t");
		}
		System.out.println();
	}
}
public double[][]  evaluateShortPath1(double []pi,int n){
	Scanner sc= new Scanner(System.in);
	SPL=new double[n][n];
	SPL = cost;

	predMatrix = new int[n][n];
	
	/*
	 * Predecessor Matrix: is defined as the predecessor of vertex i on a shortest
	 * path from vertex j with all intermediate vertices in the set 1,2,...,k
	 */
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
			if (i != j)
				predMatrix[i][j] = j ;
	}
	
	for (int k = 0; k < n; k++) {
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				// If vertex k is on the shortest path from
				// i to j, then update the value of dist[i][j]
				  if ((SPL[i][k] + SPL[k][j] < SPL[i][j])&& (SPL[i][k] != I)&&( SPL[k][j] != I)) {
		                SPL[i][j] = SPL[i][k] + SPL[k][j]; 
					predMatrix[i][j] = predMatrix[i][k];}
					
			
			if( SPL[i][k] != I && // Is there any path from i to k?
					SPL[k][j] != I && // Is there any path from k to j?
					SPL[i][i] < 0)      // Is k part of a negative loop?
					SPL[k][j] = -I; 
			}
		}
		}
	
	
	 return SPL; 
}

public double[][]  evaluateShortPath(double []pi,int n){
	Scanner sc= new Scanner(System.in);
	SPL=new double[n][n];
	SPL = cost;

	predMatrix = new int[n][n];
	
	/*
	 * Predecessor Matrix: is defined as the predecessor of vertex i on a shortest
	 * path from vertex j with all intermediate vertices in the set 1,2,...,k
	 */
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
			if (i != j)
				predMatrix[i][j] = j ;
	}
	
	for (int k = 0; k < n; k++) {
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				// If vertex k is on the shortest path from
				// i to j, then update the value of dist[i][j]
				  if (SPL[i][k] + SPL[k][j] < SPL[i][j]) {
		                SPL[i][j] = SPL[i][k] + SPL[k][j]; 
					predMatrix[i][j] = predMatrix[i][k];}
					}
			}
		}
	
	
	 return SPL; 
}

/*public double[][]  evaluateShortPath(double []pi,int n){
	SPL=new double[n][n];
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<n;j++)
		{
			if(i==j) {
				SPL[i][j]=0;
			}
			else if(cost[i][j]!=I) 
			{
				SPL[i][j]=pi[i]+pi[j];
				
			}
			else {
				SPL[i][j]=I;
			}
		}
		
	}
	/*System.out.println("***************************** ");
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<n;j++)
			System.out.print(SPL[i][j]+" ");
		System.out.println(" ");
	}
	System.out.println(" *******************");*/
/*	for(int k=0;k<n;k++)
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{	  if (SPL[i][k] + SPL[k][j] < SPL[i][j]) 
                SPL[i][j] = SPL[i][k] + SPL[k][j]; 
			}
			
		}
	}
	 return SPL; 
}*/

    //Graph Constructor
    public Graph(List<Edge> edges,int n,double []pi,int [][] link)
    {	cost=new double[n][n];
   
    	for(int ii=0;ii<n;ii++)
    		{for(int j=0;j<n;j++)
    			{if(ii==j) {
    				cost[ii][j]=0;
    			}
    			else if (link[ii][j]==1) {
    				cost[ii][j]=pi[j];
    			}
    		
    			else {
    				cost[ii][j]=I;
    			}
    			}
    		}
    	
    			
        // adjacency list memory allocation
    	//for (int i = 0; i < edges.size(); i++)
        for (int i = 0; i < edges.size(); i++)
            adj_list.add(i, new ArrayList<>());
 
        // add edges to the graph
        for (Edge e : edges)
        {	//e.weight=pi[e.dest]+pi[e.src];
        	e.weight=pi[e.dest];
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest,Integer.toString(e.dest), e.weight));
            //adj_list.get(e.dest).add(new Node(e.src,Integer.toString(e.src), e.weight));
            cost[e.src][e.dest]=e.weight;
           // cost[e.dest][e.src]=e.weight;
        }
      
      
    }
    public Graph(List<Edge> edges,int n,double []pi,int [][] link,int [][]delta,int m,double []fi)
    {	cost=new double[n][n];
   
    	for(int ii=0;ii<n;ii++)
    		{for(int j=0;j<n;j++)
    			{if(ii==j) {
    				cost[ii][j]=0;
    			}
    			else if (link[ii][j]==1) {
    				cost[ii][j]=pi[j];
    				List<Integer> coveredTarget= new ArrayList<Integer>();
    		    	
    				for(int jj=0;jj<m;jj++) {
    					if(delta[ii][j]==1) {
    						coveredTarget.add(jj);
    					}
    				
    				
    			}
    				for(int i=0;i<coveredTarget.size();i++) {
    					cost[ii][j]=cost[ii][j]-fi[coveredTarget.get(i)];	
    	        	}	
    			}
    		
    			else {
    				cost[ii][j]=I;
    			}
    			}
    		}
    	
    			
        // adjacency list memory allocation
    	//for (int i = 0; i < edges.size(); i++)
        for (int i = 0; i < edges.size(); i++)
            adj_list.add(i, new ArrayList<>());
 
        // add edges to the graph
        for (Edge e : edges)
        {	//e.weight=pi[e.dest]+pi[e.src];
        	List<Integer> coveredTarget= new ArrayList<Integer>();
    	
    				for(int j=0;j<m;j++) {
    					if(delta[e.dest][j]==1) {
    						coveredTarget.add(j);
    					}
    				
    				
    			}
        	e.weight=pi[e.dest];
        	for(int i=0;i<coveredTarget.size();i++) {
        		e.weight=e.weight-fi[coveredTarget.get(i)];	
        	}
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest,Integer.toString(e.dest), e.weight));
            //adj_list.get(e.dest).add(new Node(e.src,Integer.toString(e.src), e.weight));
            cost[e.src][e.dest]=e.weight;
           // cost[e.dest][e.src]=e.weight;
        }
      
      
    }
    public Graph(List<Edge> edges,int n,double []pi,int size,int [][] link)
    {	cost=new double[n][n];
    	for(int ii=0;ii<n;ii++)
    		for(int j=0;j<n;j++)
    		{
    			if(ii==j) {
    				cost[ii][j]=0;
    			}
    			else if (link[ii][j]==1) {
    				cost[ii][j]=pi[j];
    			}
    			else if((link[ii][j]==0)) {
    				cost[ii][j]=I;
    			}
    		}
    			
    			
        // adjacency list memory allocation
    	//for (int i = 0; i < edges.size(); i++)
        for (int i = 0; i <size ; i++)
            adj_list.add(i, new ArrayList<>());
 
        // add edges to the graph
        for (Edge e : edges)
        {	//e.weight=pi[e.dest]+pi[e.src];
        	e.weight=pi[e.dest];
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest,Integer.toString(e.dest), e.weight));
            //adj_list.get(e.dest).add(new Node(e.src,Integer.toString(e.src), e.weight));
            cost[e.src][e.dest]=e.weight;
           // cost[e.dest][e.src]=e.weight;
        }
      
    }

  
// print adjacency list for the graph
    public static void printGraph(Graph graph,int n)  {
        int src_vertex = 0;
        int list_size = graph.adj_list.size();
 
        System.out.println("The contents of the graph***:");
        while (src_vertex < list_size) {
            //traverse through the adjacency list and print the edges
            for (Node edge : graph.adj_list.get(src_vertex)) {
                System.out.println("Vertex:" + src_vertex + " ==> " + edge.value + 
                                " (" + edge.weight + ")");
            }
 
          //  System.out.println();
            src_vertex++;
        }
     /*  for(int ii=0;ii<n;ii++)
    		{for(int j=0;j<n;j++)
    		{
    			System.out.print(cost[ii][j]+" ");
    		}
    		System.out.println("");
    		}*/
        
    }
		  public void addEdge(String nodeName1, String nodeName2, double weight) {
			    Node node1 = nodes.get(nodeName1);
			    if (node1 == null) {
			      node1 = new Node(nodeName1);
			    }

			    Node node2 = nodes.get(nodeName2);
			    if (node2 == null) {
			      node2 = new Node(nodeName2);
			    }

			    node1.addNeighbor(node2, weight);
			    node2.addNeighbor(node1, weight);

			    nodes.put(nodeName1, node1);
			    nodes.put(nodeName2, node2);
			  }

		  public Graph() {}
 /*   public static void main (String[] args){
        // define edges of the graph 
    	double pi[]= {0,0,0,0,0,0};
       List<Edge> edges = Arrays.asList(new Edge(0, 1, pi),new Edge(0, 2, pi),
                   new Edge(1, 2, pi),new Edge(1, 3, pi), new Edge(2, 1, pi),
                   new Edge(2, 4, pi), new Edge(3, 4, pi),new Edge(3, 5, pi));
    	//List<Edge> edges=Arrays.asList(new Edge(2,0, pi),new Edge(0,3, pi));
    	int n=6;
        int link [][]=new int[n][n];
        
        // call graph class Constructor to construct a graph
        Graph graph = new Graph(edges,n,pi);
       
     
        // print the graph as an adjacency list
        Graph.printGraph(graph,n);
        Individu ind =new Individu(n);
        ind.C[1]=1;
        ind.C[2]=1;
        ind.C[3]=1;
        List<Edge> subEdges= new ArrayList<Edge>();
        Graph subgraph = new Graph(edges,n,ind,subEdges,pi);
        for(int i=0;i<subEdges.size();i++)
        {	
        	subgraph.addEdge(Integer.toString(subEdges.get(i).src), Integer.toString(subEdges.get(i).dest), subEdges.get(i).weight);
        }
        // print the graph as an adjacency list
        Graph.printGraph(subgraph,n);
        System.out.println("size="+ edges.size());
        for(int i=0;i<edges.size();i++)
        {	System.out.println(Integer.toString(edges.get(i).src) + " "+Integer.toString(edges.get(i).dest)+" "+edges.get(i).weight);
        	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
        }
      /*  graph.addEdge("0", "1", 5);
        graph.addEdge("0", "2", 1);
        graph.addEdge("1", "2", 2);
        graph.addEdge("1", "3", 1);
        graph.addEdge("2", "3", 4);
        graph.addEdge("2", "4", 8);
        graph.addEdge("3", "4", 3);
        graph.addEdge("3", "5", 6);*/
       /* List<String> result = graph.shortestPath("0", "3");
    
        System.out.println("shortest path between 0 and 3: " + result);
        System.out.println(result.size());
     
        for(int i=0;i<result.size();i++)
        {
        	 System.out.print(result.get(i)+"->");
        }*/
      /* graph.evaluateShortPath(pi,n );
        int u = 1;
		int v = 5;
		String path = String.format("%d -> %d    %2d     %s", u, v, (int) graph.SPL[u][v], u);
		do {
			u = graph.predMatrix[u][v];
			path += " -> " + u;
		} while (u != v);
		System.out.println(path);
        
    }*/
   /* public int [] path(int i,int j,Graph graph,int k){
	int []pathh= new int[100];
	
	//graph.evaluateShortPath(pi,n );
	pathh[0]=i;
	
	do {
		i = graph.predMatrix[i][j];
		pathh[k]=i;
		k++;
	} while (i != j);

	return pathh;
	
}*/
    public List<Integer> path(int i,int j,Graph graph){
    	Scanner sc= new Scanner(System.in);
    	List< Integer>pathh= new ArrayList<>();
    	
    	//int ag2=sc.nextInt();
    	if (i != j) {
			int u = i ;
			int v = j;
			pathh.add(u);
			do {
				u = graph.predMatrix[u][v];
				pathh.add(u);
				
				
			} while (u != v);
			
		}
    	
   /* if (i != j) {
    	//graph.evaluateShortPath(pi,n );
    	pathh.add(i);
    	do {
    		System.out.print("\nsors*****"+i);
			i = graph.predMatrix[i][j];
			System.out.print("\nsors*****"+i);
			pathh.add(i);
			
		} while (i != j);
    	}*/
    	return pathh;
    	
    }
	public List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
}
