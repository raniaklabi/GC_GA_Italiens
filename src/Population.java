import java.beans.beancontext.BeanContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.ssl.SSLPeerUnverifiedException;

public class Population {
	static int I = 99999;//infinity
	
	List<Individu> p; 
	int sizep;
	int MaxIT;
	int maxInitDup;
	int maxDup;
	double bestSolution;
	Scanner sc= new Scanner(System.in);
	public Population(int size,int MaxIT, int maxInitDup,int maxDup,int n)
	{
		this.maxDup=maxDup;
		this.maxInitDup=maxInitDup;
		this.MaxIT=MaxIT;
		this.sizep=size;
		this.p=new ArrayList<Individu>();
		this.bestSolution=100;
	}
	public Individu calculeBestSolution(int n)
	{
		Individu au=new Individu(n);
		

    	for(int i=0;i<this.sizep-1;i++)
    	{
    		
    		if(this.p.get(i).fitness<this.bestSolution)
    		{
    			this.bestSolution=this.p.get(i).fitness;
    			au=this.p.get(i);
    		    
    		}
    	}
    	/*for(int j=0;j<au.size;j++) {
    		System.out.print(au.C[j]+ " ");
		}*/
    	//int ag2=sc.nextInt();
    	return au;
 
	}
	public void tournement(Individu ind1,Individu ind2)
	{

		Individu au=new Individu(ind1.size);
		
	 	//System.out.println("size=" +this.sizep);

    	for(int i=0;i<this.sizep-1;i++)
    	{
    		for(int j=i+1;j<this.sizep;j++)
    		{
    		if(this.p.get(i).fitness>this.p.get(j).fitness)
    		{
    		    au=this.p.get(i); 
    		   this.p.set(i, this.p.get(j));
    		    this.p.set(j, au);
    		    
    		}
    		
    		}
    	}
    	
    //	ind1=this.p.get(0);
    //	ind2=this.p.get(1);
    
		//System.out.println("**************");
    	for(int j=0;j<this.p.get(0).size;j++) {
			ind1.C[j]=this.p.get(0).C[j];
		}
    	ind1.fitness=this.p.get(0).fitness;
    	for(int j=0;j<this.p.get(1).size;j++) {
			ind2.C[j]=this.p.get(1).C[j];
		}
    	ind2.fitness=this.p.get(1).fitness;
	
		
	}
	public void InitializePopulation(double Talpha,int n,int m,Graph graph,double [][]SPL,double []pi,int [][]delta,List<Edge> edges, List<Edge> undirectededges,int [][]link) {
		
		int i=0;
		//System.out.println("\nsize p="+sizep);

		while(i<sizep) {
			boolean exist=true;
			int redundancy=0;
			
			while((exist==true)&&(redundancy<maxInitDup)) {
			//while((exist==true)) {

				Individu  ind=new Individu(n);

			List<Edge> subEdges = new ArrayList<>();
			ind=coverFeasibilityOperator(ind, n, m, Talpha, delta,pi);
			ind=connectFeasibiltyOperator(ind,n, m, graph, SPL, pi,edges,subEdges,link);
			ind=redundancyRemovalOperator(ind,edges,n,m,subEdges,delta,Talpha,pi,link);

			//ind=redundancyRemovalOperator(ind,undirectededges,n,m,subEdges,delta,Talpha,pi);
			//int ag2=sc.nextInt();
			/*System.out.println("sors!!!!!!!");
			for( int j=0;j<ind.size;j++) {
				System.out.print(ind.C[j]+ " ");
			}*/
			//int ag2=sc.nextInt();
			exist=existCover(ind,i);
			if(exist==false) {
				p.add(i, ind);
				i++;
				redundancy=0;
			}
			else {
				redundancy++;
				//System.out.print("\nStop with "+redundancy);
				//int ag2=sc.nextInt();
			}
			}
			if(redundancy>=maxInitDup) {
				sizep=i;
				break;
			}
	
		}
		
		
	}
	public Individu connectFeasibiltyOperatorImprovement(Individu ind,int n,int m,Graph graph,double [][]SPL,double []pi,List<Edge> edges,List<Edge> subEdges,int [][]link) {	
	List<Integer> notUsedBasicInitial=new ArrayList<Integer>();
		int K=0;
		for(int i=0;i<ind.size;i++)
		{
			if(ind.C[i]==1) {
				notUsedBasicInitial.add(i);
				K++;
			}
		}
		
		List<List<Edge>> group = new ArrayList<>();
		for (int i = 0; i <K; i++)
            group.add(i, new ArrayList<>());
		List<List<Integer>> groupInd = new ArrayList<>();
		for (int i = 0; i <K; i++)
            groupInd.add(i, new ArrayList<>());
		double best=1000;
		int bestInitialChoosenSensor=notUsedBasicInitial.get(0);
		for(int s=0;s<notUsedBasicInitial.size();s++)
		{
			
			/*System.out.print("\ncover before constructConnect=");
			for(int i=0;i<ind.size;i++)
				System.out.print(ind.C[i]+" ");*/
			//ag2=sc.nextInt();
			List<Integer> notUsedBasic=new ArrayList<Integer>();
			for(int i=0;i<ind.size;i++)
			{
				if(ind.C[i]==1) {
					notUsedBasic.add(i);
				}
			}
			//System.out.println("");
			//List<Integer> connectiveInd = new ArrayList<Integer>();
			List<Integer> l = new ArrayList<Integer>();
			Random random = new Random();
			l.add(notUsedBasic.get(s));
			//System.out.println("\notusedbasic "+notUsedBasic);
			//System.out.println("s= "+s+"  "+notUsedBasic.get(s) );
			notUsedBasic.remove(s);
			/*System.out.println("SPL ");
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
					System.out.print(SPL[i][j]+" ");
				System.out.println(" ");
			}*/
			//ag2=sc.nextInt();
			int s1=0,s2=0;
			while (notUsedBasic.size()>0) {
				double min=1000;
				
				for(int i=0;i<l.size();i++)
				{
					for(int j=0;j<notUsedBasic.size();j++)
					{
						
						 if((SPL[l.get(i)][notUsedBasic.get(j)]<min)&&l.get(i)!=notUsedBasic.get(j)) {
						
							s1=l.get(i);
							s2=notUsedBasic.get(j);
							//min=result.size();
							min=SPL[l.get(i)][notUsedBasic.get(j)];
						 }
					}
				}
				//System.out.println("notusedbasic "+notUsedBasic);
				//System.out.println("connective cover "+connectiveInd);
			   //  System.out.println("s1,s2 : "+s1+" "+s2);
			    // ag2=sc.nextInt();
			     List<Integer> result =graph.path(s1, s2, graph);
			    // System.out.println("Path"+result);
			     for(int i=0;i<result.size()-1;i++) {
			    	 Edge e=new Edge(result.get(i), result.get(i+1), pi);
			    	 if(equals(group.get(s), e)==false) {
			    		 group.get(s).add(new Edge(result.get(i), result.get(i+1), pi));}

			     }
			    
			    
			     l=union2sets(result,l);
			    
				notUsedBasic=Difference2sets(notUsedBasic,result); 
				// System.out.println("notusedbased "+notUsedBasic);
				
			}
			
			Graph subgraph = new Graph(group.get(s), n, pi,edges.size(),link);
			for(int i=0;i<group.get(s).size();i++)
	        {	
	        	subgraph.addEdge(Integer.toString(group.get(s).get(i).src), Integer.toString(group.get(s).get(i).dest), group.get(s).get(i).weight);
	        }
			
		
		if(calculePath(subgraph)<best) {
			best=calculePath(subgraph);
			bestInitialChoosenSensor=s;
		}
		/*System.out.println("cover before connecty operator"+notUsedBasicInitial);
		System.out.println("cover"+l);
		System.out.println("path= "+calculePath(subgraph)+"choosen sensor "+s);*/
		/*if(calculePath(subgraph)!=0) {
			int ag2=sc.nextInt();
		}*/
		/*System.out.println("!!!!!!!!!!!!!!!!!!!!*");
		  for(int i=0;i<group.get(s).size();i++) {
			    System.out.println(group.get(s).get(i).src+"->"+group.get(s).get(i).dest);
			     }*/
		  
		 // System.out.println("\nsors+++++++"+l);
		
		groupInd.add(s,l);
		
		//System.out.println("\nsors+++++++"+groupInd.get(s));
	
		}
		//System.out.println("bestPath= "+best+"choosen sensor"+bestInitialChoosenSensor);
		for(int i=0;i<group.get(bestInitialChoosenSensor).size();i++) {
		  subEdges.add(new Edge(group.get(bestInitialChoosenSensor).get(i).src, group.get(bestInitialChoosenSensor).get(i).dest, pi));
		     }
		
		//subEdges=group.get(bestInitialChoosenSensor);
		/*System.out.println("333333333333333333333333333333333");
		 for(int i=0;i<subEdges.size();i++) {
			    System.out.println(subEdges.get(i).src+"->"+subEdges.get(i).dest);
			     }
		System.out.println("\nsors!!!!!!"+groupInd.get(bestInitialChoosenSensor));*/
		for(int i=0;i<groupInd.get(bestInitialChoosenSensor).size();i++) {
			ind.C[groupInd.get(bestInitialChoosenSensor).get(i)]=1;
		}
		
		//int ag2=sc.nextInt();
		ind.CalculeFitness(pi);
		return ind;
		
	}
	public double calculePath(Graph graph) {
		double a=0;
		int src_vertex = 0;
        int list_size = graph.adj_list.size();
 
      //  System.out.println("The contents of the graph:");
        while (src_vertex < list_size) {
            for (Node edge : graph.adj_list.get(src_vertex)) {
                a=a+edge.weight;
            }
            src_vertex++;
        }
		return a;
		
	}
	public Individu connectFeasibiltyOperator(Individu ind,int n,int m,Graph graph,double [][]SPL,double []pi,List<Edge> edges,List<Edge> subEdges,int [][]link) {
		//graph.printGraph(graph, n);
		//List<Integer> basic= new ArrayList<Integer>();
		/*System.out.print("\ncover before constructConnect=");
		for(int i=0;i<ind.size;i++)
			System.out.print(ind.C[i]+" ");*/
		//int ag2=sc.nextInt();
		List<Integer> notUsedBasic=new ArrayList<Integer>();
		for(int i=0;i<ind.size;i++)
		{
			if(ind.C[i]==1) {
				notUsedBasic.add(i);
			}
		}
		//System.out.println("");
		List<Integer> connectiveInd = new ArrayList<Integer>();
		Random random = new Random();
		int s=random.nextInt(notUsedBasic.size());
		connectiveInd.add(notUsedBasic.get(s));
		System.out.println("\notusedbasic "+notUsedBasic);
		System.out.println("s= "+s+"  "+notUsedBasic.get(s) );
		int ag2=sc.nextInt();
		notUsedBasic.remove(s);
		System.out.println("\notusedbasic "+notUsedBasic);
		System.out.println("s= "+s+"  "+notUsedBasic.get(s) );
		ag2=sc.nextInt();
		/*System.out.println("SPL ");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				System.out.print(SPL[i][j]+" ");
			System.out.println(" ");
		}*/
		//
		int s1=0,s2=0;
		while (notUsedBasic.size()>0) {
			double min=1000;
			
			for(int i=0;i<connectiveInd.size();i++)
			{
				for(int j=0;j<notUsedBasic.size();j++)
				{
					
					 if((SPL[connectiveInd.get(i)][notUsedBasic.get(j)]<min)&&connectiveInd.get(i)!=notUsedBasic.get(j)) {
					
						s1=connectiveInd.get(i);
						s2=notUsedBasic.get(j);
						//min=result.size();
						min=SPL[connectiveInd.get(i)][notUsedBasic.get(j)];
					 }
				}
			}
			//System.out.println("notusedbasic "+notUsedBasic);
			//System.out.println("connective cover "+connectiveInd);
		   //  System.out.println("s1,s2 : "+s1+" "+s2);
		    // ag2=sc.nextInt();
		     List<Integer> result =graph.path(s1, s2, graph);
		    // System.out.println("Path"+result);
		     for(int i=0;i<result.size()-1;i++) {
		    	 Edge e=new Edge(result.get(i), result.get(i+1), pi);
		    	 if(equals(subEdges, e)==false) 
		    	 {
		    	 subEdges.add(new Edge(result.get(i), result.get(i+1), pi));
		    	 }
		    	// System.out.println(subEdges.get(i).src+"->"+subEdges.get(i).dest);
		     }
			 //List<String> result = graph.shortestPath(Integer.toString(s1), Integer.toString(s2));
			//System.out.println("result "+result);
			//ag2=sc.nextInt();
			 
			// System.out.println("result "+listOfInteger);
			connectiveInd=union2sets(result,connectiveInd);
			
			
			notUsedBasic=Difference2sets(notUsedBasic,result); 
			// System.out.println("notusedbased "+notUsedBasic);
			
		}
		for(int i=0;i<connectiveInd.size();i++) {
			ind.C[connectiveInd.get(i)]=1;
		}
		
		 /* for(int i=0;i<subEdges.size();i++) {
		    System.out.println(subEdges.get(i).src+"->"+subEdges.get(i).dest);
		     }*/
		/*System.out.print("\ncover after constructConnect=");
		for(int i=0;i<ind.size;i++)
			System.out.print(ind.C[i]+" ");*/
		
		//ind.CalculeFitness(pi);
		//System.out.print("\nfitness="+ind.fitness);
		//ag2=sc.nextInt();
		//System.out.println("connective ind "+connectiveInd);
		// int ag2=sc.nextInt();
		return ind;
		
	}
	   public boolean equals(List<Edge> subEdges, Edge e){
		   
		   for(int i=0;i<subEdges.size();i++) {
	           if(((subEdges.get(i).src==e.src)&&(subEdges.get(i).dest==e.dest))||((subEdges.get(i).src==e.dest)&&(subEdges.get(i).dest==e.src))||(e.dest==subEdges.get(i).dest)||(e.dest==subEdges.get(0).src))

	           // if(((subEdges.get(i).src==e.src)&&(subEdges.get(i).dest==e.dest)))
	             return true;
		   }
			return false;
	    }
	public Individu redundancyRemovalOperator(Individu ind,List<Edge> edges,int n,int m,List<Edge> subEdges,int [][]delta,double Talpha,double []pi,int[][]link) {
		Individu minimalInd=ind;
		Graph subgraph = new Graph(subEdges, n, pi,edges.size(),link);
		for(int i=0;i<subEdges.size();i++)
        {	
        	subgraph.addEdge(Integer.toString(subEdges.get(i).src), Integer.toString(subEdges.get(i).dest), subEdges.get(i).weight);
        }
		//System.out.println("path="+calculePath(subgraph));
		// Graph.printGraph(subgraph,n);
		// int ag2=sc.nextInt();
		
		/* System.out.print("\ncover before constructRedundancy=");
			for(int i=0;i<ind.size;i++)
				System.out.print(ind.C[i]+" ");*/
			
		  List<pair> subEdge=new ArrayList<pair>();
		 
		  
		  for(int i=0;i<subEdges.size();i++)
	        {	
			  subEdge.add(new pair(subEdges.get(i).src, subEdges.get(i).dest));
			 
	        }
		  
		 /* for (Edge e :  subEdges)
		  {
		
			  subEdge.add(new pair(e.src, e.dest));
		  }*/
		int cnt = subEdge.size(); 
		
		Vector t = new Vector(); 
		/* Number of nodes */
		/*int node=0;
		for(int i=0;i<ind.size;i++)
			if(ind.C[i]==1) node++;*/
			
		int node = cnt + 1; 
		//System.out.println("cnt= "+cnt+"node="+ node);
		for(int i = 0; i < 1005; i++) 
		{ 
			t.add(new Vector()); 
		} 
		//int ag2=sc.nextInt();
		/* Create the tree */
		for (int i = 0; i < cnt; i++) 
		{ 
			((Vector)t.get(subEdge.get(i).first)).add(subEdge.get(i).second); 
			((Vector)t.get(subEdge.get(i).second)).add(subEdge.get(i).first); 
		} 
		
		/* Function call */
		
		GFG gfg=new GFG();
		gfg.leaves=new int[gfg.nbreMaxLeaves];
		gfg.i=0;
		
		//gfg.dfs(t, subEdge.get(0).first, 0);
		//System.out.print("sors ");
		gfg.dfs(t, 0, 0);
		
		//ag2=sc.nextInt();
		/*System.out.print("leaves:  ");
		for(int i=0;i<gfg.i;i++)
			if(gfg.leaves[i]!=0)
			{System.out.print( gfg.leaves[i] + " "); }*/
		//int ag2=sc.nextInt();
	
		
		Random random = new Random();
		List<Integer> coveredTarget= new ArrayList<Integer>();
    	for(int i=1;i<minimalInd.C.length;i++) {
			if(minimalInd.C[i]==1) {
				ArrayList<Integer> Ti= new ArrayList<Integer>();
				for(int j=0;j<m;j++) {
					if(delta[i][j]==1) {
						Ti.add(j);
					}
				}
				coveredTarget=union2sets(coveredTarget, Ti);
				
			}
    	
		}
    	//System.out.print("Talpha  :"+coveredTarget);
    	//ag2=sc.nextInt();
    	
      	while((coveredTarget.size()>=Talpha)&&(gfg.i>0)) {
   		 List<Integer> cover= new ArrayList<Integer>();
	        // add edges to the graph
	        for(int i=1;i<minimalInd.size;i++) {
	        	if(minimalInd.C[i]==1) {
	        		cover.add(i);
	        	}
	        }
			int s = random.nextInt(gfg.i);
			//System.out.println("s="+s+"leave: "+gfg.leaves[s] );
			coveredTarget.clear();
			//System.out.print("Cover  :"+cover);
			//System.out.print("Talpha  :"+coveredTarget.size());
	    	//ag2=sc.nextInt();
			//ag2=sc.nextInt();
			for(int i=0;i<cover.size();i++) 
			{
				if((cover.get(i)!=gfg.leaves[s]))
				{
					ArrayList<Integer> Ti= new ArrayList<Integer>();
					for(int j=0;j<m;j++) 
					{
						if(delta[cover.get(i)][j]==1) 
						{
							Ti.add(j);
						}
					}
					coveredTarget=union2sets(coveredTarget, Ti);
				}
			}
		
				
				
			
		//	System.out.println("talpha"+coveredTarget.size()+"k="+gfg.i);
			//ag2=sc.nextInt();
			if(coveredTarget.size()>=Talpha) {
				
				 minimalInd.C[gfg.leaves[s]]=0;
				
							
			}
			gfg.leaves=removeTheElement(gfg.leaves, s);
			gfg.i=gfg.i-1;
			/*System.out.print("leaves:  ");
			for(int i=0;i<gfg.i;i++)
				if(gfg.leaves[i]!=0)
				{System.out.print( gfg.leaves[i] + " "); }*/
			//ag2=sc.nextInt();
			/*for(int i=0;i<minimalInd.size;i++)
				System.out.print(minimalInd.C[i]+" ");*/
			 
			 
			/* System.out.print("Cover  :"+cover);
			 for(int i=0;i<gfg.i;i++)
					if(gfg.leaves[i]!=0)
					{System.out.print( gfg.leaves[i] + " "); }*/
			//ag2=sc.nextInt();
		}
	
		//minimalInd.CalculeFitness(pi);
		/* System.out.print("\ncover after constructRedundancy=");
			for(int i=0;i<minimalInd.size;i++)
				System.out.print(minimalInd.C[i]+" ");*/
			//ag2=sc.nextInt();
		return minimalInd;
	}
	
public int[] removeTheElement(int[] arr, int index) 
{ 

if (arr == null
|| index < 0
|| index >= arr.length) { 

return arr; 
} 

// Create another array of size one less 
int[] anotherArray = new int[arr.length - 1]; 

// Copy the elements except the index 
// from original array to the other array 
for (int i = 0, k = 0; i < arr.length; i++) { 

// if the index is 
// the removal element index 
if (i == index) { 
continue; 
} 

// if the index is not 
// the removal element index 
anotherArray[k++] = arr[i]; 
} 

// return the resultant array 
return anotherArray; 
} 
public Individu coverFeasibilityOperator(Individu ind,int n,int m,double Talpha,int [][]delta,double []pi) {
		Individu coverInd=ind;
		List<Integer> coveredTarget= new ArrayList<Integer>();
	
			List<Integer> uncoveredTarget= new ArrayList<Integer>();
			for(int i=0;i<m;i++)
				uncoveredTarget.add(i);
	
			while(coveredTarget.size()<Talpha) {
				Random random = new Random();
				int t; 
				t = random.nextInt(uncoveredTarget.size());
				ArrayList<Integer> Sj= new ArrayList<Integer>();
				for(int i=1;i<n;i++) {
				if(delta[i][uncoveredTarget.get(t)]==1) {
					Sj.add(i);
				}
				}
				int s; 
				s = random.nextInt(Sj.size());
				coverInd.C[Sj.get(s)]=1;
				ArrayList<Integer> Ts= new ArrayList<Integer>();
				for(int j=0;j<m;j++) {
					if((delta[Sj.get(s)][j]==1)){
						Ts.add(j);
						}
				}
				coveredTarget=union2sets(coveredTarget, Ts);
				uncoveredTarget=Difference2sets(uncoveredTarget, coveredTarget);
				
			}
		
		//int ag2=sc.nextInt();
		//coverInd.CalculeFitness(pi);
		
		return coverInd;
	}
	public List<Integer> Difference2sets(List<Integer> H1,List<Integer> H2)
	{

		 List<Integer> aDiffB = H1.stream()
			        .filter(i -> !H2.contains(i))
			        .collect(Collectors.toList());
		

	//System.out.println(aDiffB);
	return aDiffB;

	}
	public List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}

	public static <T, U> List<U> 
    convertStringListToIntList(List<T> listOfString, 
                               Function<T, U> function) 
    { 
        return listOfString.stream() 
            .map(function) 
            .collect(Collectors.toList()); 
    } 
	public boolean existCover(Individu ind,int size) {
	
		
		List<Integer> testedInd= new ArrayList<Integer>();
		for(int i=0;i<ind.size;i++)
		{
			if(ind.C[i]==1) {
				testedInd.add(i);
			}
		}
		boolean exist=false;
		
		for(int j=0;j<size;j++) {
			List<Integer> ind1= new ArrayList<Integer>();
			for( int jj=0;jj<p.get(j).size;jj++)
			{
				if(p.get(j).C[jj]==1) {
					ind1.add(jj);
				}
			}
			exist=testedInd.equals(ind1);
			//System.out.print(ind1+ ": "+exist);
			
			if(exist==true)
			{
				break;
			}
			
		}
		return exist;
		
	}

	public Individu crossover(Individu ind1,Individu ind2,double []pi) {
		Individu newChro= new Individu(ind1.size);
		newChro.C[0]=1;
		for(int i=1;i<ind1.size;i++)
		{	if((ind1.C[i]==1)&&(ind2.C[i]==1)) 
			{
				newChro.C[i]=1;
			}
			else {
				newChro.C[i]=0;
			
			}
			
		}
		//newChro.CalculeFitness(pi);
		return newChro;
		
	}
	public double bestFiteness()
	{
		double best=1000;
		for(int i=0;i<sizep;i++)
		{
			if(p.get(i).fitness<best) {
				best=p.get(i).fitness;
			}
		}
		return best;
	}
	public Individu mutation1(Individu parent1,Individu parent2,Individu child,double []pi) {
		List<Integer> identique= new ArrayList<Integer>();
		for(int i=1;i<parent1.size;i++)
		{
			if((parent1.C[i]==parent2.C[i])&&(parent2.C[i]==child.C[i])) {
				identique.add(i);
			}
		}

		if(identique.size()>0) {
			
			Random random = new Random();
			int s=random.nextInt(identique.size());
			//System.out.println("ok with s=: "+s);
			if(child.C[identique.get(s)]==0)
			child.C[identique.get(s)]=1;
			else child.C[identique.get(s)]=0;
			}

		else {
				child.C[0]=1;
				for(int i=1;i<parent1.size;i++) 
					child.C[i]=0;
		}	
		
		//child.CalculeFitness(pi);
		
		return child;
	}


	public void updatePopulation(Individu ind)
	{
		if(!existCover(ind,sizep)) {
			//trier la population en ordre croissant
			Individu au=new Individu(ind.size);
			for(int i=0;i<ind.size-1;i++)
			{
				for(int j=i+1;j>ind.size;j++)
				{
					if(p.get(i).fitness<p.get(j).fitness)
					{
						au=p.get(i); 
						p.set(i, p.get(j));
						p.set(j, au);
    		    
					}
    		
				}
			}
    	
		}
		Random random = new Random();
		int s = random.nextInt(sizep/2);
		p.set(s, ind);		
	}
	public void calculeFitnessPopulation(double []pi)
	{
		for(int i=0;i<sizep;i++) {
			p.get(i).CalculeFitness(pi);
		}
	}
	
	
}
