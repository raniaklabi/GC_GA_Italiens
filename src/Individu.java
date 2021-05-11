import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Individu {
	int []C;
	int size;
	double fitness;
	public int[] getC() {
		return C;
	}
	public void setC(int[] c) {
		C = c;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public void CalculeFitness(double []pi) {
		this.fitness=0;
		for(int i=1;i<size;i++){
			if(C[i]==1) {
				this.fitness=this.fitness+pi[i];
		
		}
		
		}
	}
	public void CalculeFitnessBeta(double []pi,double []fi, int[][]delta,int m,double betha) {
		this.fitness=0;
		for(int i=1;i<size;i++){
			if(C[i]==1) {
				this.fitness=this.fitness+pi[i];
		
		}
		
		}
		List<Integer> coveredTarget= new ArrayList<Integer>();
		for(int j=1;j<size;j++) {
			if(C[j]==1) {
			ArrayList<Integer> Ti= new ArrayList<Integer>();
			for(int jjj=0;jjj<m;jjj++) {
				if(delta[j][jjj]==1) {
					Ti.add(jjj);
				}
			}
			coveredTarget=union2sets(coveredTarget, Ti);
			}
		}
		for(int j=0;j<coveredTarget.size();j++) {
			this.fitness=this.fitness-fi[coveredTarget.get(j)];
			
		}
		double s=0;
		for(int k=0;k<coveredTarget.size();k++) {
			s=s+(betha*fi[coveredTarget.get(k)]);
		}
		this.fitness=s-this.fitness;
		
    	
	}
	/*public  Individu(int n)
	{
		size=n;
		C=new int [size];
		C[0]=1;
		Random random = new Random();
		for(int i=1;i<size;i++)
		{
			C[i] = random.nextInt(2);
		}
		fitness=0;
		
	}*/
	public  Individu(int n)
	{
		size=n;
		C=new int [size];
		C[0]=1;
		for(int i=1;i<size;i++)
		{
			C[i] = 0;
		}
		fitness=0;
		
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
