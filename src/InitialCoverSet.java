import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class InitialCoverSet {
	public int K=2;
	public int a[][];
	public int b[][];
	
	
	public List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
	public void calculeCoverSets(double Talpha,double betha,int n,int m,int [][]delta,int[][]link,float []E,double max_E) {
	
		try
		{	double dmin=0.2;
			IloCplex cplex1=new IloCplex();
			IloIntVar f[][][]=new IloIntVar[K][n] [n] ;
			for(int k=0; k<K; k++)
			{
				for(int i=0;i<n;i++)
				{
					for(int j=0; j<n;j++)
					{
					
					String varName="F"+(k)+""+(i)+""+(j);
					// examples of name of variables: z111, z112, ...
					f[k][i][j]=cplex1.intVar(0,Integer.MAX_VALUE ,varName);
					}
				}
			}
			
			IloNumVar t [] = new IloNumVar[K];
			IloNumVar x [][] = new IloNumVar[n][];
			IloNumVar v [][] = new IloNumVar[n][];
			IloNumVar w [][] = new IloNumVar[m][];
			
			
			
			//variable t1....tk
			for ( int i = 0; i < K; i++)
			{
				t[i] = cplex1.numVar(0, Double.MAX_VALUE);
			}
			for (int i = 0; i <n; i++) {
				 x[i] = cplex1.boolVarArray(K);
			       
			    } 
			
			for (int i = 0; i <n; i++) {
				 v[i] = cplex1.numVarArray(K, 0, Double.MAX_VALUE);
			       
			    }
			for (int i = 0; i <m; i++) {
				 w[i] = cplex1.numVarArray(K, 0, Double.MAX_VALUE);
			       
			    }

			
			IloLinearNumExpr objective1=cplex1.linearNumExpr();
			for(int i = 0; i < K; i++)
	    		objective1.addTerm(1.0, t[i]);
			 
			cplex1.addMaximize(objective1);
			/***********************(connetivityConstraint1)************************/
			for(int k=0;k<K;k++)
			{IloLinearNumExpr Assignement =cplex1.linearNumExpr();
			IloLinearNumExpr Assignement1 =cplex1.linearNumExpr();
			for(int j=0; j<n;j++)
			{
				if(link[0][j]==1) {
					Assignement.addTerm(1.0,f[k][0][j]);
				}
				Assignement1.addTerm(1.0,x[j][k]);
			}
			cplex1.addEq(Assignement,Assignement1);
			Assignement.clear();
			Assignement1.clear();
			}
			
			/***********************(connetivityConstraint2)************************/
			for(int k=0;k<K;k++)
			{
				for(int i=1; i<n;i++) 
				{IloLinearNumExpr Assignement =cplex1.linearNumExpr();
				for(int j=0; j<n;j++) 
				{
					
					if(link[i][j]==1) {
						Assignement.addTerm(1.0,f[k][i][j]);
					}
				
				}
					for(int j=0; j<n;j++) 
					{
						
						if(link[j][i]==1) {
							Assignement.addTerm(-1.0,f[k][j][i]);
						}
					
					}
					
					cplex1.addEq(Assignement, x[i][k]);
					Assignement.clear();
				}
			}
			/***********************(connetivityConstraint3)************************/
			for(int k=0;k<K;k++)
			{	
				
				for(int i=0; i<n;i++) 
				{IloLinearNumExpr Assignement =cplex1.linearNumExpr();
					for(int j=1; j<n;j++) 
					{
						
						if(link[j][i]==1) {
							Assignement.addTerm(1.0,f[k][j][i]);
						}
					
					}
					cplex1.addLe(Assignement, cplex1.prod((n)-1, x[i][k]));
					cplex1.addGe(Assignement, x[i][k]);
					Assignement.clear();
				}
				
			}
			/***********************(1)************************/
			for(int k = 0; k <K ; k++)
			{
				
				for(int j = 0; j <m; j++)
				{
					for(int i=1;i<n;i++)
					if(delta[i][j]!=0)
					{
						cplex1.addGe(w[j][k],v[i][k]);
						
						
					}
					
					
				}
			}
			
			/***********************(2)************************/
			for(int k = 0; k <K ; k++)
			{
				
				for(int j = 0; j <m; j++)
				{IloLinearNumExpr Assignement =cplex1.linearNumExpr();
					for(int i=1;i<n;i++)
					{if(delta[i][j]!=0)
					{
						
						Assignement.addTerm(1.0,v[i][k]);
					}
					
					}
					cplex1.addGe(Assignement, w[j][k]);
					Assignement.clear();
				}	
			}
					
			/***********************(3)************************/
			for(int i = 1; i <n ; i++)
			{
				IloLinearNumExpr Assignement =cplex1.linearNumExpr();
				for(int k = 0; k <K; k++)
				{
					Assignement.addTerm(1.0,v[i][k]);
				}	
					cplex1.addLe(Assignement, E[i]);
					Assignement.clear();
					
			}
			/***********************(4)************************/
			for(int i = 1; i <n; i++)
			{
			for(int k = 0; k <K ; k++)
			{
			
				IloLinearNumExpr Assignement2 =cplex1.linearNumExpr();
				
				Assignement2.setConstant(max_E);
				Assignement2.addTerm(-max_E,x[i][k] );
				Assignement2.addTerm(1.0,v[i][k]);
				cplex1.addGe(Assignement2,t[k]);
				Assignement2.clear();
			}
			
			}
			
			
			/***********************(5)*************************************/
			
			for(int j = 0; j <m; j++)
			{
			for(int k = 0; k <K ; k++)
			{
			
				cplex1.addLe(w[j][k],t[k]);
				
			}
			}
			
			/***************************(7)*********************************/
			for(int k = 0; k <K ; k++)
				{
					for(int i = 1; i <n; i++)
					{
							cplex1.addGe(v[i][k],cplex1.prod(dmin, x[i][k]));
						
					}

				}
			/***************************(8)*********************************/
				for(int k = 0; k <K ; k++)
				{
					for(int i = 1; i <n; i++)
					{
							cplex1.addLe(v[i][k],cplex1.prod(max_E, x[i][k]));
						
					}

				}
				
				
				/***********************(9:Contrainte Beta)************************/
				
				for(int j = 0; j <m ; j++)
				{
					IloLinearNumExpr Assignement =cplex1.linearNumExpr();
					
					IloLinearNumExpr Assignement1 =cplex1.linearNumExpr();
					
					for(int k = 0; k <K; k++)
					{
						Assignement.addTerm(1.0,w[j][k]);
						
					}	
					
					for(int k = 0; k <K; k++)
					{
						Assignement1.addTerm(1.0,t[k]);
						
					}	
					cplex1.addGe(Assignement,cplex1.prod(betha,Assignement1));

					Assignement1.clear();
					Assignement.clear();
				}
				
				/***********************(10)***********************/
				for(int k = 0; k <K ; k++)
				{
					IloLinearNumExpr Assignement =cplex1.linearNumExpr();
					
					for(int j = 0; j <m; j++)
					{
						Assignement.addTerm(1.0,w[j][k]);
						
					}	
					cplex1.addGe(Assignement,cplex1.prod(Talpha,t[k]));
					Assignement.clear();	
						
				}
				
		
			boolean solve=cplex1.solve();
			if((solve==true ))
			{	
				
				
				for(int i=0;i<K;i++)
					//System.out.print((float)cplex1.getValue(z[i])+":"+(float)cplex1.getValue(t[i])+"; ");

					System.out.print((float)cplex1.getValue(t[i])+"; ");
				System.out.println("");
				double lifetime=0;
				int optimizeK=0;
				int i;
				for( i=0;i<K;i++)
				{
					if((float)cplex1.getValue(t[i])>Math.pow(10, -5)) {
						lifetime= lifetime+(float)cplex1.getValue(t[i]);
						optimizeK=optimizeK+1;
		
					}
				}
				System.out.println(i+" ");	
				System.out.println("Kop= "+optimizeK+", lifetime= "+lifetime);
				a= new int[n][100000];
				b= new int[m][100000];
				
				
				for( i=0;i<n;i++)
				{	
					for (int k = 0; k <K; k++)
					{//System.out.println(k+" ");	
						if(cplex1.getValue(t[k])>0) {
							
							a[i][k]=(int) cplex1.getValue(x[i][k]);	
						}
					}
					
				}
				for( int j=0;j<m;j++)
				{	
					for (int k = 0; k <K; k++)
					{//System.out.println(k+" ");	
						if(cplex1.getValue(t[k])>0) {
							
							b[j][k]=(int) cplex1.getValue(w[j][k]);	
						}
					}
					
				}
			K=optimizeK;	
			}
			else {
				System.out.println("!!!!!!!!!!!!!!!!Erreur Model not solved with optimiezd K="+K);	
				
			}
			//int age=sc.nextInt();
			
		}
				
		catch(IloException exc)
		{
			
			exc.fillInStackTrace();
		}

	}
}
