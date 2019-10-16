public class Chromosome {
	private int[] queens;
	private int index;

	// 생성자
	public Chromosome(int n) {
		queens = new int[n];
		index = n;
	}

	public int[] getState() {
		return queens;
	}

	// 현재 유전자가 가지고있는 퀸 배열을 설정하는 함수.
	public void setState(int location, int value) {
		if (index == 0 || location >= index) {
			return;
		}
		queens[location] = value;
	}

	public int getIndex() {
		return index;
	}

	// 현재 유전자가 가지고있는 퀸 배열을 출력.
	public void printState() {
		if (queens.length == 0) {
			System.out.println("초기값 설정이 안되어있어 print할 수 없음.");
			return;
		}

		for (int i = 0; i < index; i++) {
			System.out.print(queens[i] + " ");
		}
		System.out.println();
	}

	// current배열을 램덤하게 섞어주는 함수.
	public void shuffle() {
		for (int x = 0; x < index; x++) {
			int i = (int) (Math.random() * index);
			int j = (int) (Math.random() * index);

			int tmp = queens[i];
			queens[i] = queens[j];
			queens[j] = tmp;
		}
	}

	// 현재 퀸 배열을 겹치지 않게 정의한 뒤에, 랜덤으로 섞어줍니다.
	public void init() {
		// 겹치지 않게 정의
		for (int i = 0; i < index; i++) {
			queens[i] = i;
		}
		// 랜덤 섞기
		shuffle();
	}

	// 현재 상태의 점수를 계산해주는 함수
	// n*n - 공격가능한columnm의 개수 * n
	// 저번 과제에서는 n*n - count로 정의했으나
	// 이번 과제에서는 Rullet wheel을 사용할때 점수차이가 클수록 좋은 유전자와 안좋은 유전자가 선택될 확률의 차이가 크기 때문에
	// 빠른 속도로 찾을 수 있어서 n*n - count*n으로 정의했습니다.
	public int calculateScore() {
		if (queens.length == 0) {
			System.out.println("c초기값 설정이 안되어있어 score를 계산할 수 없음.");
			return -1;
		}
		// 모든 퀸끼리 서로 비교해줌.
		int[] countarr = new int[index];
		int count = 0;
		for (int i = 0; i < index; i++) {
			for (int j = i + 1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if (queens[i] == queens[j] || Math.abs(j - i) == Math.abs(queens[j] - queens[i])) {
					// 칼럼의 개수를 센다.
					countarr[i] = 1;
				}
			}
		}
		// count = 공격가능한 칼럼의 개수
		for (int i = 0; i < index; i++) {
			if (countarr[i] == 1) {
				count++;
			}
		}
		return index * index - count * index;
	}

}