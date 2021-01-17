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

public class ECG {

	public static void main(String[] args) throws IOException {
		double r=1;
		double bestLifetime;
		donnee  P1 = new donnee();
    	/*P1.Read_Data_v1("Exp10_15_1_Neighbour.txt");
    	P1.Print_Data_NosInstance_Groupe1();*/
		/*P1.Read_Data_neighbour("Exp20_15_5_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		//P1.Read_Data_Francais("CMLP_03.dat");
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultECG.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);
		P1.link();
		P1.Print_Data_Francais();
		Scanner sc= new Scanner(System.in);
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
    	Graph graph1 = new Graph(edgesUndirected,P1.N,initialPI,P1.link);
   	 for(int i=0;i<edgesUndirected.size();i++)
        {	
        	graph1.addEdge(Integer.toString(edgesUndirected.get(i).src), Integer.toString(edgesUndirected.get(i).dest), edgesUndirected.get(i).weight);
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
    	double [][]SPL=new double[100][100];
    	long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
    	InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	Graph graph=null;
    	coverSets.calculeCoverSets(TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edgesUndirected,P1.link);
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
				
				 mb=new MasterProb();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T);
				/* System.out.println("****a****");	
					for (int i = 0; i <P1.N; i++)
	     			{	double s=0;
	     				for (int k = 0; k <coverSets.K; k++)
	     				{		s=s+(coverSets.a[i][k])*(mb.t[k]);
	     						System.out.print(coverSets.a[i][k]+" ");
	     						
	     					
	     				}
	     				if(s<=P1.T[i]) System.out.print(": True");
							else System.out.print(": false");
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
	     			// ag2=sc.nextInt();
				 sph=new subProblemGA();
				 r=sph.chromosome(P1.N, P1.M, P1.delta,TAalpha,edges,mb.pi,SPL,graph,edgesUndirected,P1.link);
    			
				 if(r>0) {
				System.out.println("\nheuristic");
			 // ag2=sc.nextInt();
    			coverSets.K=coverSets.K+1;
    			System.out.println("new cover set: "+ sph.cover);
    			System.out.println("new cover set: "+ sph.coveredTarget);
    			System.out.println("reduced cost= "+ r);
    			
    			System.out.println("K: "+ coverSets.K);
    			
    			for(int i=0;i<sph.cover.size();i++)
    				coverSets.a[sph.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sph.coveredTarget.size();i++)
    				coverSets.b[sph.coveredTarget.get(i)][coverSets.K-1]=1;
    			if(bestLifetime-mb.lifetime==0) {
        			occ++;	
        			}
        			else {
        			bestLifetime=mb.lifetime;
        			occ=0;}
        			if(occ>30) {
        				break;
        			}
    			//bestLifetime=mb.lifetime;
    			
    			
				 }
				 else{
					 System.out.println("\nexacte");
					// int ag2=sc.nextInt();
	        			subProbModelExacte spme= new subProbModelExacte();
	        			r=spme.subProbExacte(mb.pi, P1.N, P1.M, P1.link, P1.delta, TAalpha);
	        			if(r>0) {
	        				coverSets.K=coverSets.K+1;
	        				 System.out.println("\n");
	        				for(int i=0;i<spme.cover.size();i++)
	            				coverSets.a[spme.cover.get(i)][coverSets.K-1]=1;
	            			for(int i=0;i<spme.coveredTarget.size();i++)
	            				coverSets.b[spme.coveredTarget.get(i)][coverSets.K-1]=1;
	            			System.out.println("cover: "+spme.cover);	
	            			System.out.println("covered Target: "+spme.coveredTarget);	
	            			if(bestLifetime-mb.lifetime==0) {
	                			occ++;	
	                			}
	                			else {
	                			bestLifetime=mb.lifetime;
	                			occ=0;}
	                			if(occ>30) {
	                				break;
	                			}
	            			//bestLifetime=mb.lifetime;
	            			System.out.println("Optimum with lifetime="+bestLifetime);
	            			// ag2=sc.nextInt();
	        			}
	        			else {
	        				
	        		    	System.out.println("End!!!!!!!!");
	        		    	System.out.println("Optimum with lifetime="+mb.lifetime);
	        		    	
	        			}
	        		}
    			
				 System.out.println("reduced cost= "+ r);
 		    	System.out.println("Optimum with lifetime="+bestLifetime);

    			// System.out.println("\nheuristic");
			//	int ag2=sc.nextInt();
    		
			 }
			 

				elapsedTime = (new Date()).getTime() - startTime;
				pw.append("file"+args[0]+"Talpha"+TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde");
				pw.newLine();	
				pw.close();
		    	System.out.println("End!!!!!!!! with best lifetime"+bestLifetime+"execution time: "+elapsedTime/1000+" seconde");

    		
	}
	

}

