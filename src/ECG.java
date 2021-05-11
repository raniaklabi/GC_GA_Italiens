import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ECG {

	public static void main(String[] args) throws IOException {
		double r=1;
		double bestLifetime;
		donnee  P1 = new donnee();
		double time_limit=3600000;
    	/*P1.Read_Data_v1("Exp10_15_1_Neighbour.txt");
    	P1.Print_Data_NosInstance_Groupe1();*/
		/*P1.Read_Data_neighbour("Exp20_15_5_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultECG.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);
		//P1.Read_Data_Francais("CMLP_03.dat");
		P1.link();
		P1.Print_Data_Francais();
		Scanner sc= new Scanner(System.in);
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		double initialPI[]=new double[P1.N];
		for(int i=0;i<P1.N;i++) {
			initialPI[i]=0;
		}
		
  
   	 
   	//graph1.printGraph(graph1, P1.N);
  //  int ag2=sc.nextInt();
    	List<Edge> edges= new ArrayList<>();
    	
    	for(int i=0;i<P1.N;i++) {
    		for(int j=0;j<P1.N;j++) {
    			if(P1.link[i][j]==1) {
    				edges.add(new Edge(i, j,initialPI));
    			//	edges.add(new Edge(i, j,pi));
    			}
    		}
    	}
    	/*List<Edge> edges = Arrays.asList(new Edge(0, 1,pi),new Edge(0, 2, pi),
                new Edge(1, 2,  pi),new Edge(1, 3,  pi), new Edge(2, 3,  pi),
                new Edge(2, 4,  pi), new Edge(3, 4,  pi),new Edge(3, 5,  pi),new Edge(4, 5,  pi)
                ,new Edge(4, 6,  pi),new Edge(5, 6,  pi),new Edge(5, 7,  pi),
                new Edge(6, 7,  pi),new Edge(6, 8,  pi),new Edge(7, 8,  pi),new Edge(7, 9,  pi),new Edge(8, 9,  pi),new Edge(1, 0,pi),new Edge(2, 0, pi),
                new Edge(2, 1,  pi),new Edge(3, 1,  pi), new Edge(3, 2,  pi),
                new Edge(4, 2,  pi), new Edge(4, 3,  pi),new Edge(5, 3,  pi),new Edge(5, 4,  pi)
                ,new Edge(6, 4,  pi),new Edge(6, 5,  pi),new Edge(7, 5,  pi),
                new Edge(7, 6,  pi),new Edge(8, 6,  pi),new Edge(8, 7,  pi),new Edge(9, 7,  pi),new Edge(9, 8,  pi));*/
    	double [][]SPL=new double[P1.N][P1.N];
    	GFG gfg=new GFG();
    	InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	Graph graph=null;
    	coverSets.calculeCoverSets(TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
    	// int ag2=sc.nextInt();
    	/*System.out.println("The population contains: ");
    	for(int i=0;i<coverSets.pop.sizep;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<coverSets.pop.p.get(i).size;jj++) 
    		{
    			if(coverSets.pop.p.get(i).C[jj]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println("");
    	}*/
    
    	
    	/*coverSets.K=coverSets.K+1;
    	coverSets.a[2][coverSets.K-1]=1;
    	coverSets.a[0][coverSets.K-1]=1;
    	for(int i=0;i<P1.M;i++)
    		{ 
			coverSets.b[i][coverSets.K-1]=1;}*/
    	
    	
    	bestLifetime=0;
    	MasterProb mb;
    	subProblemGA sph;
   	 	int occ=0;
			 while((r>0)) {
					elapsedTime = (new Date()).getTime() - startTime;
        			if(elapsedTime>=time_limit)
        			{
        			break;
        			}
				 mb=new MasterProb();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T);
				 sph=new subProblemGA();
				 r=sph.chromosome(P1.N, P1.M, P1.delta,TAalpha,edges,mb.pi,SPL,graph,P1.link);
				 if(r>0) {
				//System.out.println("\nheuristic");
			 // ag2=sc.nextInt();
    			/*coverSets.K=coverSets.K+1;
    			for(int i=0;i<sph.cover.size();i++)
    				coverSets.a[sph.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sph.coveredTarget.size();i++)
    				coverSets.b[sph.coveredTarget.get(i)][coverSets.K-1]=1;*/
					 for(int i=0;i<sph.chroms.size();i++) {
    					 coverSets.K=coverSets.K+1;
    					 List<Integer> 	cover = new ArrayList<Integer>();
    				    	for(int ii=0;ii<sph.chroms.get(i).size;ii++) {
    				    		if (sph.chroms.get(i).C[ii]==1) {
    				    			cover.add(ii);
    				    		}
    				    	}
    						
    				    	List<Integer>coveredTarget = new ArrayList<Integer>();
    				    	for(int ii=1;ii<cover.size();ii++) {
    				    		//System.out.println("*****gene: "+cover.get(i));
    								ArrayList<Integer> Ti= new ArrayList<Integer>();
    								for(int j=0;j<P1.M;j++) {
    									if(P1.delta[cover.get(ii)][j]==1) {
    										Ti.add(j);
    									}
    								}
    								coveredTarget=union2sets(coveredTarget, Ti);
    								
    						}
    				    	for(int ii=0;ii<cover.size();ii++)
    		    				coverSets.a[cover.get(ii)][coverSets.K-1]=1;
    		    			for(int ii=0;ii<coveredTarget.size();ii++)
    		    				coverSets.b[coveredTarget.get(ii)][coverSets.K-1]=1;
    					
    				}
					 
					 if(bestLifetime-mb.lifetime==0) {
        			occ++;	
        			}
        			else {
        			bestLifetime=mb.lifetime;
        			occ=0;}
        			if(occ>50) {
        				break;
        			}
    			//bestLifetime=mb.lifetime;
    			
    			
				 }
				 else{
						elapsedTime = (new Date()).getTime() - startTime;
	        			if(elapsedTime>=time_limit)
	        			{
	        			break;
	        			}
					// System.out.println("\nexacte");
					//int ag2=sc.nextInt();
	        			subProbModelExacte spme= new subProbModelExacte();
	        			r=spme.subProbExacte(mb.pi, P1.N, P1.M, P1.link, P1.delta, TAalpha);
	        			if(r>0) {
	        				coverSets.K=coverSets.K+1;
	        				for(int i=0;i<spme.cover.size();i++)
	            				coverSets.a[spme.cover.get(i)][coverSets.K-1]=1;
	            			for(int i=0;i<spme.coveredTarget.size();i++)
	            				coverSets.b[spme.coveredTarget.get(i)][coverSets.K-1]=1;
	            			
	        				 if(bestLifetime-mb.lifetime==0) {
		                			occ++;	
		                			}
		                			else {
		                			bestLifetime=mb.lifetime;
		                			occ=0;}
		                			if(occ>50) {
		                				break;
		                			}
	        			}
	        			else {
	    
	        				 bestLifetime=mb.lifetime;
	        				 System.out.println("End!!!!!!!!");
	        		    	//System.out.println("Optimum with lifetime="+mb.lifetime);
	        		    	
	        			}
	        		}
    			
				// System.out.println("reduced cost= "+ r);
 		    	System.out.println("Optimum with lifetime="+bestLifetime);

 		   	elapsedTime = (new Date()).getTime() - startTime;
			if(elapsedTime>=time_limit)
			{
			break;
			}
    		
			 }
				elapsedTime = (new Date()).getTime() - startTime;
				pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
				pw.newLine();
				pw.close();
		    	System.out.println("End!!!!!!!! with best lifetime"+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);

    		
	}
	
	public static List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
}

