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

public class MCSCBetaExacte {

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
		/*File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSCExateBeta.txt");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;*/
		/*P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);*/
		//P1.Read_Data_Francais("CMLP_01.dat");
		P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);
		P1.link();
		P1.Print_Data_Francais();
		Scanner sc= new Scanner(System.in);
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
    	InitialCoverSet coverSets=new InitialCoverSet();
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
    	bestLifetime=0;
    	MasterProbBeta mb;
    	subProbMCSCBeta sph;
    	
    	coverSets.calculeCoverSets(P1.TAalpha,P1.betha, P1.N, P1.M,P1.delta,P1.link,P1.T,P1.max_energie());

    	//coverSets.calculeCoverSets(P1.TAalpha, P1.N, P1.M, graph, SPL, initialPI, P1.delta, edges,edgesUndirected,P1.link);
    	//coverSets.pop.calculeFitnessPopulation(initialPI);
    	// int ag2=sc.nextInt();
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
    	}*/
    	//int ag2=sc.nextInt();
    	
    	/*coverSets.K=coverSets.K+1;
    	coverSets.a[2][coverSets.K-1]=1;
    	coverSets.a[0][coverSets.K-1]=1;
    	for(int i=0;i<P1.M;i++)
    		{ 
			coverSets.b[i][coverSets.K-1]=1;}*/
    	int nbExacte=0;
    	
    	 int occ=0;
    	 mb=new MasterProbBeta();
			 while((r>0)) {
				
					elapsedTime = (new Date()).getTime() - startTime;
        			if(elapsedTime>=time_limit)
        			{
        			break;
        			}
				
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);
				 sph=new subProbMCSCBeta();
	    			r=sph.coverMCSC(P1.N, P1.M, P1.delta,P1.TAalpha, edges,mb.pi,P1.link,mb.fi,P1.betha);
				 if((r>0)&&(occ<20)) {
				System.out.println("\nheuristic");
				System.out.println("\n"+occ);
			 //int ag2=sc.nextInt();
    			coverSets.K=coverSets.K+1;
    			for(int i=0;i<sph.cover.size();i++)
    				coverSets.a[sph.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sph.coveredTarget.size();i++)
    				coverSets.b[sph.coveredTarget.get(i)][coverSets.K-1]=1;
					/* for(int i=0;i<sph.chroms.size();i++) {
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
    					
    				}*/
					 
					 if(bestLifetime-(float)mb.lifetime==0) {
        			occ++;	
        			}
        			else {
        			bestLifetime=(float)mb.lifetime;
        			occ=0;}
        			/*if(occ>50){
        				break;
        			}*/
    			//bestLifetime=mb.lifetime;
    			
    			
				 }
				 else{
					 nbExacte++;
						elapsedTime = (new Date()).getTime() - startTime;
	        			if(elapsedTime>=time_limit)
	        			{
	        			break;
	        			}
					 //System.out.println("\nexacte");
					//ag2=sc.nextInt();
	        			subProbModelExactBeta spme= new subProbModelExactBeta();
	        			r=spme.subProbExacteBeta(mb.pi,mb.fi, P1.N, P1.M, P1.link, P1.delta, P1.TAalpha,P1.betha);
	        			if((r>0))  {
	        				coverSets.K=coverSets.K+1;
	        				for(int i=0;i<spme.cover.size();i++)
	            				coverSets.a[spme.cover.get(i)][coverSets.K-1]=1;
	            			for(int i=0;i<spme.coveredTarget.size();i++)
	            				coverSets.b[spme.coveredTarget.get(i)][coverSets.K-1]=1;
	       				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);
	       				/* System.out.println("**, "+mb.lifetime);
	       				System.out.println("**, "+bestLifetime);
        				 ag2=sc.nextInt();*/
	            		
	            			
	            		
	        				 if(mb.lifetime-bestLifetime>0.0001) {
	        					 occ=0;
	        					 bestLifetime=mb.lifetime;
		                			}
		                			else {
		                			occ++;
		                			}
		                			if(occ>=50) {
		                				break;
		                			}
	        				/* System.out.println("**, "+occ);
	        				 ag2=sc.nextInt();*/
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
				
				 try {
					 File file = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSCBetaExacte.dat");
					 if (!file.exists()) {
					        file.createNewFile();
					    }
					    FileWriter fw = new FileWriter(file, true);
					    BufferedWriter pw = new BufferedWriter(fw);
				pw.write("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K+"nbreExacteAppel: "+nbExacte);
				pw.newLine();	
				pw.close();
					 }
			    catch (IOException e) { e.printStackTrace(); }
				     	System.out.println("End!!!!!!!! with best lifetime"+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K);
				     	System.out.println(P1.betha);
				     	int ag2=sc.nextInt();
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

