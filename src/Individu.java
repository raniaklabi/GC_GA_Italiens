import java.util.Random;

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

}
