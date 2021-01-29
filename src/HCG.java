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

public class HCG {

	public static void main(String[] args) throws IOException {
		Scanner sc= new Scanner(System.in);
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\result.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		double time_limit=3600000;
		
		donnee  P1 = new donnee();
    	/*P1.Read_Data_v1("Exp10_15_1_Neighbour.txt");
    	P1.Print_Data_NosInstance_Groupe1();*/
		/*P1.Read_Data_neighbour("Exp20_15_1_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		/*P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);
		 System.out.println(TAalpha);*/
		//
		P1.Read_Data_Francais("CMLP_01.dat");
		P1.link();
		//P1.Print_Data_Francais();
		//int  ag2=sc.nextInt();
    	//double pi[]= {0,0,0,0,0,0,0,0,0,0};
		
		double r=1;
		double bestLifetime;
		double [][]SPL=new double[P1.N][P1.N];
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		double initialPI[]=new double[P1.N];
		for(int i=0;i<P1.N;i++) {
			initialPI[i]=0;
		}
	

		List<Edge> edgesUndirected= new ArrayList<>();
    	
    	for(int i=0;i<P1.N;i++) {
    		for(int j=i+1;j<P1.N;j++) {
    			if(P1.link[i][j]==1) {
    				edgesUndirected.add(new Edge(i, j,initialPI));
    			//	edges.add(new Edge(i, j,pi));
    			}
    		}
    	}
    	List<Edge> edges= new ArrayList<>();
    	
    	for(int i=0;i<P1.N;i++) {
    		for(int j=0;j<P1.N;j++) {
    			if(P1.link[i][j]==1) {
    				edges.add(new Edge(i, j,initialPI));
    			//	edges.add(new Edge(i, j,pi));
    			}
    		}
    	}
	
    	InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	Graph graph=null;
    	coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
    	//System.out.println("\nThe population contains: ");
    	/*for(int i=0;i<coverSets.pop.sizep;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<coverSets.pop.p.get(i).size;jj++) 
    		{
    			if(coverSets.pop.p.get(i).C[jj]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println(": "+coverSets.pop.p.get(i).fitness);
    	}
    	int ag2=sc.nextInt();*/

    	bestLifetime=0;
    	MasterProb mb;
    	subProblemGA sph;
    	 int occ=0;
			 while((r>0)) {
    	
    	
				 mb=new MasterProb();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T);
				 sph=new subProblemGA();
				 r=sph.chromosome(P1.N, P1.M, P1.delta,P1.TAalpha,edges,mb.pi,SPL,graph,edgesUndirected,P1.link);
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
				
				 
				 
				 
				/* coverSets.K=coverSets.K+1;
    			for(int i=0;i<sph.cover.size();i++)
    				coverSets.a[sph.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sph.coveredTarget.size();i++)
    				coverSets.b[sph.coveredTarget.get(i)][coverSets.K-1]=1;*/
				// int ag2=sc.nextInt();
				/* System.out.println("****a****");	
    			for (int i = 0; i <P1.N; i++)
    			{	
    				for (int k = 0; k <coverSets.K; k++)
    				{
    						System.out.print(coverSets.a[i][k]+" ");	
    					
    				}
    				System.out.println("");	
    			}
    			System.out.println("****b****");	
    			for (int i = 0; i <P1.M; i++)
    			{	
    				for (int k = 0; k <coverSets.K; k++)
    				{
    						System.out.print(coverSets.b[i][k]+" ");	
    					
    				}
    				System.out.println("");	
    			}*/
    			
    			/*if(mb.lifetime-bestLifetime<=Math.pow(10, -5)) {
        			break;
        			}
        			else {
        				bestLifetime=mb.lifetime;
    				}*/
    			System.out.println(" lifetime="+mb.lifetime);	
    			if(bestLifetime-mb.lifetime==0) {
        			occ++;	
        			}
        			else {
        			bestLifetime=mb.lifetime;
        			occ=0;}
        			if(occ>50) {
        				break;
        			}
        			elapsedTime = (new Date()).getTime() - startTime;
        			if(elapsedTime>=time_limit)
        			{
        			break;
        			}
        			
				// ag2=sc.nextInt();	
    	}
			 elapsedTime = (new Date()).getTime() - startTime;
			/* pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
			 pw.newLine();	
			 pw.close();*/
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
