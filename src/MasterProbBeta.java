import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.concert.IloRange;

public class MasterProbBeta {
	double pi[];
	double fi[];
	double lifetime;
	double t[];
	public void master(int N,int M,int K, int [][]a,int [][]b,double []E,double betha)
	{
		
		Scanner sc= new Scanner(System.in);
		  fi= new double[M];
		  pi= new double[N];
		  t= new double[K];
	try
	{	
		IloCplex cplex1=new IloCplex();
		IloNumVar d [] = new IloNumVar[K];
		
		//variable t1....tk
		for ( int i = 0; i < K; i++)
		{
			d[i] = cplex1.numVar(0, Double.POSITIVE_INFINITY);
		}
		//expression   
		 //t1+....+tk
		IloLinearNumExpr objective1=cplex1.linearNumExpr();
		for(int i = 0; i < K; i++)
    		objective1.addTerm(1.0, d[i]);
		//define objective 
		cplex1.addMaximize(objective1);
		List<IloRange>constraintsSensors = new ArrayList<IloRange>();
		List<IloRange>constraintsTargets = new ArrayList<IloRange>();
		//define constrains  60x+60y>=300
		
		/***********************(1)************************/
		for(int i = 1; i <N ; i++)
		{
			IloLinearNumExpr Assignement =cplex1.linearNumExpr();
			for(int k = 0; k <K; k++)
			{
				Assignement.addTerm(a[i][k],d[k]);
			}	
				constraintsSensors.add(cplex1.addLe(Assignement,E[i]));
			
				Assignement.clear();
				
		}
		/***********************(9:Contrainte Beta)************************/
		for(int j = 0; j <M ; j++)
		 
		{	
				IloLinearNumExpr Assignement =cplex1.linearNumExpr();
				
				for(int k = 0; k <K; k++)
				{
					double segma=betha-b[j][k];
					Assignement.addTerm(segma,d[k]);
					
				}	
				constraintsTargets.add(cplex1.addLe(Assignement,0));

				Assignement.clear();
		}
		/***********************(9:Contrainte Beta)************************/
		
		/*for(int j = 0; j <M ; j++)
		{
			IloLinearNumExpr Assignement =cplex1.linearNumExpr();
			
			IloLinearNumExpr Assignement1 =cplex1.linearNumExpr();
			
			for(int k = 0; k <K; k++)
			{
				Assignement.addTerm(b[j][k], d[k]);
				
			}	
			
			for(int k = 0; k <K; k++)
			{
				Assignement1.addTerm(1.0,d[k]);
				
			}	
			
			
			//cplex1.addGe(Assignement,betha*K);
			constraintsTargets.add((IloRange) cplex1.addGe(Assignement,cplex1.prod(betha,Assignement1)));

			Assignement1.clear();
			Assignement.clear();
		}
		/******************************************************************/
		boolean solve=cplex1.solve();
		if(solve==true )
		{	
		lifetime=0;
		//System.out.println("activation time of each cover set");
		for(int k=0;k<K;k++) {
			t[k]=(float)cplex1.getValue(d[k]);
			//System.out.print(cplex1.getValue(d[k])+ " **");
			}
		//int ag2=sc.nextInt();	
		for(int k=0;k<K;k++)
			lifetime=lifetime+(float)cplex1.getValue(d[k]);
		//System.out.println("LIFETIME="+lifetime);
		for(int i=0; i<constraintsSensors.size();i++)
		{
		if(Math.floor(cplex1.getDual(constraintsSensors.get(i))* 100) / 100>=0)	
		{
		 pi[i+1]=Math.floor(cplex1.getDual(constraintsSensors.get(i))* 100) / 100;
		}
		else 
		{
			pi[i+1]=1;
		}	
		}
		for(int i=0; i<constraintsTargets.size();i++)
		{//fi[i]=cplex1.getDual(constraintsTargets.get(i));
		if(Math.floor(cplex1.getDual(constraintsTargets.get(i))* 100) / 100>=0)	
		{
		 fi[i]=Math.floor(cplex1.getDual(constraintsTargets.get(i))* 100) / 100;
		}
		else 
		{
			fi[i]=1;
		}	
		}
		
		/*for(int i=0; i<pi.length;i++)
			System.out.println("pi["+(i) +"]="+pi[i]);
		
		for(int i=0; i<fi.length;i++)
			System.out.println("fi["+(i) +"]="+fi[i]);
		 int ag2=sc.nextInt();	*/
		/*for(int i=0; i<N;i++)
			System.out.println("E["+(i) +"]="+E[i]);*/
		/*for(int i=0; i<pi.length;i++)
		{	double s=0;
		for(int k=0;k<K;k++)
		s=s+((float)cplex1.getValue(d[k])*a[i][k]);
		System.out.println("consumed energy is equal to : "+s);
			if(pi[i]<0) {
				s=0;
				for(int k=0;k<K;k++)
				s=s+((float)cplex1.getValue(d[k])*a[i][k]);
				System.out.println("consumed energy "+i +"is equal to : "+s);
		int ag2=sc.nextInt();	
		}
		}*/
		/*System.out.println("****a****");	
		for (int i = 0; i <N; i++)
		{	
			for (int k = 0; k <K; k++)
			{
					System.out.print(a[i][k]+" ");	
				
			}
			System.out.println("");	
		}
		for(int k=0;k<K;k++) {
			System.out.print(cplex1.getValue(d[k])+ " **");
			}*/
		/*System.out.println("\n****b****");	
		for (int i = 0; i <M; i++)
		{	double s=0;
			boolean val=false;
			for (int k = 0; k <K; k++)
			{		s=s+(b[i][k]*(float)cplex1.getValue(d[k]));
			if(s>=lifetime*betha) {
				val=true;
			}
					System.out.print(b[i][k]+" ");	
				
			}
			System.out.println(": "+s+","+val+","+(betha*lifetime));	
		}*/
		//int ag2=sc.nextInt();	
	}
		
		else {
			System.out.println("Not solved!!!!!!!!!!!!!!!!!!!!! ");
		}
		cplex1.clearModel();
		cplex1.end();
	}		
	catch(IloException exc)
	{
		
		exc.fillInStackTrace();
	}
}
}
