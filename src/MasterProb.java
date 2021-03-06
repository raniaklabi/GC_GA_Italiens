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

public class MasterProb {
	double pi[];
	double lifetime;
	double t[];
	public void master(int N,int M,int K, int [][]a,int [][]b,float []E)
	{
		
		Scanner sc= new Scanner(System.in);
		
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
		//cplex1.setParam(IloCplex.Param.Simplex.Display,2);
		//cplex1.setParam(IloCplex.Param.Conflict.Display,2);
		//cplex1.setParam(IloCplex.Param.Network.Display,2);
		//cplex1.setParam(IloCplex.Param.Tune.Display,2);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Auto);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Dual); 
		//cplex1.setParam (IloCplex.IntParam.BarCrossAlg, IloCplex.Algorithm.Primal);
		//cplex1.setParam (IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Dual);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal); 
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Dual);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Auto);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);
		//cplex1.setParam (IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Network);
		//cplex1.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Barrier);
		//cplex1.setParam(IloCplex.IntParam.BarCrossAlg, IloCplex.Algorithm.Dual) ;
		//cplex1.setParam(IloCplex.IntParam. RootAlg, IloCplex.Algorithm.Dual);
		//cplex1. setParam (IloCplex.IntParam. NodeAlg, IloCplex.Algorithm.Auto);
		//cplex1. setParam (IloCplex.IntParam.RandomSeed, IloCplex.Algorithm.Dual);
		//cplex1.setParam(IloCplex.IntParam. RootAlg, IloCplex.Algorithm.Concurrent);
		
		boolean solve=cplex1.solve();
		if(solve==true )
		{	//cplex1.exportModel("lpex1.Lp");
		lifetime=0;
		//System.out.println("activation time of each cover set");
		for(int k=0;k<K;k++) {
			t[k]=cplex1.getValue(d[k]);
			//System.out.print(cplex1.getValue(d[k])+ " **");
			}
		//int ag2=sc.nextInt();	
		for(int k=0;k<K;k++)
			lifetime=lifetime+(float)cplex1.getValue(d[k]);
		System.out.println("LIFETIME="+lifetime);
		for(int i=0; i<constraintsSensors.size();i++)
		{
		if(cplex1.getDual(constraintsSensors.get(i)) / 100>=0)	
		{
		 pi[i+1]=cplex1.getDual(constraintsSensors.get(i));
		}
		else 
		{
			pi[i+1]=0;
		}	
		}
		
		/*for(int i=0; i<pi.length;i++)
			System.out.println("pi["+(i) +"]="+pi[i]);
		int ag2=sc.nextInt();*/
		//System.out.println("****a****");	
		/*System.out.println("");
		for (int i = 0; i <N; i++)
		{	double s=0;
	
			for (int k = 0; k <K; k++)
			{
					System.out.print(a[i][k]+" ");	
					
					s=s+cplex1.getValue(d[k])*a[i][k];
					
				
			}
			System.out.println(": "+s+", "+(E[i]-s)+", "+pi[i]);
			
		}
		int ag2=sc.nextInt();*/
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
