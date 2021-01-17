// Java implementation of the approach 
import java.util.*; 

class GFG 
{ 
	
 
 static int nbreMaxLeaves=500;
static int []leaves=new int[nbreMaxLeaves];
static int i=0;

// Function to perform DFS on the tree 
static void dfs(Vector t, int node, int parent) 
{ 
	int flag = 1; 
	//System.out.println("okkkkkkkkkkkkk ");
	// Iterating the children of current node 
	for (int i = 0; i < ((Vector)t.get(node)).size(); i++) 
	{ 
		int ir = (int)((Vector)t.get(node)).get(i); 
		
		// There is at least a child 
		// of the current node 
		if (ir != parent) 
		{ 
			flag = 0; 
			dfs(t, ir, node); 
		} 
	} 
	
	// Current node is connected to only 
	// its parent i.e. it is a leaf node 
	if (flag == 1) 
		{//System.out.print( node + "* "); 
		leaves[i]=node;
		i++;
		}
} 

// Driver code 
public static void main(String args[]) 
{ 
	/*Adjacency list */
	
	Vector t = new Vector(); 

	/*List of all edges */
	pair edges[] = { new pair( 10, 2 ), 
					new pair( 10, 3 ), 
					new pair( 2, 4 ), 
					new pair( 2, 7),
					new pair( 2, 9), 
					new pair( 9, 5 ),new pair( 9, 0),new pair( 0,12),new pair( 0,13)
					 }; 
	/*pair edges[] = { new pair( 1,2 ), 
			new pair( 1,3), 
			new pair( 2, 4 ) 
			};*/

	/*Count of edges */
	int cnt = edges.length ; 

	/* Number of nodes */
	//int node = cnt + 1; 
	int node = cnt + 1; 
	System.out.println("node="+ node);
	for(int i = 0; i < 1005; i++) 
	{ 
		t.add(new Vector()); 
	} 

	/* Create the tree */
	for (int i = 0; i < cnt; i++) 
	{ 
		((Vector)t.get(edges[i].first)).add(edges[i].second); 
		((Vector)t.get(edges[i].second)).add(edges[i].first); 
	} 

	/* Function call */
	//dfs(t, 1, 0); 
	dfs(t, edges[0].first, 5);
	System.out.println(" ");
	for(int i=0;i<nbreMaxLeaves;i++)
		if(leaves[i]!=0)
		{System.out.print( leaves[i] + " "); }
		
} 
} 

// This code is contributed by Arnab Kundu 
