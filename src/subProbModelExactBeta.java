import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class subProbModelExactBeta {
	double r;
	List<Integer> cover;
	List<Integer> coveredTarget;
	Scanner sc= new Scanner(System.in);
	public subProbModelExactBeta() {
		r=0;
		cover= new ArrayList<Integer>();
		coveredTarget= new ArrayList<Integer>();
	}
	public double subProbExacteBeta(double []pi,double []fi,int n,int m,int [][]link, int [][]delta,double tAlpha,double beta) {
		try
		{	/*	System.out.println("\n Deeeelta: ");
		    for(int i=0;i<n;i++)
		    {
		        for(int j=0;j<m;j++)
		        	System.out.print(delta[i][j] +" ");
				System.out.println("");
		    }*/
			double time_limit=3600000;
			IloCplex cplex=new IloCplex();
			IloNumVar f[][]=new IloNumVar[n][n] ;
			
				for(int i=0;i<n;i++)
				{
					for(int j=0; j<n;j++)
					{
					f[i][j]=cplex.numVar(0, Integer.MAX_VALUE);
					}
				}
			
				IloNumVar y [] = new IloNumVar[n];
				for ( int i = 0; i < n; i++)
				{
					y[i] = cplex.boolVar();
				}
				IloNumVar z [] = new IloNumVar[m];
				for ( int i = 0; i < m; i++)
				{
					z[i] = cplex.boolVar();
				}
			
				IloLinearNumExpr objective=cplex.linearNumExpr();
				for(int i = 0; i < n; i++)
					objective.addTerm(pi[i], y[i]);
				for(int i = 0; i < m; i++)
					objective.addTerm(-fi[i], z[i]);
				
				cplex.addMinimize(objective);
				/***************************dmin*************/
				/*for(int j = 0; j <m; j++) {
					cplex.addGe(w[j],cplex.prod(0.2, z[j]));
				}
				/***********************(Importconstraint)************************/

				/*cplex.addEq(y[0],1);
				/***************************************************/
				/***********************(connetivityConstraint1)************************/
				IloLinearNumExpr Assignement =cplex.linearNumExpr();
				IloLinearNumExpr Assignement1 =cplex.linearNumExpr();
				for(int j=0; j<n;j++)
				{
					if(link[0][j]==1) {
						Assignement.addTerm(1.0,f[0][j]);
					}
					Assignement1.addTerm(1.0,y[j]);
				}
				cplex.addEq(Assignement,Assignement1);
				Assignement.clear();
				Assignement1.clear();
				
			
				/***********************(connetivityConstraint2)************************/
				
				for(int i=1; i<n;i++) 
				{IloLinearNumExpr Assignement3 =cplex.linearNumExpr();
				for(int j=0; j<n;j++) 
				{
					
					if(link[i][j]==1) {
						Assignement3.addTerm(1.0,f[i][j]);
					}
				
				}
					for(int j=0; j<n;j++) 
					{
						
						if(link[j][i]==1) {
							Assignement3.addTerm(-1.0,f[j][i]);
						}
					
					}
					
					cplex.addEq(Assignement3, y[i]);
					Assignement3.clear();
				}
				
				/***********************(connetivityConstraint3)************************/
				
				for(int i=0; i<n;i++) 
				{IloLinearNumExpr Assignement4 =cplex.linearNumExpr();
					for(int j=1; j<n;j++) 
					{
						
						if(link[j][i]==1) {
							Assignement4.addTerm(1.0,f[j][i]);
						}
					
					}
					cplex.addLe(Assignement4, cplex.prod((n)-1, y[i]));
					cplex.addGe(Assignement4, y[i]);
					Assignement4.clear();
				}
					/****************(constraintTargets)**********************************/
					for(int j = 0; j <m; j++)
					{IloLinearNumExpr Assignement4 =cplex.linearNumExpr();
						for(int i=1;i<n;i++)
						if(delta[i][j]!=0)
						{
							Assignement4.addTerm(delta[i][j], y[i]);
							
							
						}
						
						cplex.addGe(Assignement4,z[j]);
						Assignement4.clear();
					}
					
					/**********************(constrainttAlpha)******************/
					IloLinearNumExpr Assignement5 =cplex.linearNumExpr();
					for(int j = 0; j <m; j++)
					{
							Assignement5.addTerm(1, z[j]);
					}		
					cplex.addGe(Assignement5,tAlpha);
					Assignement5.clear();
					/*******************************************************/
					cplex.setParam(IloCplex.IntParam.TimeLimit, time_limit); 
					//cplex.setParam(IloCplex.Param.MIP.Limits.Solutions, 1);
					boolean solve=cplex.solve();
					
					if((solve==true ))
						
						{	double s=0;
						for(int i=0;i<n;i++) {
							if(cplex.getValue(y[i])>=0.99) {
							cover.add(i);
							}
						}
						
							for(int i=1;i<n;i++) {
								//System.out.print((int)cplex.getValue(y[i])+"**** ");
								if(cplex.getValue(y[i])>=0.99) {
									r=r+pi[i];
									ArrayList<Integer> Ti= new ArrayList<Integer>();
									for(int j=0;j<m;j++) {
										if(delta[i][j]==1) {
											Ti.add(j);
										}
									}
									coveredTarget=union2sets(coveredTarget, Ti);
								}
							}
							
							
							/*for(int i=0;i<m;i++) {
								if((int)cplex.getValue(z[i])==1) {
									r=r-fi[i];
									s=s+(beta*fi[i]);
								}
							}*/
							for(int i=0;i<coveredTarget.size();i++) {
								r=r-fi[coveredTarget.get(i)];
								s=s+(beta*fi[coveredTarget.get(i)]);
							}
							//r=s-r;
							r=1-s-r;
						//	r=1-s-cplex.getObjValue();
							System.out.println("\nreduced cost= "+r);
							//int ag2=sc.nextInt();
						}
		}
		catch(IloException exc)
		{
			
			exc.fillInStackTrace();
		}
		return r;
	}
	public List<Integer> union2sets(List<Integer> H1,List<Integer> H2)
	{


		 List<Integer> union= Stream.concat(H2.stream(), H1.stream())
			        .distinct().sorted()
			        .collect(Collectors.toList());
		

	// System.out.println(union);
	return union;

	}
}
