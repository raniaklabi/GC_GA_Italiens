import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class subProbMCSCcorrige {
	double r;
	static int I = 99999;
	 List<Integer> cover;
	 List<Integer> coveredTarget;
	public subProbMCSCcorrige() {
		r=0;
		cover= new ArrayList<Integer>();
		coveredTarget= new ArrayList<Integer>();
	}
	public double coverMCSC(int n,int m,int [][]delta,double Talpha,List<Edge>  edges,double []pi,int [][]link) {
		Scanner sc= new Scanner(System.in);
		 List<Edge> subEdges = new ArrayList<>();
		Graph graph = new Graph(edges,n,pi,link);
	
   	 	for(int i=0;i<edges.size();i++)
        {	
        	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
        }
   	 	
   	 	graph.evaluateShortPath(pi,n );
   		/*System.out.println("\nspl: ");
   	 	for(int ii=0;ii<n;ii++) {
   	 		for(int jj=0;jj<n;jj++) {
   	 			System.out.print(graph.SPL[ii][jj]+" ");
   	 		}
   	 		System.out.println("");
   	 	}*/
   	// int ag2=sc.nextInt();
		cover.add(0);
		List<Integer> S=new ArrayList<Integer>();
		for(int i=1;i<n;i++)
			S.add(i);
		while ((coveredTarget.size()<Talpha)&&(S.size()>0)) {
			/*List<Edge> subEdges=new ArrayList<>();
			 for(int i=0;i<S.size()-1;i++) {
		    	 Edge e=new Edge(S.get(i), S.get(i+1), pi);
		    	 if(equals(subEdges, e)==false) 
		    	 {
		    	 subEdges.add(new Edge(S.get(i),S.get(i+1), pi));
		    	 }
		     }
			 Graph graph = new Graph(subEdges, n, pi,edges.size(),link);
				for(int i=0;i<subEdges.size();i++)
		        {	
		        	graph.addEdge(Integer.toString(subEdges.get(i).src), Integer.toString(subEdges.get(i).dest), subEdges.get(i).weight);
		        }
		   	 	
		   	 graph.evaluateShortPath(pi,n );*/
			int choosenSensor2=S.get(0);
			int choosenSensor1=cover.get(0);
			int s1=0;
			int s2=0;
			double fitness=1000;
			List<Node>  neighbors = new ArrayList<>();
			List<Integer> listNeighbor=new ArrayList<Integer>();
			for(int i=0;i<cover.size();i++)
			
			{
				s1=cover.get(i);
				neighbors=	graph.adj_list.get(s1);
		        for(int iii=0;iii<neighbors.size();iii++) {
						if((S.contains(neighbors.get(iii).value))&&(!listNeighbor.contains(neighbors.get(iii).value)) ){
						//if(S.contains(neighbors.get(iii).value)) {
						listNeighbor.add(neighbors.get(iii).value);}
					}
					List<Integer> coverAdjacent=new ArrayList<Integer>();
					for(int k=0;k<cover.size();k++)
					{	if(cover.get(k)!=0) {
						List<Integer> t1=new ArrayList<Integer>();
						for(int ii=0;ii<m;ii++)
							if(delta[cover.get(k)][ii]==1) {
								t1.add(ii);
							}
						coverAdjacent=union2sets(coverAdjacent, t1);
					}
					}
					for(int ii=1;ii<n;ii++) {
						if((S.contains(ii))&&(!listNeighbor.contains(ii))) {

						List<Integer> t2=new ArrayList<Integer>();
						for(int iii=0;iii<m;iii++)
							if(delta[ii][iii]==1) {
								t2.add(iii);
								
						}
						List<Integer> diff=intersection2sets(coverAdjacent, t2);
						if(diff.size()>=1) {
							//listNeighbor.add(ii);
							}
						listNeighbor.add(ii);
						}
					}
					
					//System.out.println("\nneighbors");
					//System.out.print(listNeighbor);
				// ag2=sc.nextInt();
				
				for(int j=0;j<listNeighbor.size();j++)
				{
					s2=listNeighbor.get(j);
					List<Integer> result =graph.path(s1, s2, graph);
				
					List<Integer> Ti= new ArrayList<Integer>();
			for(int k=0;k<result.size();k++) {
				if(result.get(k)!=0) {
			 List<Integer> T1= new ArrayList<Integer>();
				for(int ii=0;ii<m;ii++)
					if(delta[result.get(k)][ii]==1) {
						T1.add(ii);
					}
				Ti=union2sets(Ti, T1);
				}
			}
				List<Integer> supTargets=Difference2sets( Ti,coveredTarget);
				
				double v=0;
				v=graph.SPL[s1][s2];
				/*for(int k=0;k<result.size();k++)
					v=v+pi[result.get(k)];*/
				/**********************************/
				double f;
				double f1=Talpha-coveredTarget.size();
				
				/*if((supTargets.size()==0)) {
				//f=1/f1;
				f=Math.sqrt(Math.pow(v, 2)+1/Math.pow(1/f1,2));
				f=v;
				
			}
			
			else*/
				
				if(supTargets.size()>f1) {
					f= v/f1;
					
					
				}
				else {
					f= v/supTargets.size();

				}
			//f=v;
			//f= v/f1;
			//f=Math.sqrt(Math.pow(v, 2)+Math.pow(supTargets.size(),2));

			
			// System.out.print("v= "+ v+"suuppTragets= "+supTargets.size()+"f="+f);
			//int ag2=sc.nextInt();
			//if((f<fitness)&&((!cover.contains(s2))||(!cover.contains(s1)))) {
			if(f<fitness) {
				fitness=f;
				choosenSensor2=s2;
				choosenSensor1=s1;
			}
			
			}
			}
			
			//System.out.print("\nsors!!!!! with s1= "+choosenSensor1+" s2= "+choosenSensor2);
			//ag2=sc.nextInt();
			//graph.evaluateShortPath(pi,n );
			 List<Integer> result =graph.path(choosenSensor1, choosenSensor2, graph);
			//List<String> result = graph.shortestPath(Integer.toString(choosenSensor1), Integer.toString(choosenSensor2));
			 for(int i=0;i<result.size()-1;i++) {
		    	 Edge e=new Edge(result.get(i), result.get(i+1), pi);
		    	 if(equals(subEdges, e)==false) 
		    	 {
		    	 subEdges.add(new Edge(result.get(i), result.get(i+1), pi));
		    	 }
		     }
			// System.out.println("***"+listOfInteger );
				for(int k=0;k<result.size();k++)
				{if(!cover.contains(result.get(k))) {
					cover.add(result.get(k));
					
				}
				}
				for(int k=0;k<result.size();k++) {
					S.remove(new Integer(result.get(k)));
				}
				for(int k=0;k<cover.size();k++) {
					if(cover.get(k)!=0) {
					List<Integer> Ti= new ArrayList<Integer>();
					for(int ii=0;ii<m;ii++)
						if(delta[cover.get(k)][ii]==1) {
							Ti.add(ii);
						}
					coveredTarget=union2sets(coveredTarget, Ti);
					}
				}
			//System.out.println("talpha="+coveredTarget.size());
			//System.out.println(cover);
		
		}

		if((S.size()>=0)&&(coveredTarget.size()>=Talpha)) {
		//System.out.println("sors!!!!! with talpha="+coveredTarget.size());
		//System.out.println(cover);
		//ag2=sc.nextInt();
			/*******************super flux*************************/
			Individu  ind=new Individu(n);
			for(int k=0;k<cover.size();k++)
				ind.C[cover.get(k)]=1;
			ind=redundancyRemovalOperator(ind,edges,n,m,subEdges,delta,Talpha,pi,link);
			cover.clear();
			for(int k=0;k<ind.C.length;k++)
			{
				if(ind.C[k]==1) {
					cover.add(k);
				}
			}
			coveredTarget.clear();
				for(int k=0;k<cover.size();k++) {
					if(cover.get(k)!=0) {
					List<Integer> Ti= new ArrayList<Integer>();
					for(int ii=0;ii<m;ii++)
						if(delta[cover.get(k)][ii]==1) {
							Ti.add(ii);
						}
					coveredTarget=union2sets(coveredTarget, Ti);
					}
				}
			/************************************/
		for(int k=0;k<cover.size();k++)
			r=r+pi[cover.get(k)];
	
		r=1-r;
		}
		else {
			r=0;
		}
		/***********************************************/
		
		System.out.println("reduced cost="+r);
		/***************************************************/
		//ag2=sc.nextInt();
		
		return r;
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
	public List<Integer> Difference2sets(List<Integer> H1,List<Integer> H2)
	{

		 List<Integer> aDiffB = H1.stream()
			        .filter(i -> !H2.contains(i))
			        .collect(Collectors.toList());
		

	//System.out.println(aDiffB);
	return aDiffB;

	}
	 public List<Integer> intersection2sets(List<Integer> H1,List<Integer> H2)
	 {
	
		 List<Integer> intersection = H1.stream()
			        .filter(H2::contains)
			        .collect(Collectors.toList());
		
	
	 //System.out.println(intersection);
	 return intersection;
	
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
		boolean test=true;
		
		while(test==true)
		{
		
		
		Graph subgraph = new Graph(subEdges, n, pi,edges.size(),link);
		for(int i=0;i<subEdges.size();i++)
       {	
       	subgraph.addEdge(Integer.toString(subEdges.get(i).src), Integer.toString(subEdges.get(i).dest), subEdges.get(i).weight);
       }
			
		 List<pair> subEdge=new ArrayList<pair>();

		  for(int i=0;i<subEdges.size();i++)
	        {	
			  subEdge.add(new pair(subEdges.get(i).src, subEdges.get(i).dest));
			 
	        }
		int cnt = subEdge.size(); 
		
		Vector t = new Vector(); 
			
		int node = cnt + 1; 
		for(int i = 0; i < 1005; i++) 
		{ 
			t.add(new Vector()); 
		} 
	
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
		Random random = new Random();
		//random.setSeed(0);
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
   	
     	if((gfg.i>0)) {
  		 List<Integer> cover= new ArrayList<Integer>();
	        // add edges to the graph
	        for(int i=0;i<minimalInd.size;i++) {
	        	if(minimalInd.C[i]==1) {
	        		cover.add(i);
	        	}
	        }
	        int essai=0;
	        int s=-1;
	        int []ess=new int [gfg.i];
	        for(int hh=0;hh<gfg.i;hh++)
	        {
	        	ess[hh]=0;
	        }
	        while((essai<gfg.i)) {
	        	//System.out.println("sors111  ");
	        do {
	        	  s = random.nextInt(gfg.i);
			} while (ess[s]!=0);
	       // System.out.println("sors111  "+s);
			
			coveredTarget.clear();
			
			for(int i=0;i<cover.size();i++) 
			{
				if((cover.get(i)!=gfg.leaves[s])&&(cover.get(i)!=0))
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
			if (coveredTarget.size()<Talpha) {
				ess[s]=-1;
				essai++;	
			}
			else {
				break;
			}
	        }
	        
	       
			if(essai>=gfg.i) {
				test=false;
			}
			else {

			/*System.out.println("leaves:  ");
			for(int i=0;i<gfg.i;i++)
				if(gfg.leaves[i]!=0)
				{System.out.print( gfg.leaves[i] + " "); }
			System.out.println("\ns="+s+"choosen leave= "+gfg.leaves[s]);	
			 for(int i=0;i<subEdges.size();i++) {
				    System.out.println(subEdges.get(i).src+"->"+subEdges.get(i).dest);
				     }*/
			
		//	System.out.println("talpha"+coveredTarget.size()+"k="+gfg.i);
			//ag2=sc.nextInt();
			//System.out.println("after remove edes");
			 for(int i=0;i<subEdges.size();i++) {
				 if((subEdges.get(i).dest==gfg.leaves[s])) {
					 //System.out.println("yess");
					 subEdges.remove(i);
				 }
				     }
			 
			/* for(int i=0;i<subEdges.size();i++) {
				    System.out.println(subEdges.get(i).src+"->"+subEdges.get(i).dest);
				     }*/
			// int ag2=sc.nextInt();
			if(coveredTarget.size()>=Talpha) {
				
				 minimalInd.C[gfg.leaves[s]]=0;
				
							
			}
			gfg.leaves=removeTheElement(gfg.leaves, s);
			gfg.i=gfg.i-1;
			}
     	}
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
}