import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class subProblemGA {
	Scanner sc= new Scanner(System.in);
	int sizep=100;
	int MaxIT=2000;
	int maxInitDup=100;
	int maxDup=100;
	//List<Integer> cover;
	//List<Integer> coveredTarget;
	List<Individu> chroms;
	public double chromosome(int n, int m,int [][]delta,double Talpha,List<Edge>  edges,double []pi,double [][]SPL,Graph graph,List<Edge>  undirectededges,int [][]link) {
		
    	graph = new Graph(edges,n,pi,link);
   	 	for(int i=0;i<edges.size();i++)
        {	
        	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
        }
   
		
    	Population pop=new Population(sizep, MaxIT, maxInitDup, maxDup,n);
    	
		SPL=graph.evaluateShortPath(pi,n );
    	pop.InitializePopulation1(Talpha, n, m, graph, SPL, pi, delta, edges,undirectededges,link);

    	/*System.out.print("\n************************sizepop= "+sizep);
    	int ag2=sc.nextInt();*/
    	pop.calculeFitnessPopulation(pi);
    		
    	int itr=0;
    	int dup=0;
    	//double bestFitness=1000;
    	double bestFitness=pop.bestFiteness();
    	while((itr<MaxIT)&&(dup<maxDup)) {
    		
    		Individu C=new Individu(n);
    		
    		/*for(int i=0; i<pi.length;i++)
    			System.out.println("pi["+(i) +"]="+pi[i]);*/
    		//
    		Individu ind1 = new Individu(n);
    		Individu ind2 =  new Individu(n);
    		/*System.out.println("before ordering");
    		System.out.println("");
    		for(int i=0;i<pop.sizep;i++) {
    			for(int j=0;j<pop.p.get(i).size;j++) {
    				System.out.print(pop.p.get(i).C[j]+" ");
    			}
    			System.out.println(": "+pop.p.get(i).fitness);
    		}*/
    	
    		pop.tournement1(ind1, ind2);	
    		
    		/*System.out.println("after ordering");
    		System.out.println("");
    		for(int i=0;i<pop.sizep;i++) {
    			for(int j=0;j<pop.p.get(i).size;j++) {
    				System.out.print(pop.p.get(i).C[j]+" ");
    			}
    			System.out.println(": "+pop.p.get(i).fitness);
    		}
    		for(int j=0;j<ind1.size;j++) {
				System.out.print(ind1.C[j]+" ");
			}
			System.out.println(": "+ind1.fitness);
			for(int j=0;j<ind2.size;j++) {
				System.out.print(ind2.C[j]+" ");
			}
			System.out.println(": "+ind2.fitness);*/
			//int ag2=sc.nextInt();
    		C=pop.crossover(ind1, ind2,pi);
    		/*System.out.println("after crossover");
			for(int j=0;j<C.size;j++) {
				System.out.print(C.C[j]+" ");
			}
			System.out.println(": "+C.fitness);*/
    		C=pop.mutation1(ind1, ind2, C,pi);
    		/*System.out.println("after mutation");
			for(int j=0;j<C.size;j++) {
				System.out.print(C.C[j]+" ");
			}
			System.out.println(": "+C.fitness);*/
			//
    		List<Edge> subEdges = new ArrayList<>();
    		/*long startTime = System.currentTimeMillis();
    		long elapsedTime = 0L;*/
    		C=pop.coverFeasibilityOperator(C, n, m, Talpha, delta,pi);
    		C=pop.connectFeasibiltyOperator(C, n, m, graph, SPL, pi,edges,subEdges,link);
    		C=pop.redundancyRemovalOperator(C,edges,n,m,subEdges,delta,Talpha,pi,link);
    		/*elapsedTime = (new Date()).getTime() - startTime;
        	System.out.println("execution time pour construire une population: "+elapsedTime);
        	int ag2=sc.nextInt();*/
    		C.CalculeFitness(pi);
    		/*System.out.print("\n*********cover during iteration: ");
    		for(int j=0;j<C.size;j++) {
				System.out.print(C.C[j]+" ");
			}
			System.out.println(": "+C.fitness);*/
			//ag2=sc.nextInt();
    		
    		if(pop.existCover(C,pop.sizep)==false) {
    			//C.CalculeFitness(pi);
    			pop.updatePopulation(C);
    			dup=0;
    			
    			if(C.fitness>= bestFitness) {
    				itr++;
    			}
    			else {
    				 bestFitness=pop.bestFiteness();
    			}
    			
    			//itr++;
    			
    		}
    		else {
    			dup++;
    		}  
    		/*if(dup>maxDup) {
        		pop.sizep=itr;
        		break;
        	}*/
    		
    		//System.out.println("iterrr: "+itr);	
   		// 
    	
    	}
    	
    /*	System.out.println("");
		for(int i=0;i<pop.sizep;i++) {
			for(int j=0;j<pop.p.get(i).size;j++) {
				System.out.print(pop.p.get(i).C[j]+" ");
			}
			System.out.println(": "+pop.p.get(i).fitness);
		}
		
		int ag2=sc.nextInt();*/
		chroms = new ArrayList<Individu>();
		for(int i=0;i<pop.sizep;i++) {
			if(pop.p.get(i).fitness<1) {
				chroms.add(pop.p.get(i));
			}
		}
		/*for(int i=0;i<chroms.size();i++) {
			for(int j=0;j<chroms.get(i).size;j++) {
				System.out.print(chroms.get(i).C[j]+" ");
			}
			System.out.println(": "+chroms.get(i).fitness);
		}
		ag2=sc.nextInt();*/
    	Individu bestSolution=pop.calculeBestSolution(n);
    	bestSolution.CalculeFitness(pi);
    	//System.out.println("best individu");
    	/*for(int j=0;j<bestSolution.size;j++) {
    		System.out.print(bestSolution.C[j]+ " ");
    		
		}
    	System.out.println("\n fitness: "+bestSolution.fitness);
    	ag2=sc.nextInt();*/
    	
		/*cover = new ArrayList<Integer>();
    	for(int i=0;i<bestSolution.size;i++) {
    		if (bestSolution.C[i]==1) {
    			cover.add(i);
    		}
    	}*/
		/*System.out.print("*****coveredTargets: "+coveredTarget);
		System.out.print("*****cover: "+cover);*/
		//int ag2=sc.nextInt();
    	/*coveredTarget = new ArrayList<Integer>();
    	for(int i=1;i<cover.size();i++) {
    		//System.out.println("*****gene: "+cover.get(i));
				ArrayList<Integer> Ti= new ArrayList<Integer>();
				for(int j=0;j<m;j++) {
					if(delta[cover.get(i)][j]==1) {
						Ti.add(j);
					}
				}
				coveredTarget=union2sets(coveredTarget, Ti);
				
		}*/
    	/*for(int i=0;i<pop.sizep;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<pop.p.get(i).size;jj++) 
    		{
    			if(pop.p.get(i).C[jj]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println(": "+ pop.p.get(i).fitness);
    	}*/
		
		
		/*System.out.println("*****cover: "+cover);
		System.out.println("*****coveredTargets: "+coveredTarget);*/
		
    	//System.out.println("cover returned by sub problem: "+cover+"reduced cost"+(1-bestSolution.fitness));
    	return 1-bestSolution.fitness;
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
