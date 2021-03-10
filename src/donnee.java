
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class donnee {
	static final int NC=500;
	static final int NT=200;
	int N; // Nombre N de capteurs
	int M; // Nombre M de zones a surveiller 
	int nbreDeTypeDeSur;
	int area=500;
	Scanner sc= new Scanner(System.in);
	int link[][]=new int [NC][NC];
	double cordSensors[][] = new double[NC][2];
	double cordTargets[][] = new double[NC][2];
	protected double alpha=1;
	protected double TAalpha;
	protected double betha=alpha;
	double Distance[][] = new double[NC][NT];
	int Voisin[][] = new int[NC][NC];
	double R[] =new double[NC];
	double Rc,Rs;
	double Xbs,Ybs;
	double energieAccumule[] =new double[NT];
	double P[] =new double[NC];
	double[] T = new double[NC]; // duree de vie de chaque capteur separee par un espace
	int delta[][] = new int[NC][NT];
	int connectDirectly[] =new int[NC];
	int nbreCS;
	public void Print_Data_NosInstance()
	{
		System.out.println("Nombre de capteurs N = " + N);
		System.out.println("Nombre de cibles M = " + M);
		System.out.print("Durée de vie de chaque capteur: ");
		for(int i =0; i < N; i++)
		{
			System.out.print("E"+(i+1)+"="+T[i] + " ");
	    }
					
		System.out.println("");
		System.out.println("Les cibles couverts par chaque capteur : ");
		
		System.out.println("Voisin : ");
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
				System.out.print(Voisin[i][j] +" ");
			System.out.println("");
		}
		System.out.println("");
		System.out.println("The sensors that connect directly with SB");
		for(int i=0;i<N;i++)
			System.out.print(connectDirectly[i] +" ");
		System.out.println("");
		System.out.println("Coordinaates of sensors : ");
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<2;j++)
				System.out.print(cordSensors[i][j] +" ");
			System.out.println("");
		}
		System.out.println("Delta ");
		 for(int i=0;i<N;i++)
		    {
		        for(int j=0;j<M;j++)
		        	System.out.print(delta[i][j] +" |");
				System.out.println("");
		    }
		  System.out.println("Link");
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(link[i][j]+ " ");
			}
			System.out.println("");
		
		}
		//int age=sc.nextInt();
		
	}
	public void link() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(i==j) {
					link[i][j]=0;
				}
				else {
					double s=Math.sqrt(Math.pow((cordSensors[j][0]-cordSensors[i][0]), 2)+Math.pow((cordSensors[j][1]-cordSensors[i][1]), 2));
					
					if(s<=Rc) {
						link[i][j]=1;
						
					}
				}
			}
		}
		
	}
	public void Print_Data_Francais()
	{
		System.out.println("Number of sensors N = " + N);
		System.out.println("Number of targets M = " + M);
		System.out.println("sensing range Rs = " + Rs);
		System.out.println("communication range Rc= " + Rc);
		System.out.println("Xbs = " + Xbs);
		System.out.println("Ybs= " + Ybs);
		for(int  i =0; i < N; i++)
		{ 
	  		
	  			for( int j =0; j < 2; j++)
	  				{
	  				
	  				System.out.print(cordSensors[i][j] + " ");
	  				}
	  		System.out.println("  "+T[i]);
		
		} 
		for(int  i =0; i < M; i++)
		{ 
	  		
	  			for( int j =0; j < 2; j++)
	  				{
	  				
	  				System.out.print(cordTargets[i][j] + " ");
	  				}
	  			System.out.println(" ");
		} 
		for( int i =0; i < N; i++)
		{
			for( int j =0; j < M; j++) {
				
				System.out.print(Distance[i][j]+" ");

			}
			System.out.println(" ");
		}
		System.out.println("Delta ");
		 for(int i=0;i<N;i++)
		    {
		        for(int j=0;j<M;j++)
		        	System.out.print(delta[i][j] +" |");
				System.out.println("");
		    }
		  System.out.println("Link");
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(link[i][j]+ " ");
			}
			System.out.println("");
		}  
		   TAalpha=Math.ceil(alpha*M);
		
	}
	public void Read_Data_Francais(String fichier1) 
	{
	   try {
		    InputStream ips=new FileInputStream(fichier1); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
	  	   	String[] parts;
	  	    ligne = br.readLine();
			ligne = br.readLine();
			parts = ligne.split(" ");
			  N = Integer.parseInt(parts[0]);
			  M = Integer.parseInt(parts[1]);
			  Rs = Integer.parseInt(parts[2]);
			  Rc = Integer.parseInt(parts[3]);
			  ligne = br.readLine();
			  parts = ligne.split(" ");
			  Xbs = Double.parseDouble(parts[0]);
			  Ybs=  Double.parseDouble(parts[1]);
				for( int i =0; i < N; i++)
				{ 
			  		ligne = br.readLine();
			  		ligne = ligne.trim().replaceAll(" +", " ");	
			  		parts = ligne.split(" ");
			  		cordSensors[i][0] = Double.parseDouble(parts[0]);
			  		cordSensors[i][1] = Double.parseDouble(parts[1]);
			  		T[i]=Double.parseDouble(parts[2]);
			  		
				}
				for( int i =0; i < M; i++)
				{ 
			  		ligne = br.readLine();
			  		ligne = ligne.trim().replaceAll(" +", " ");	
			  		parts = ligne.split(" ");
			  		cordTargets[i][0] = Double.parseDouble(parts[0]);
			  		cordTargets[i][1] = Double.parseDouble(parts[1]);
				}
				for( int i =0; i < N; i++)
				{
					for( int j =0; j < M; j++) {
						
						Distance[i][j]=Math.sqrt(Math.pow((cordTargets[j][0]-cordSensors[i][0]), 2)+Math.pow((cordTargets[j][1]-cordSensors[i][1]), 2));

					}
				}
	  	   	br.close();
	  	   	Calculer_delta();
	   }
	   catch (Exception e){
			System.out.println(e.toString());
		}	

	}
	public void Read_Data_neighbour(String fichier1)
	{
		String []survTarget=new String[NT];
	   int i,j,L;
	   try {
		  

		    InputStream ips=new FileInputStream(fichier1); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
	  	   	String[] parts;
	  	  ligne = br.readLine();
			ligne = br.readLine();
	        N = Integer.parseInt(ligne);
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
      
	  	   	M = Integer.parseInt(ligne);
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
  
	  	   	nbreDeTypeDeSur= Integer.parseInt(ligne);
	
	  	   	ligne = br.readLine();
	  	   	ligne = br.readLine();
	  	   	
	  	  	for( L = 0; L < N; L++)
	  	   	{
	  	   		ligne = br.readLine();
	  	   		parts= ligne.split(" ");
	  	   		R[L]= Double.parseDouble(parts[0]);
	  	   		T[L]= Double.parseDouble(parts[1]);
	  	   	
	  	   	// System.out.println("***********"+T[L]);
	  	   	
				 for(i = 6; i< parts.length;i++)
				 { Distance[L][i-6] = Double.parseDouble(parts[i]);
				 System.out.print(Distance[L][i-6]+ "|");
				 }
				 System.out.println("");
			
	      }
	  	  System.out.print("rayon");
	  	  for( L = 0; L < N; L++)
	  	  {System.out.print(R[L] + "| ");
	  	  }
			//System.out.print(T[i] + " ");
		      ligne = br.readLine();
		  	  ligne = br.readLine();
		  	 parts = ligne.split(" ");
		  	for( i =0; i < M; i++)
			{
		  		 survTarget[i] = parts[i];
				System.out.print(survTarget[i] + " ");
			
			} 
		 	
	  	   	
	  	   	ligne = br.readLine();
			
	  	  	for( i = 0; i < N; i++)
	  	   	{
	  	   		ligne = br.readLine();
	  	   		parts= ligne.split(" ");
				 for(j = 0; j< parts.length;j++)
				 { Voisin[i][j] = Integer.parseInt(parts[j]);
				 System.out.print(Voisin[i][j]+ "|");
				 }
				 System.out.println("");
			
	      }

	  	   	ligne = br.readLine();
	  	  ligne = br.readLine();
	  	  parts = ligne.split(" ");
		  	for( i =0; i < N; i++)
			{
		  		connectDirectly[i] = Integer.parseInt(parts[i]);
				System.out.print(connectDirectly[i] + " ");
			
			} 
		  	ligne = br.readLine();
		  	 
			  	for( i =0; i < N; i++)
				{ 
			  		ligne = br.readLine();
			  		parts = ligne.split(" ");
			  			for( j =0; j < 2; j++)
			  				{
			  				cordSensors[i][j] = Double.parseDouble(parts[j+1]);
			  				System.out.print(cordSensors[i][j] + " ");
			  				}
			  		System.out.println(" ");
				
				} 


	      br.close();
	      Rc=Rs=R[0];
	      Calculer_delta();
	     
	   }
	   catch (Exception e){
			System.out.println(e.toString());
		}	

	}
		public void Read_Data_v1(String fichier)
		{
		    // Role:
		    //cette fonction permet de lire toutes  les donnees necessaires pour la simulation a partir d'un fichier S
		   String []survTarget=new String[NT];
		   int i,j,k,L;
		   
		   String line; /* or other suitable maximum line size */
		   String S;
		   
		   try {
			  

			    InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				ligne = br.readLine();
				ligne = br.readLine();
		        N = Integer.parseInt(ligne);
		  	   	ligne = br.readLine();
		  	   	ligne = br.readLine();
	      
		  	   	M = Integer.parseInt(ligne);
		  	   	ligne = br.readLine();
		  	   	ligne = br.readLine();
	  
		  	   	nbreDeTypeDeSur= Integer.parseInt(ligne);
		
		  	   	ligne = br.readLine();
		  	   	ligne = br.readLine();
		  	   	String[] parts;
		  	  	for( L = 0; L < N; L++)
		  	   	{
		  	   		ligne = br.readLine();
		  	   		parts= ligne.split(" ");
		  	   		R[L]= Double.parseDouble(parts[0]);
		  	   		T[L]= new Float( Double.parseDouble(parts[1]));
		  	   	
		  	   	// System.out.println("***********"+T[L]);
		  	   	
					 for(i = 6; i< parts.length;i++)
					 { Distance[L][i-6] = Double.parseDouble(parts[i]);
					 //System.out.print(Distance[L][i-6]+ "|");
					 }
					// System.out.println("");
				
		      }
		  	 // System.out.print("rayon");
		  	 /* for( L = 0; L < N; L++)
		  	  {System.out.print(R[L] + "| ");
		  	  }*/
				//System.out.print(T[i] + " ");
			      ligne = br.readLine();
			  	  ligne = br.readLine();
			  	 parts = ligne.split(" ");
			  	for( i =0; i < M; i++)
				{
			  		 survTarget[i] = parts[i];
					//System.out.print(survTarget[i] + " ");
				
				} 
			  	ligne = br.readLine();
				
		  	  	for( i = 0; i < N; i++)
		  	   	{
		  	   		ligne = br.readLine();
		  	   		parts= ligne.split(" ");
					 for(j = 0; j< parts.length;j++)
					 { Voisin[i][j] = Integer.parseInt(parts[j]);
					// System.out.print(Voisin[i][j]+ "|");
					 }
					// System.out.println("");
				
		      }
		  	  	ligne = br.readLine();
			  	  ligne = br.readLine();
			  	  parts = ligne.split(" ");
				  	for( i =0; i < N; i++)
					{
				  		connectDirectly[i] = Integer.parseInt(parts[i]);
						//System.out.print(connectDirectly[i] + " ");
					
					} 
		      br.close();
		      Calculer_delta();
		   }
		   catch (Exception e){
				System.out.println(e.toString());
			}	

		}
		
	public void Calculer_delta()
	{
	    //Role:
	    //A partir de les diffrentes distances d'un capteur particulier on peut construire une ligne de delta.
	    //Retourne la matrice "delta" .
	    //Les lignes de la matrice presente les capteurs.
	    //les colonnes de la matrice presente les targets.
	    //losqu'on met par exemple 1 dans la case(1,1)  c'est_a_dire le capteur un couvre la target 1 .

	    int i,j;
	   /* System.out.println("Distance: ");
	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        	System.out.print(Distance[i][j] +" |");
			System.out.println("");
	    }*/
	   /* System.out.println("Rayon: ");
	    for(i=0;i<N;i++)
	    {
	 
	        	System.out.print(R[i] +"| ");
			
	    }*/

	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        {

	            delta[i][j]=0;
	        }

	    }

	    for(i=0;i<N;i++)
	    {
	        for(j=0;j<M;j++)
	        {
	            if(Distance[i][j]<=Rs)
	            {
	                delta[i][j]=1;
	            }
	            else
	            {
	                delta[i][j]=0;
	            }

	        }

	    }
	
	}
	public double max_energie()
	{
		double max=-1;
		//l'energie de tous les capteur est egal à 1
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i=0;i<N;i++)
		{
			//Random rn = new Random();
			//T[i]=rn.nextInt(10) + 1;
			//System.out.println("E"+(i+1)+ "=" + T[i]);
			//T[i]=1;
		}
		
		for(int i=0;i<N;i++)
		{
			if (T[i]>=max) {
				max=T[i];
			}
		}
		return max;
	}
	 
		public void Print_Data_NosInstance_Groupe1()
		{
			System.out.println("Nombre de capteurs N = " + N);
			System.out.println("Nombre de cibles M = " + M);
			System.out.print("Durée de vie de chaque capteur: ");
			for(int i =0; i < N; i++)
			{
				System.out.print("E"+(i+1)+"="+T[i] + " ");
		    }
			System.out.println("");
		
			System.out.println("\n Deeeelta: ");
		    for(int i=0;i<N;i++)
		    {
		        for(int j=0;j<M;j++)
		        	System.out.print(delta[i][j] +" ");
				System.out.println("");
		    }
			//int age=sc.nextInt();
			System.out.println("\n Neigboor: ");
		    for(int i=0;i<N;i++)
		    {
		        for(int j=0;j<N;j++)
		        	System.out.print(Voisin[i][j] +" ");
				System.out.println("");
		    }
		    System.out.println("\n sensors directely connected by base station: ");
		    for( int i =0; i < N; i++)
			{
		  		
				System.out.print(connectDirectly[i] + " ");
			
			} 
			
		}
	
}




