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
import java.util.ArrayList;
public class MCSC {

	public static void main(String[] args) throws IOException {
		Scanner sc= new Scanner(System.in);
		donnee  P1 = new donnee();
		/*P1.Read_Data_neighbour("Exp20_15_1_Neighbour.txt");
		P1.link();
		P1.Print_Data_NosInstance();*/
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSC.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);
		//P1.Read_Data_Francais("CMLP_31.dat");
		P1.link();
		P1.Print_Data_Francais();
	
		//for(int lll=0;lll<5;lll++) {
			double r=1;
			double bestLifetime;
			double [][]SPL=new double[100][100];
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
    	InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	Graph graph=null;
    	coverSets.calculeCoverSets(TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
    	System.out.println("\nThe population contains: ");
    	for(int i=0;i<coverSets.pop.sizep;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<coverSets.pop.p.get(i).size;jj++) 
    		{
    			if(coverSets.pop.p.get(i).C[jj]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println(": "+coverSets.pop.p.get(i).fitness);
    	}
    	//int ag2=sc.nextInt();

    	bestLifetime=0;
    	MasterProb mb;
    	subProbMCSCcorrige sphMCSC;
    	int occ=0;
			 while( r>0) {
    	
    	
				 mb=new MasterProb();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T);
					/*System.out.println("****a****");	
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
	    			System.out.println("++++++++++++++++++++++++++++++++++++++++");	
	    			//int ag2=sc.nextInt();
	    			sphMCSC=new subProbMCSCcorrige();
	    			r=sphMCSC.coverMCSC(P1.N, P1.M, P1.delta,TAalpha, edges,mb.pi,P1.link);
	    			System.out.println("++++++++++++++++++++++++++++++++++++++++");	

	    			coverSets.K=coverSets.K+1;
    			System.out.println("new cover set: "+ sphMCSC.cover);
    			System.out.println("coveredTarget: "+ sphMCSC.coveredTarget);
    			System.out.println("****reduced cost= "+ r);
    			 System.out.println("\nheuristic");
				// ag2=sc.nextInt();
    			System.out.println("K: "+ coverSets.K);
    			
    			for(int i=0;i<sphMCSC.cover.size();i++)
    				coverSets.a[sphMCSC.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sphMCSC.coveredTarget.size();i++)
    				coverSets.b[sphMCSC.coveredTarget.get(i)][coverSets.K-1]=1;
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
			 elapsedTime = (new Date()).getTime() - startTime;
			 
		//}	 
		
		pw.append("file"+args[0]+"Talpha"+TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
		pw.newLine();	
		pw.close();

		System.out.println("Talpha"+TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
	}
	

}
