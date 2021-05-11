import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.text.DecimalFormat;
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
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\result.dat");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		File fichier1 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\ETHCG.dat");
		BufferedWriter pw1 = new BufferedWriter(new FileWriter(fichier1,true)) ;
		File fichier2 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\averageAmountTimeTargetHCG.dat");
		BufferedWriter pw2 = new BufferedWriter(new FileWriter(fichier2,true)) ;
		File fichier3 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\information.dat");
		BufferedWriter pw3 = new BufferedWriter(new FileWriter(fichier3,true)) ;
		double time_limit=3600000;
		DecimalFormat df = new DecimalFormat("0.00");
		donnee  P1 = new donnee();
		
    	/*P1.Read_Data_v1("Exp10_15_1_Neighbour.txt");
    	P1.Print_Data_NosInstance_Groupe1();*/
		/*P1.Read_Data_neighbour("Exp20_15_1_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		/*P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);*/
		P1.Read_Data_Francais("CMLP_01.dat");
		P1.alpha=P1.betha=1;
		P1.link();
		P1.Print_Data_Francais();
		
		double averageAmountTimeTarget[] =new double[P1.M];
    	double averageTime=0;
		double averageLifetime=0;
		double averageCS=0;
		double minLifetime=1000;
		double maxLifetime=0;
		double bestLifetime;
		
		//pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" ");
		pw3.append("nbreItr  "+" nbreCoverSet  "+"iterTime");

		for(int lll=0;lll<5;lll++) {
			
		//int ag2=sc.nextInt();
		double r=1;
		
		double [][]SPL=new double[P1.N][P1.N];
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		double initialPI[]=new double[P1.N];
		for(int i=0;i<P1.N;i++) {
			initialPI[i]=0;
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
    	coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
    	
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
    	}
    	ag2=sc.nextInt();*/
    	bestLifetime=0;
    	MasterProb mb=null;
    	subProblemGA sph=null;
    	 int occ=0;
    	 int nbreItr=0;
    		
			 while((r>0)) {
				 long startTimeItr = System.currentTimeMillis();
		    		long elapsedTimeItr = 0L;
				 nbreItr++;
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
				 r=sph.chromosome(P1.N, P1.M, P1.delta,P1.TAalpha,edges,mb.pi,SPL,graph,P1.link);
				 
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
					elapsedTimeItr = (new Date()).getTime() - startTimeItr;
					pw3.append(nbreItr+"  "+sph.chroms.size()+"  "+elapsedTimeItr);
					pw3.newLine();	
				
				 
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
    				//ag2=sc.nextInt();
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
			 //pw.append(df.format(bestLifetime)+" ;");
			// pw1.append(df.format(bestLifetime)+"  ");
			 System.out.println(coverSets.K);
			 System.out.println(mb.t.length);
				for(int i=0;i<P1.M;i++)	
				{
					double ss=0;
					for (int k = 0; k <mb.t.length; k++)
					{if(mb.t[k]>0)
					{
						ss=ss+(coverSets.b[i][k]*mb.t[k]);
					}
						
					}
					averageAmountTimeTarget[i]=averageAmountTimeTarget[i]+ss;
				}
		}
		/*pw1.newLine();
		pw1.close();
		pw.append(" AverageL="+df.format((averageLifetime/5))+"execution time: "+df.format((averageTime/1000)/5)+" seconde"+"number CS="+df.format(averageCS/5)+"min: "+df.format(minLifetime)+"max: "+df.format(maxLifetime));
		pw.newLine();	
		pw.close();
		for (int i = 0; i <P1.M; i++)
	    {
		pw2.append(df.format(averageAmountTimeTarget[i]/5)+ " ");
		
	    }
		pw2.append(df.format(averageLifetime/5));
		pw2.newLine();	
		pw2.close();*/
		
    	System.out.println("End!!!!!!!! with best lifetime"+(averageLifetime/5)+"execution time: "+(averageTime/1000)/5+" seconde"+"number CS="+averageCS/5);
    	pw3.close();
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
