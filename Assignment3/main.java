import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class main {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		String path = args[1];

		// 예외 처리.
		if (n <= 3) {
			return;
		}

		// 저장 경로 저장 및 파일 writer 선언.
		File file = new File(args[1] + "/result" + Integer.toString(n) + ".txt");
		FileWriter fw = null;

		try {
			fw = new FileWriter(file, false);

			// genetic algorithm을 위한 클래스 선언.
			Genetic genetic = new Genetic();

			// genetic algorithm 초기화 (랜덤한 부모 POPULATION만큼 만듬.).
			genetic.init(n);

			// 최고 점수 = n*n, 점수 계산법 = (n*n) - (attack가능한column의수*n)
			int maxScore = n * n;

			// maxScore와 같은 녀석이 있는지 확인해서, 있으면 해당 유전자 반환, 없으면 null
			Chromosome current = genetic.findSolution(maxScore);

			long beforeTime = System.currentTimeMillis();

			// maxScore를 가진 녀석을 찾을때까지 계속해서 반복문을 돔.
			while (current == null) {
				// crossOver를 통해서 새로운 세대를 만들어냄.
				genetic.crossOverByDivisionPoint(n);
				// 새로운 세대가 maxScore를 가진 녀석이 있는지 확인.
				current = genetic.findSolution(maxScore);
			}

			long afterTime = System.currentTimeMillis();

			// maxScore를 찾은 녀석
			current.printState();
			System.out.println("Generation : " + genetic.getGeneration());

			// 걸린 시간 측정.
			double totalTime = (afterTime - beforeTime) / 1000.0;

			// 버퍼에 쓰기.
			fw.write(">Genetic Algorithm\n");
			for (int temp : current.getState()) {
				fw.write(temp + " ");
			}
			fw.write("\n");
			fw.write("Total Elapsed Time : " + String.format("%.3f", totalTime) + "\n");

			// 파일에 쓰기
			fw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
