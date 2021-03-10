import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
   	 	
		cover.add(0);
		List<Integer> S=new ArrayList<Integer>();
		for(int i=1;i<n;i++)
			S.add(i);
	
		
		while ((coveredTarget.size()<Talpha)&&(S.size()>0)) {
			List<Integer> listNeighbor=new ArrayList<Integer>();
			for(int i=0;i<cover.size();i++)
			{	
				List<Node>  neighbors = new ArrayList<>();
				neighbors=	graph.adj_list.get(cover.get(i));
				for(int iii=0;iii<neighbors.size();iii++) {
						if((S.contains(neighbors.get(iii).value))&&(!listNeighbor.contains(neighbors.get(iii).value)) ){
						listNeighbor.add(neighbors.get(iii).value);}
					}
			}
			//System.out.println(listNeighbor);
			List<Integer> coverAdjacent=new ArrayList<Integer>();
			for(int k=0;k<cover.size();k++)
			{
				List<Integer> t1=new ArrayList<Integer>();
				for(int ii=0;ii<m;ii++)
					if(delta[cover.get(k)][ii]==1) {
						t1.add(ii);
					}
				coverAdjacent=union2sets(coverAdjacent, t1);
			}
			//System.out.println(coverAdjacent);
			for(int ii=0;ii<n;ii++) {
				//if((S.contains(ii))&&(!listNeighbor.contains(ii))&&(!cover.contains(ii))) {
				//if((listNeighbor.contains(ii)==false)&&(S.contains(ii)==true)) {
				if((listNeighbor.contains(ii)==false)) {
				List<Integer> t2=new ArrayList<Integer>();
				for(int iii=0;iii<m;iii++)
					if(delta[ii][iii]==1) {
						t2.add(iii);
						
				}
				//List<Integer> diff=Difference2sets(t2,coverAdjacent);
				
				List<Integer> diff=intersection2sets(t2,coverAdjacent);
				
				/*System.out.println(coverAdjacent);
				System.out.println(t2);
				System.out.println(diff);
				int ag2=sc.nextInt();*/
				
				if(diff.size()>=1) {
					listNeighbor.add(ii);
					}
				
				}
			}
			
			double fitness=1000;
			double f=0;
			int choosenSensor2 = listNeighbor.get(0);
			int choosenSensor1=cover.get(0);
			boolean test=false;
			for(int i=0;i<cover.size();i++)
			{	
				for(int j=0;j<listNeighbor.size();j++)
				{	
			List<Integer> result =graph.path(cover.get(i), listNeighbor.get(j), graph);
			 List<Integer> Ti= new ArrayList<Integer>();
				for(int ii=0;ii<m;ii++)
					if(delta[listNeighbor.get(j)][ii]==1) {
						Ti.add(ii);
					}
				
				
				List<Integer> supTargets=Difference2sets( Ti,coveredTarget);
				
				double v=0;
				for(int k=0;k<result.size();k++)
					v=v+pi[result.get(k)];
				List<Integer> coveredTargetByChemin= new ArrayList<Integer>();
				for(int k=0;k<result.size();k++) {
					List<Integer> t1=new ArrayList<Integer>();
					for(int ii=0;ii<m;ii++)
						if(delta[result.get(k)][ii]==1) {
							t1.add(ii);
						}
					coveredTargetByChemin=union2sets(coverAdjacent, t1);
					
				}
				//double f2=coveredTargetByChemin.size();
				double f1=Talpha-coveredTarget.size();
				if((supTargets.size()==0)) {
					//f=1/f1;
					f=Math.sqrt(Math.pow(v, 2)+1/Math.pow(1/f1,2));
					
				}
				
				else
			
					if(supTargets.size()>f1) {
					f= v/f1;
					
				}	
				else {
					f= v/supTargets.size();
				}
			// f= v/supTargets.size();
				//f=Math.sqrt(Math.pow(v, 2)+1/Math.pow(1.0/f1,2));

			// f=Math.sqrt(Math.pow(v, 2)+Math.pow(1.0/supTargets.size(),2));
				// System.out.println(cover);
				// System.out.println(listNeighbor);
			
			//System.out.println("\ns1= "+cover.get(i)+"s2="+listNeighbor.get(j)+"v= "+ v+"suuppTragets= "+supTargets.size()+"f1="+f1+"f="+f);
			//int ag2=sc.nextInt();
			//if((f<=fitness)&&(!cover.contains(listNeighbor.get(j)))) {
			if((f<=fitness)&&(!cover.contains(listNeighbor.get(j))||!cover.contains(cover.get(i)))) {
				fitness=f;
				choosenSensor2=listNeighbor.get(j);
				choosenSensor1=cover.get(i);
				test=true;
			}
			/*else {
				Random random = new Random();
				int s1=0;int s2=0;
				while(s1==s2) {
					s1=random.nextInt(cover.size());
					s2=random.nextInt(listNeighbor.size());
				}
				choosenSensor2=listNeighbor.get(s2);
				choosenSensor1=cover.get(s1);
			}*/
			
			}
				
				//System.out.println("i="+i);	
			
			}
			if(test==false) {
				//System.out.println("sort chosenn sensor1="+choosenSensor1+" choosen sensor2="+ choosenSensor2);
				//System.out.println("f="+f);
				//int ag2=sc.nextInt();
			}
			//System.out.print("\nsors!!!!! with s1= "+choosenSensor1+" s2= "+choosenSensor2);
			//ag2=sc.nextInt();
			//graph.evaluateShortPath(pi,n );
			//System.out.println("sort chosenn sensor1="+choosenSensor1+" choosen sensor2="+ choosenSensor2+"fitness="+fitness);

			 List<Integer> result =graph.path(choosenSensor1, choosenSensor2, graph);
			//List<String> result = graph.shortestPath(Integer.toString(choosenSensor1), Integer.toString(choosenSensor2));
			
			// System.out.println("***"+listOfInteger );
			for(int k=0;k<result.size();k++)
			{if(!cover.contains(result.get(k))) {
				cover.add(result.get(k));
				S.remove(new Integer(result.get(k)));
				
				
			}
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
			/*System.out.println("talpha="+coveredTarget.size());
			System.out.println(cover);
			System.out.println(S);
			System.out.println(listNeighbor);
			System.out.println("sort chosenn sensor1="+choosenSensor1+" choosen sensor2="+ choosenSensor2);*/

			//int ag2=sc.nextInt();
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
		//System.out.println("reduced cost="+(1-r));
		//ag2=sc.nextInt();
		System.out.println("sors!!!!!!");
		//int ag2=sc.nextInt();
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
