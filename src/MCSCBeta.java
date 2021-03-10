import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Repeatable;
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
		/*File fichier = new File("C:\\Users\\rania\\Desktop\\Th�se\\Projects\\GC_GA_Italiens\\resultMCSCBeta.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;*/

		//P1.Read_Data_Francais("CMLP_01.dat");
		P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);
		P1.link();
		P1.Print_Data_Francais();
		//System.out.println(P1.alpha+" "+P1.betha+" "+P1.TAalpha);
		//int ag2=sc.nextInt();
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
    	
    	Graph graph=null;
    	
    	bestLifetime=0;
    	MasterProbBeta mb;
    	subProbMCSCBeta sphMCSC;
    	//InitialCoverSetsHeuristic coverSets=new InitialCoverSetsHeuristic();
    	//coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edges,P1.link);
    	
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

    	
    	int occ=0;
			 while( r>0) {
				 elapsedTime = (new Date()).getTime() - startTime;
     			if(elapsedTime>=time_limit)
     			{
     			break;
     			}
    	
				 mb=new MasterProbBeta();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);
				 sphMCSC=new subProbMCSCBeta();
				 r=sphMCSC.coverMCSC(P1.N, P1.M, P1.delta,P1.TAalpha, edges,mb.pi,P1.link,mb.fi,P1.betha);
	    			//System.out.println("++++++++++++++++++++++++++++++++++++++++");	

	    			coverSets.K=coverSets.K+1;
    			//System.out.println("new cover set: "+ sphMCSC.cover);
    			//System.out.println("coveredTarget: "+ sphMCSC.coveredTarget);
    			//System.out.println("****reduced cost= "+ r);
    			// System.out.println("\nheuristic");
				// ag2=sc.nextInt();
    			//System.out.println("K: "+ coverSets.K);
    			
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
			 
			 try {
				 File file = new File("C:\\Users\\rania\\Desktop\\Th�se\\Projects\\GC_GA_Italiens\\resultMCSCBeta.dat");
				 if (!file.exists()) {
				        file.createNewFile();
				    }
				    FileWriter fw = new FileWriter(file, true);
				    BufferedWriter pw = new BufferedWriter(fw);
			pw.write("file"+args[0]+"Talpha"+P1.TAalpha+"beta"+P1.betha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
			pw.newLine();	
			pw.close();
				 }
		    catch (IOException e) { e.printStackTrace(); }
				 

		System.out.println("Talpha"+P1.TAalpha+"beta"+P1.betha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
		//int ag2=sc.nextInt();
	}
	

}
