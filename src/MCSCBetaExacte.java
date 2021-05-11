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
		File fichier = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\resultMCSCExateBeta.dat");
		BufferedWriter pw = new BufferedWriter(new FileWriter(fichier,true)) ;
		File fichier2 = new File("C:\\Users\\rania\\Desktop\\Thèse\\Projects\\GC_GA_Italiens\\averageAmountTimeTargetMCSCExacteBeta.dat");
		BufferedWriter pw2 = new BufferedWriter(new FileWriter(fichier2,true)) ;
		DecimalFormat df = new DecimalFormat("0.00");
		/*P1.Read_Data_Francais(args[0]);
		double TAalpha=Double.parseDouble(args[1]);*/
		/*P1.Read_Data_Francais("CMLP_04.dat");
		P1.alpha=P1.betha=0.7;*/
		P1.Read_Data_Francais(args[0]);
		P1.alpha=P1.betha=Double.parseDouble(args[1]);
		P1.link();
		P1.Print_Data_Francais();
		double averageAmountTimeTarget[] =new double[P1.M];
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
    	MasterProbBeta mb=null;
    	subProbMCSCBeta sph;
    
    	coverSets.calculeCoverSets(P1.TAalpha,P1.betha, P1.N, P1.M,P1.delta,P1.link,P1.T,P1.max_energie());
    	int nbExacte=0;
    	int nb=0;
    	 int occ=0;
    	
			 while((r>0)) {
				
					elapsedTime = (new Date()).getTime() - startTime;
        			if(elapsedTime>=time_limit)
        			{
        			break;
        			}
        		mb=new MasterProbBeta();
				 mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);
				 sph=new subProbMCSCBeta();
	    			r=sph.coverMCSC(P1.N, P1.M, P1.delta,P1.TAalpha, edges,mb.pi,P1.link,mb.fi,P1.betha);
	    			/*****************exist cover set *********************/
	    			
	    			boolean test=true;
	    			int t[]=new int [P1.N];
	    			for(int i=0;i<sph.cover.size();i++) {
	    				t[sph.cover.get(i)]=1;
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
	    				
	    			if((r>0)&&(occ<10)) {
	    				if(test==false) {
				System.out.println("\nheuristic");
				System.out.println("\n"+occ);
			 //int ag2=sc.nextInt();
    			coverSets.K=coverSets.K+1;
    			for(int i=0;i<sph.cover.size();i++)
    				coverSets.a[sph.cover.get(i)][coverSets.K-1]=1;
    			for(int i=0;i<sph.coveredTarget.size();i++)
    				coverSets.b[sph.coveredTarget.get(i)][coverSets.K-1]=1;
	    				}
					 
					 if(bestLifetime-(float)mb.lifetime==0) {
        			occ++;	
        			}
        			else {
        			bestLifetime=(float)mb.lifetime;
        			occ=0;}
        			/*if(occ>10){
        				break;
        			}*/
    			//bestLifetime=mb.lifetime;
    			
    			
				 
			 }
				 else{
						elapsedTime = (new Date()).getTime() - startTime;
	        			if(elapsedTime>=time_limit)
	        			{
	        			break;
	        			}
	        			nb++;
					 System.out.println("\nexacte");
					// System.out.println("rep= "+ nbExacte);
					//int ag2=sc.nextInt();
					double copyr=r;
	        			subProbModelExactBeta spme= new subProbModelExactBeta();
	        			r=spme.subProbExacteBeta(mb.pi,mb.fi, P1.N, P1.M, P1.link, P1.delta, P1.TAalpha,P1.betha);
	        			/*****************exist cover set *********************/
		    			
		    			 test=true;
		    			 t=new int [P1.N];
		    			for(int i=0;i<spme.cover.size();i++) {
		    				t[spme.cover.get(i)]=1;
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

	        			if((r>0))  {
	        				elapsedTime = (new Date()).getTime() - startTime;
		        			if(elapsedTime>=time_limit)
		        			{
		        			break;
		        			}
		        			if(test==false) {
	        				coverSets.K=coverSets.K+1;
	        				for(int i=0;i<spme.cover.size();i++)
	            				coverSets.a[spme.cover.get(i)][coverSets.K-1]=1;
	            			for(int i=0;i<spme.coveredTarget.size();i++)
	            				coverSets.b[spme.coveredTarget.get(i)][coverSets.K-1]=1;
	            			mb.master(P1.N, P1.M, coverSets.K, coverSets.a, coverSets.b, P1.T,P1.betha);

		        			}
	       				/* System.out.println("**, "+mb.lifetime);
	       				System.out.println("**, "+bestLifetime);
        				 ag2=sc.nextInt();*/
	            		
	       				occ=0;
	            		
	        				 if((mb.lifetime-bestLifetime>0.0001) &&(copyr!=r)){
	        					 //System.out.println("** okkkkkkkk");
	        					
	        					 bestLifetime=mb.lifetime;
		                			}
		                			else {
		                		nbExacte++;
		                			}
		                			if(nbExacte>=20) {
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
    			
				 System.out.println("reduced cost= "+ r);
 		    	System.out.println("Optimum with lifetime="+bestLifetime);

 		   	elapsedTime = (new Date()).getTime() - startTime;
			if(elapsedTime>=time_limit)
			{
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
			 System.out.println("reduced cost= "+ r);
			 System.out.println("rep= "+ nbExacte);
			//int ag2=sc.nextInt();
				elapsedTime = (new Date()).getTime() - startTime;
				
				pw.append("file"+args[0]+"Talpha"+P1.TAalpha+" L="+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K+"appelExacte"+nb);
				pw.newLine();	
				pw.close();
				for (int i = 0; i <P1.M; i++)
			    {
				pw2.append(df.format(averageAmountTimeTarget[i])+ " ");
				
			    }
				pw2.append(df.format(bestLifetime));
				pw2.newLine();	
				pw2.close();
				System.out.println("End!!!!!!!! with best lifetime"+bestLifetime+"execution time: "+elapsedTime/1000+" seconde"+"number CS="+coverSets.K+"nbreExacteAppel: "+nb);
				    	
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

