package assignment3;

import java.util.Arrays;
import java.util.Random;

public class Genetic {
	public static final int POPULATION = 100;
	public static final double MUTATION_RATE = 0.1;
	public static final double CROSSOVER_RATE = 0.9;
	Chromosome[] current;
	float[] wheel;
	int generation;
	
	public Genetic() {
		current = new Chromosome[POPULATION];
		wheel = new float[POPULATION];
		generation = 0;
	}
	
	public Chromosome[] getCurrent() {
		return current;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public void printCurrent() {
		for (int i = 0; i < POPULATION; i++) {
			current[i].printState();
		}
		System.out.println();
	}
	
	public void printWheel() {
		for (int i = 0; i < POPULATION; i++) {
			System.out.print(wheel[i]+ " ");
		}
		System.out.println();
	}
	
	public void init(int n) {
		for (int i = 0; i < POPULATION; i++) {
			Chromosome ch = new Chromosome(n);
			ch.init();
			current[i] = ch;
		}
		makeWheel();
	}
	
	public void makeWheel() {
		int sum = 0;
		float wheelScore;
		for (int i = 0; i < POPULATION; i++) {
			sum += current[i].calculateScore();
		}
		for (int i = 0; i < POPULATION; i++) {
			wheelScore = (float)current[i].calculateScore()/(float)sum;
			if (i==0) {
				wheel[i] = wheelScore;
			}
			else {
				wheel[i] = wheel[i-1]+wheelScore;
			}
		}
	}
	
	public int selectChromosome() {
		float picker = (float)(Math.random());
		
		if (0<=picker && picker<wheel[0]) {
			return 0;
		}
		
		for(int i = 1; i < POPULATION; i++) {
			if (i != POPULATION-1) {
				if (wheel[i]<=picker && picker<wheel[i+1]) {
					return i;
				}
			
			}
			else {
				return i;
			}
		}
		return -1;
	}
	
	public void crossOver(int n) {
		Chromosome[] tempCurrent = new Chromosome[POPULATION];
		Random generator = new Random();
		
		for (int j = 0; j < POPULATION; j++) {
		int mother = this.selectChromosome();
		int father = this.selectChromosome();
		while (father == mother) {
			father = this.selectChromosome();
		}
		
		//1-crossover_rate * 100 의 확률 그대로 부모를 줌
		if (Math.random()>CROSSOVER_RATE) {
			if (Math.random()<0.5) {
				tempCurrent[j] = current[mother];
			}
			else {
				tempCurrent[j] = current[father];
			}
		}
		
		//이외의 경우 부모가 아닌 자식을 준다.
		else {
		Chromosome child = new Chromosome(n);
			
		int[] same = new int[n];
		int[] number = new int[n];
		// 같은열 찾기
		for (int i = 0; i < n; i++) {
			if (current[mother].getState()[i] == current[father].getState()[i]) {
			same[i] = 1;
			}
			else {
				number[current[mother].getState()[i]] = 1;
			}
		}
		// 같은건 그대로 
		for (int i = 0; i < n; i++) {				
			if (same[i]==1) {
			child.setState(i, current[mother].getState()[i]);			}
		}
		
		// 다른건 랜덤으로.
		for (int i = 0; i < n; i++) {
				if (same[i]==0) {
					while(true) {
						int k = generator.nextInt(n);
						if(number[k]==1) {
							child.setState(i,k);
							number[k] = 0;
							break;
						}
					}
				}
		}

		mutation(child, n);
		tempCurrent[j] = child;
		}
		}
		
		for (int i = 0; i < POPULATION; i++) {
			current[i] = tempCurrent[i];
		}
		makeWheel();
		generation++;
	}
	
	public void mutation(Chromosome child, int n) {
		if (Math.random()<MUTATION_RATE) {
			int first = (int)(Math.random()*n);
			int second = (int)(Math.random()*n);
			while (second==first) {
				second = (int)(Math.random()*n);
			}
			int temp = child.getState()[first];
			child.getState()[first] = child.getState()[second];
			child.getState()[second] = temp;
			
			first = (int)(Math.random()*n);
			second = (int)(Math.random()*n);
			while (second==first) {
				second = (int)(Math.random()*n);
			}
			temp = child.getState()[first];
			child.getState()[first] = child.getState()[second];
			child.getState()[second] = temp;
		}
	}
	
	
	public Chromosome findSolution(int maxScore) {
		for (int i = 0; i < POPULATION; i++) {
			if(current[i].calculateScore() == maxScore) {
				return current[i];
			}
		}
		return null;
	}
}
