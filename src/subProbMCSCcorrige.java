import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
		Graph graph = new Graph(edges,n,pi,link);
		
		
   	 	for(int i=0;i<edges.size();i++)
        {	
        	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
        }
   	 	
   	 	graph.evaluateShortPath(pi,n );
   	 	//graph.printSolution(graph.predMatrix,n);
		//Graph.printGraph(graph,n);
		
		//int ag2=sc.nextInt();
			
   	 	//Graph.printGraph(graph,n);
		cover.add(0);
		List<Integer> S=new ArrayList<Integer>();
		for(int i=1;i<n;i++)
			S.add(i);
		/*System.out.println("\n Deeeelta: ");
	    for(int i=0;i<n;i++)
	    {
	        for(int j=0;j<m;j++)
	        	System.out.print(delta[i][j] +" ");
			System.out.println("");
	    }*/
		while ((coveredTarget.size()<Talpha)&&(S.size()>0)) {
			int choosenSensor2=S.get(0);
			int choosenSensor1=cover.get(0);
			int s1=0;
			int s2=0;
			double fitness=1000;
			List<Node>  neighbors = new ArrayList<>();
			List<Integer> listNeighbor=new ArrayList<Integer>();
			for(int i=0;i<cover.size();i++)
			
			{	//System.out.println(cover);
				//System.out.println("i="+i);
				s1=cover.get(i);
				//System.out.println("sensor: "+s1);
				//System.out.println("neighbors");
				
					neighbors=	graph.adj_list.get(s1);
					/*for(int iii=0;iii<neighbors.size();iii++) {
						System.out.print(neighbors.get(iii).name+"|");
					}*/
					//List<Integer> listNeighbor=new ArrayList<Integer>();
					for(int iii=0;iii<neighbors.size();iii++) {
						if((S.contains(neighbors.get(iii).value))&&(!listNeighbor.contains(neighbors.get(iii).value)) ){
						//if(S.contains(neighbors.get(iii).value)) {
						listNeighbor.add(neighbors.get(iii).value);}
					}
					List<Integer> coverAdjacent=new ArrayList<Integer>();
					for(int k=1;k<cover.size();k++)
					{
						List<Integer> t1=new ArrayList<Integer>();
						for(int ii=0;ii<m;ii++)
							if(delta[cover.get(k)][ii]==1) {
								t1.add(ii);
							}
						coverAdjacent=union2sets(coverAdjacent, t1);
					}
					for(int ii=0;ii<n;ii++) {
						if((S.contains(ii))&&(!listNeighbor.contains(ii))) {

						List<Integer> t2=new ArrayList<Integer>();
						for(int iii=0;iii<m;iii++)
							if(delta[ii][iii]==1) {
								t2.add(iii);
								
						}
						List<Integer> diff=intersection2sets(coverAdjacent, t2);
						if(diff.size()>=1) {
							//List<Integer> l=new ArrayList<Integer>();
							//if(S.contains(ii)) {
							//l.add(ii);
							//listNeighbor=union2sets(listNeighbor, l);
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
				//System.out.println("\ns1= "+s1+"s2="+s2);
				//ag2=sc.nextInt();
					
			//graph.evaluateShortPath(pi,n );
			List<Integer> result =graph.path(s1, s2, graph);
				//List<String>result = graph.shortestPath(Integer.toString(s1), Integer.toString(s2));
			// System.out.print("short Path"+ result);
			// ag2=sc.nextInt();
			 List<Integer> Ti= new ArrayList<Integer>();
				for(int ii=0;ii<m;ii++)
					if(delta[s2][ii]==1) {
						Ti.add(ii);
					}
				List<Integer> supTargets=Difference2sets( Ti,coveredTarget);
			//	System.out.print("SUpp Targets"+supTargets);
				//ag2=sc.nextInt();
				double v=0;
				for(int k=1;k<result.size();k++)
					v=v+pi[result.get(k)];
			double f= v/supTargets.size();
			// System.out.print("v= "+ v+"suuppTragets= "+supTargets.size()+"f="+f);
			// ag2=sc.nextInt();
			//if((f<fitness)&&((!cover.contains(s2))||(!cover.contains(s1)))) {
			if(f<fitness) {
				fitness=f;
				choosenSensor2=s2;
				choosenSensor1=s1;
			}
			
			}
				//neighbors.clear();
				//System.out.print("\nsors*****"+i);
				//ag2=sc.nextInt();
			}
			
			//System.out.print("\nsors!!!!! with s1= "+choosenSensor1+" s2= "+choosenSensor2);
			//ag2=sc.nextInt();
			//graph.evaluateShortPath(pi,n );
			 List<Integer> result =graph.path(choosenSensor1, choosenSensor2, graph);
			//List<String> result = graph.shortestPath(Integer.toString(choosenSensor1), Integer.toString(choosenSensor2));
			
			// System.out.println("***"+listOfInteger );
			for(int k=1;k<result.size();k++)
			{if(!cover.contains(result.get(k))) {
				cover.add(result.get(k));
			S.remove(new Integer(result.get(k)));
			 List<Integer> Ti= new ArrayList<Integer>();
				for(int ii=0;ii<m;ii++)
					if(delta[result.get(k)][ii]==1) {
						Ti.add(ii);
					}
				coveredTarget=union2sets(coveredTarget, Ti);
			}
			}
			//System.out.println("talpha="+coveredTarget.size());
			//System.out.println(cover);
		}
		if(S.size()>0) {
		//System.out.println("sors!!!!! with talpha="+coveredTarget.size());
		//System.out.println(cover);
		//ag2=sc.nextInt();
		for(int k=0;k<cover.size();k++)
		r=r+pi[cover.get(k)];}
		else {
			r=1;
		}
		System.out.println("reduced cost="+(1-r));
		//ag2=sc.nextInt();
		
		return 1-r;
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
}
