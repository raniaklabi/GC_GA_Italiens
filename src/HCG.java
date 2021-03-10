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
		P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);
		// System.out.println(TAalpha);
		//
		//P1.Read_Data_Francais("CMLP_01.dat");
		P1.link();
		P1.Print_Data_Francais();
		//int  ag2=sc.nextInt();
    	//double pi[]= {0,0,0,0,0,0,0,0,0,0};
		
		
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
    	double averageTime=0;
		double averageLifetime=0;
		double averageCS=0;
		double minLifetime=1000;
		double maxLifetime=0;
		for(int lll=0;lll<5;lll++) {
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
    	coverSets.calculeCoverSets(TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
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
				 System.out.println("k="+coverSets.K);
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T);
				/* System.out.println( " **");
				 for(int k=0;k<mb.t.length;k++) {
						
						System.out.print(mb.t[k]+ " **");
						}
				 
				 System.out.println("\n****a****");	
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
	    			}
	    			int ag2=sc.nextInt();
				
				
				 System.out.println( " **"+coverSets.K);
				 System.out.println( " **"+mb.t.length);*/
				/* for(int i=0;i<mb.t.length;i++) {
					 if(mb.t[i]==0) {
						 //s--;
						  mb.t=removeTheElement(mb.t,i);
						 coverSets.a=deleteColumn(coverSets.a, i);
						 coverSets.b=deleteColumn(coverSets.b, i);
						 i--;
						 coverSets.K--;
					 }
				 }*/
				 sph=new subProblemGA();
				 r=sph.chromosome(P1.N, P1.M, P1.delta,TAalpha,edges,mb.pi,SPL,graph,edgesUndirected,P1.link);
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
				
				 
				 
				 
				/*coverSets.K=coverSets.K+1;
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
        		
        			
				// ag2=sc.nextInt();	
    	}
			 elapsedTime = (new Date()).getTime() - startTime;
			 averageTime=averageTime+elapsedTime;
			 averageLifetime=averageLifetime+bestLifetime;
			 averageCS=averageCS+coverSets.K;
			 if(bestLifetime<minLifetime) {
				 minLifetime=bestLifetime;
			 }
			 if(bestLifetime>maxLifetime) {
				 maxLifetime=bestLifetime;
			 }
			 //int ag2=sc.nextInt();
		}
		pw.append("file"+args[0]+"Talpha"+TAalpha+" L="+(averageLifetime/5)+"execution time: "+(averageTime/1000)/5+" seconde"+"number CS="+averageCS/5+"min: "+minLifetime+"max: "+maxLifetime);
		pw.newLine();	
		pw.close();

    	System.out.println("End!!!!!!!! with best lifetime"+(averageLifetime/5)+"execution time: "+(averageTime/1000)/5+" seconde"+"number CS="+averageCS/5);
    
	}
	public static List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
	public static int[][] deleteColumn(int[][] args,int col)
	{
	    int[][] nargs=null;
	    if(args != null && args.length > 0 && args[0].length > col)
	    {
	        nargs = new int[args.length][args[0].length-1];
	        for(int i=0; i<args.length; i++)
	        { 
	            int newColIdx = 0;
	            for(int j=0; j<args[i].length; j++)
	            {
	                if(j != col)
	                {
	                    nargs[i][newColIdx] = args[i][j];
	                    newColIdx++;
	                }               
	            }
	        }
	    }
	    return nargs; 
	}
	public static double[] removeTheElement(double[] arr, int index) 
	{ 

	if (arr == null
	|| index < 0
	|| index >= arr.length) { 

	return arr; 
	} 

	// Create another array of size one less 
	double[] anotherArray = new double[arr.length - 1]; 

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
