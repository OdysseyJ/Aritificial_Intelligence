package assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.omg.CORBA.Current;

import assignment2.State;

public class main {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		String path = args[1];
		
		// 예외 처리.
		if (n <= 3) {
			return ;
		}
		
		// 저장 경로 저장 및 파일 writer 선언.
		File file = new File(args[1]+"/result" + Integer.toString(n) + ".txt");
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(file, false);
			// 현재 상태 초기화.

			Genetic genetic = new Genetic();
			genetic.init(n);
			
			int maxScore = n*n;
			Chromosome current = genetic.findSolution(maxScore);
			
			long beforeTime = System.currentTimeMillis();	
			
			while (current == null) {
				genetic.crossOver(n);
				current = genetic.findSolution(maxScore);
			}
			
			long afterTime = System.currentTimeMillis();

			current.printState();
			
			// 걸린 시간 측정.
			double totalTime = (afterTime-beforeTime)/1000.0;

			System.out.println(totalTime);
			System.out.println(genetic.getGeneration());
			// maxScore를 찾은 경우.
			
			// 버퍼에 쓰기.
			fw.write(">Hill Climbing\n");
//				for (int temp : current.getState()) {
//					fw.write(temp + " ");
//				}
				fw.write("\n");
				fw.write("Total Elapsed Time : "+ String.format("%.3f", totalTime) + "\n");
				
			// 파일에 쓰기 
			fw.flush();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
