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
import java.util.ArrayList;
public class MCSC {

	public static void main(String[] args) throws IOException {
		Scanner sc= new Scanner(System.in);
		donnee  P1 = new donnee();
		double time_limit=3600000;
		/*P1.Read_Data_neighbour("Exp20_15_1_Neighbour.txt");
	    P1.alpha=P1.betha=1;
		P1.link();
		P1.Print_Data_NosInstance();*/
		
		/*P1.Read_Data_v1("Exp10_15_1.txt");
		P1.alpha=P1.betha=1;
		P1.link();
		P1.Print_Data_NosInstance();*/
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSC.dat");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		File fichier2 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\averageAmountTimeTargetMCSC.dat");
		BufferedWriter pw2 = new BufferedWriter(new FileWriter(fichier2,true)) ;
		DecimalFormat df = new DecimalFormat("0.00");
		/*P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);*/
		P1.Read_Data_Francais("CMLP_01.dat");
		P1.alpha=P1.betha=1;
		P1.link();
		P1.Print_Data_Francais();

		double averageAmountTimeTarget[] =new double[P1.M];
		//int ag2=sc.nextInt();
		//for(int lll=0;lll<5;lll++) {
			double r=1;
			double bestLifetime;
			double [][]SPL=new double[100][100];
			long startTime = System.currentTimeMillis();
			long elapsedTime = 0L;
		
		
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
    	coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,P1.link);
    	coverSets.pop.calculeFitnessPopulation(initialPI);
    	/*System.out.println("\nThe population contains: ");
    	for(int i=0;i<coverSets.pop.sizep;i++) {
    		System.out.println("Ind: "+(i+1));
    		for(int jj=0;jj<coverSets.pop.p.get(i).size;jj++) 
    		{
    			if(coverSets.pop.p.get(i).C[jj]==1) {
    				System.out.print(jj+" ");
    			}
    				
    		}
    		System.out.println(": "+coverSets.pop.p.get(i).fitness);
    	}*/
    	

    	bestLifetime=0;
    	MasterProb mb=null;
    	subProbMCSCcorrige sphMCSC=null;
    	int occ=0;
			 while( r>0) {
				 elapsedTime = (new Date()).getTime() - startTime;
     			if(elapsedTime>=time_limit)
     			{
     			break;
     			}
    	
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
	    			//System.out.println("++++++++++++++++++++++++++++++++++++++++");	
	    			//ag2=sc.nextInt();
	    			sphMCSC=new subProbMCSCcorrige();
	    			r=sphMCSC.coverMCSC(P1.N, P1.M, P1.delta,P1.TAalpha, edges,mb.pi,P1.link);
	    			//System.out.println("++++++++++++++++++++++++++++++++++++++++");	
	    			//System.out.println("\nreduced cost= "+r+" \nlifetime="+mb.lifetime);
	    			//ag2=sc.nextInt();
	    		/*****************exist cover set *********************/
	    			
	    			boolean test=true;
	    			int t[]=new int [P1.N];
	    			for(int i=0;i<sphMCSC.cover.size();i++) {
	    				t[sphMCSC.cover.get(i)]=1;
	    			}
	    			/*System.out.println(sphMCSC.cover);
	    			for(int i=0;i<P1.N;i++) {
	    				System.out.print(t[i]+ "|");
	    			}*/
	    			
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
	    			else {
	    				System.out.println("already exist this cover set");
	    				//ag2=sc.nextInt();
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
			
		
		/*pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
		pw.newLine();	
		pw.close();
		for (int i = 0; i <P1.M; i++)
	    {
		pw2.append(df.format(averageAmountTimeTarget[i])+ " ");
		
	    }
		pw2.append(df.format(bestLifetime));
		pw2.newLine();	
		pw2.close();*/
		System.out.println("Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
	}
	
	public boolean exit(ArrayList<Integer> cover, int [][]A,int n,int K) {
		boolean test=true;
		int t[]=new int [n];
		for(int i=0;i<cover.size();i++) {
			t[cover.get(i)]=1;
		}

			for(int j=0;j<K;j++) {
				for(int i=0;i<n;i++) {	
					
					if(t[i]!=A[i][j]) {
						test=false;
						break;
					}
			}
		}
		return test;
	}
}
