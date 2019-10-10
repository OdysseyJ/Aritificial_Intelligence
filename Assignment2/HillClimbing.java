import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HillClimbing {
	
	// 최고의 score를 가진 neighbor반환하는 메서드.
	// 만약, local optimum에 빠질 경우, restart해줌.
	public static State nextState(State current){
		
		// 가장 점수가 높은 이웃과 비교하기 위한 현재 상태.
		int index = current.getIndex();
		int currentScore = current.calculateScore();
		
		// 가장 점수가 높은 이웃.
		State best = actionRule(current);
		int bestScore = best.calculateScore();
		
		// bestScore는 currentScore보다 크거나 같다.
		// (더 큰 값을 찾아가기 때문 작아질수 없음)
		// 만약, 이웃들 중에서 나보다 더 점수가 높은 이웃이 없다면
		// local Optimal에 빠졌다는 뜻
		if (bestScore == currentScore) {
			// restart
			best.init();
		}
		return best;
	}
	
	// action rule에따라 가능한 모든 neighbor를 구해서
	// 그중 점수가 가장 높은 이웃을 반환해줌.
	// 내가 정의한 Action Rule = 각 열에 있는 퀸 1행씩 밑으로 움직여보기.
	public static State actionRule(State current) {
		int index = current.getIndex();
		int bestScore = current.calculateScore();
		State temp = new State(index);
		State best = new State(index);
		
		// best >= current임.
		// 더 작아질 수 없음.
		for (int i = 0; i < index; i++) {
			best.setState(i, current.getState()[i]);
			temp.setState(i, current.getState()[i]);
		}
		
		// 가능한 모든 이웃 구하기.
		for (int i = 0; i < index; i++) {
			int save = current.getState()[i];
			for (int j = 0; j < index; j++) {
				temp.setState(i, j);
				int tempScore = temp.calculateScore();
				if (tempScore>bestScore) {
					//값 설정
					bestScore = tempScore;
					//복사 temp-> best
					for (int k = 0; k < index; k++) {
						best.setState(k, temp.getState()[k]);
					}
				}
			}
			//값 원상태로 되돌리기.
			temp.setState(i,save);
		}
		return best;
	}
	
	public static void main(String[] args) {
		// 인풋 입력받기.
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
			State current = new State(n);
			current.init();
			// 현재 상태의 점수.
			int score = current.calculateScore();
			// 최고 점수
			int maxScore = n*n;
			
			long beforeTime = System.currentTimeMillis();	
			// Hill Climbing - score가 maxScore가 될때까지
			// current -> next로 이동하면서 score계산.
			while(score != maxScore) {
				current = nextState(current);
				score = current.calculateScore();
			}	
			long afterTime = System.currentTimeMillis();
			// 걸린 시간 측정.
			double totalTime = (afterTime-beforeTime)/1000.0;
			
			// maxScore를 찾은 경우.
			
			// 버퍼에 쓰기.
			fw.write(">Hill Climbing\n");
				for (int temp : current.getState()) {
					fw.write(temp + " ");
				}
				fw.write("\n");
				fw.write("Total Elapsed Time : "+ String.format("%.3f", totalTime) + "\n");
				
			// 파일에 쓰기 
			fw.flush();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}