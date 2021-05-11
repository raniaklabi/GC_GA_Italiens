import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class MCSCBeta {

	public static void main(String[] args) throws IOException {
		Scanner sc= new Scanner(System.in);
		donnee  P1 = new donnee();
		double time_limit=3600000;
		/*P1.Read_Data_neighbour("Exp20_15_1_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSCBeta.dat");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		File fichier2 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\averageAmountTimeTargetMCSCBeta.dat");
		BufferedWriter pw2 = new BufferedWriter(new FileWriter(fichier2,true)) ;
		DecimalFormat df = new DecimalFormat("0.00");

		/*P1.Read_Data_Francais("CMLP_04.dat");
		P1.alpha=P1.betha=0.7;*/
		P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);
		P1.link();
		P1.Print_Data_Francais();
		double averageAmountTimeTarget[] =new double[P1.M];
		//System.out.println(P1.alpha+" "+P1.betha+" "+P1.TAalpha);
		//int ag2=sc.nextInt();
		//for(int lll=0;lll<5;lll++) {
			double r=1;
			double bestLifetime;
			double [][]SPL=new double[P1.N][P1.N];
			long startTime = System.currentTimeMillis();
			long elapsedTime = 0L;
		//int ag2=sc.nextInt();
		
		
    	//double pi[]= {0,0,0,0,0,0,0,0,0,0};
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
    	
    	Graph graph=null;
    	
    	bestLifetime=0;
    	MasterProbBeta mb=null;
    	//MasterProbSimplex mb;
    	subProbMCSCBeta sphMCSC=null;
    	//InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	//coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edges,P1.link);
    	/*graph = new Graph(edges,P1.N,initialPI,P1.link);
   	 	for(int i=0;i<edges.size();i++)
        {	
        	graph.addEdge(Integer.toString(edges.get(i).src), Integer.toString(edges.get(i).dest), edges.get(i).weight);
        }
   	 	SPL=graph.evaluateShortPath(initialPI,P1.N );*/
    	InitialCoverSet coverSets=new InitialCoverSet();
    	coverSets.calculeCoverSets(P1.TAalpha,P1.betha, P1.N, P1.M,P1.delta,P1.link,P1.T,P1.max_energie());
    	////coverSets.pop.calculeFitnessPopulation(initialPI);
    	/*System.out.println("\nThe population contains: ");
    	for(int i=0;i<coverSets.K;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<P1.N;jj++) 
    		{
    			if(coverSets.a[jj][i]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println();
    	}
    	for(int i=0;i<P1.M;i++) {

    		for(int jj=0;jj<coverSets.K;jj++) 
    		{
    			
    				System.out.print(coverSets.b[i][jj]+" ");
    			
    				
    		}
    		System.out.println();
    	}
    	int ag2=sc.nextInt();*/

    	int nbretimes=0;
    	int occ=0;
			 while( r>0) {
				 nbretimes++;
				 elapsedTime = (new Date()).getTime() - startTime;
     			if(elapsedTime>=time_limit)
     			{
     			break;
     			}
    	
				 mb=new MasterProbBeta();
				 
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);
				
		    	
     			/*double c[]=new double [coverSets.K];
     			for(int q=0;q<c.length;q++) {
     				c[q]=1;
     			}
     			double b[]=new double [P1.N];
     			for(int q=0;q<b.length;q++) {
     				b[q]=P1.T[q];
     			}
     			double A[][]=new double [P1.N][coverSets.K];
     			for(int q=0;q<P1.N;q++) {
     				for(int qq=0;qq<coverSets.K;qq++) {
     				A[q][qq]=coverSets.a[q][qq];
     				}
     			}
				 mb=new MasterProbSimplex(A,b, c);
				 mb.master(P1.N, P1.M, coverSets.K, A, coverSets.b,b,c,P1.betha);
				 int ag2=sc.nextInt();*/
				/*if(nbretimes>=30) {
					 for(int i=0;i<mb.t.length;i++) {
						 if(mb.t[i]<0.2) {
							  mb.t=removeTheElement(mb.t,i);
							 coverSets.a=deleteColumn(coverSets.a, i);
							 coverSets.b=deleteColumn(coverSets.b, i);
							 i--;
							 coverSets.K--;
						 }
					 }
					 nbretimes=0;*/
					/* System.out.println("\n****a****");	
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
		    			{	double s=0;
		    				for (int k = 0; k <coverSets.K; k++)
		    				{		s=s+(mb.t[k]*coverSets.b[i][k]);
		    						System.out.print(coverSets.b[i][k]+" ");	
		    					
		    				}
		    				System.out.println(": "+s/mb.lifetime);	
		    			
		    			}
		    			
		    			for(int k=0;k<coverSets.K;k++) {
		    				
		    				System.out.print(mb.t[k]+ " **");
		    				}*/
		    		
		    			
				// }
				 
				 
				 
				 
				 sphMCSC=new subProbMCSCBeta();
				 r=sphMCSC.coverMCSC(P1.N, P1.M, P1.delta,P1.TAalpha, edges,mb.pi,P1.link,mb.fi,P1.betha);
					
				 /*****************exist cover set *********************/
	    			
	    			boolean test=true;
	    			int t[]=new int [P1.N];
	    			for(int i=0;i<sphMCSC.cover.size();i++) {
	    				t[sphMCSC.cover.get(i)]=1;
	    			}
	    			
	    				for(int j=0;j<coverSets.K;j++) {
	    					for(int i=0;i<P1.N;i++) {	
	    						
	    						if(t[i]!=coverSets.a[i][j]) {
	    							test=false;
	    							break;
	    						}
	    				}
	    					if((test==false)&&(j<coverSets.K-1)) {
	    						test=true;
	    					}
	    					else {
	    						break;
	    					}
	    			}
	    			
	    				
	    			/**************************************/	
				 
				 if(test==false) {
				 coverSets.K=coverSets.K+1;

    			for(int i=0;i<sphMCSC.cover.size();i++)
    				coverSets.a[sphMCSC.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sphMCSC.coveredTarget.size();i++)
    				coverSets.b[sphMCSC.coveredTarget.get(i)][coverSets.K-1]=1;
				 }
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
    	}
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
			 elapsedTime = (new Date()).getTime() - startTime;
			 
		//}	 
			 
				pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
				pw.newLine();	
				pw.close();
				for (int i = 0; i <P1.M; i++)
			    {
				pw2.append(df.format(averageAmountTimeTarget[i])+ " ");
				
			    }
				pw2.append(df.format(bestLifetime));
				pw2.newLine();	
				pw2.close();
				 

		System.out.println("Talpha"+P1.TAalpha+"beta"+P1.betha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
		//int ag2=sc.nextInt();
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


