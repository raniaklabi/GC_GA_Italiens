import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitialCoverSetsHeuristic {
	public int K=2;
	int MaxIT=100;
	int maxInitDup=100;
	int maxDup=100;
	public int a[][];
	public int b[][];
	Population pop;
	
	public List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
	public void calculeCoverSets(double Talpha,int n,int m,Graph graph,double [][]SPL,double []pi,int [][]delta,List<Edge> edges,List<Edge> undirectededges,int[][]link) {
		Scanner sc= new Scanner(System.in);
    	graph = new Graph(edges,n,pi,link);
    	 for(int i=0;i<edges.size();i++)
         {	
         	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
         }
    	 SPL=graph.evaluateShortPath(pi,n );
		pop=new Population(K, MaxIT, maxInitDup, maxDup,n);
		pop.InitializePopulation1(Talpha, n, m, graph, SPL, pi, delta, edges,undirectededges,link);
		K=pop.sizep;
		a= new int[n][100000];
		b= new int[m][100000];
		for(int k=0;k<pop.sizep;k++) {
		for(int i=0;i<n;i++) {
			a[i][k]=pop.p.get(k).C[i];
		}
		}
		for(int k=0;k<pop.sizep;k++) {
		List<Integer> coveredTarget= new ArrayList<Integer>();
		for(int i=1;i<n;i++) {
			if(pop.p.get(k).C[i]==1) {
				ArrayList<Integer> Ti= new ArrayList<Integer>();
				for(int j=0;j<m;j++) {
					if(delta[i][j]==1) {
						Ti.add(j);
					}
				}
				coveredTarget=union2sets(coveredTarget, Ti);
			}
		}
		
		for(int i=0;i<coveredTarget.size();i++) {
			b[coveredTarget.get(i)][k]=1;
			
		}
		}
	
	}
}
