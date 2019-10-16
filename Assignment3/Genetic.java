import java.util.Random;

public class Genetic {

	// 한 세대가 가지는 유전자의 개수
	// 돌연변이가 나타날 확률
	// crossover가 일어날 확률
	public static final int POPULATION = 100;
	public static final double MUTATION_RATE = 0.1;
	public static final double CROSSOVER_RATE = 0.9;

	// 현재 세대의 유전자를 보관하고 있는 배열
	// Rullet wheel을 위해 현재 유전자들을 가지고 만든 휠.
	// 현재 세대
	Chromosome[] current;
	float[] wheel;
	int generation;

	// 생성자
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

	// 현재 유전자 배열 출력
	public void printCurrent() {
		for (int i = 0; i < POPULATION; i++) {
			current[i].printState();
		}
		System.out.println();
	}

	// 현재 휠 출력
	public void printWheel() {
		for (int i = 0; i < POPULATION; i++) {
			System.out.print(wheel[i] + " ");
		}
		System.out.println();
	}

	// genetic알고리즘을 위해 POPULATION만큼의 부모를 만들어냄.
	// 무작위 랜덤으로 유전자 생성.
	// 해당 랜덤 유전자 배열을 가지고 wheel을 만듬.
	public void init(int n) {
		for (int i = 0; i < POPULATION; i++) {
			Chromosome ch = new Chromosome(n);
			ch.init();
			current[i] = ch;
		}
		makeWheel();
	}

	// Rullet wheel을 위해 휠을 만들어주는 함수
	// 1번째 유전자가 선택될 확률 = 1번째유전자의 점수 / 전체 유전자 점수의 합
	// 유전자의 점수가 높을수록 선택 확률이 높아진다.
	// 만약, 4개의 유전자셋이 있으면
	// 1번 유전자 = 0~0.3, 2번 유전자 = 0.3~0.5, 3번유전자 = 0.5~0.65, 4번유전자 = 0.65~1.0
	// 따라서 wheel[POPULATION-1] = 1이다.
	public void makeWheel() {
		int sum = 0;
		float wheelScore;
		// 유전자 점수들의 합
		for (int i = 0; i < POPULATION; i++) {
			sum += current[i].calculateScore();
		}
		for (int i = 0; i < POPULATION; i++) {
			// 현재 유전자의 점수 / 전체 유전자 점수들의 합
			wheelScore = (float) current[i].calculateScore() / (float) sum;

			// 휠 만들기
			if (i == 0) {
				wheel[i] = wheelScore;
			} else {
				wheel[i] = wheel[i - 1] + wheelScore;
			}
		}
	}

	// 유전자 선택을 도와주는 함수
	// picker = 0~1사이의 랜덤 수
	// picker 를 가지고 rullet wheel을 돌면서 해당 범위에 해당하는 유전자를 들고옴.
	// rullet wheel이 해당 유전자가 선택될 확률을 반영하고 있다.(유전자가 좋을수록 선택확률 높아짐)
	public int selectChromosome() {
		// picker = 0~1사이 랜덤 수
		float picker = (float) (Math.random());

		// 휠에서 선택하기
		if (0 <= picker && picker < wheel[0]) {
			return 0;
		}

		for (int i = 1; i < POPULATION; i++) {
			if (i != POPULATION - 1) {
				if (wheel[i] <= picker && picker < wheel[i + 1]) {
					return i;
				}

			} else {
				return i;
			}
		}
		return -1;
	}

	// Division Point를 통한 crossOver함수.
	// Division Point를 기준으로 왼쪽 = 어머니, 오른쪽 = 아버지의 유전자를 받음.
	// 0~n-1까지의 division point를 돌면서 가장 좋은 division point를 찾음.(제일 점수가 높은 곳)
	// 이후 해당 division point를 기준으로 child를 만듬
	// 그렇게 child를 생성하면 중복되는 부분이 생김 => 중복 제거.
	public void crossOverByDivisionPoint(int n) {
		Chromosome[] tempCurrent = new Chromosome[POPULATION];
		Random generator = new Random();

		// 어머니 유전자, 아버지 유전자 찾기
		for (int j = 0; j < POPULATION; j++) {
			int mother = this.selectChromosome();
			int father = this.selectChromosome();

			// 중복 제거
			while (father == mother) {
				father = this.selectChromosome();
			}

			// crossOver를 하지 않는 경우 (10%)
			// 어머니나 아버지중 부모를 하나를 그냥 그대로 자식으로 준다.
			if (Math.random() > CROSSOVER_RATE) {
				if (Math.random() < 0.5) {
					tempCurrent[j] = current[mother];
				} else {
					tempCurrent[j] = current[father];
				}
			}

			// crossOver를 하는경우
			else {
				int max = 0;
				int index = 0;
				int divisionPoint = 0;
				Chromosome child = null;
				// 가장 좋은 division point찾기.
				for (int k = 0; k < n; k++) {

					child = new Chromosome(n);

					divisionPoint = k;
					for (int i = 0; i < divisionPoint; i++) {
						child.setState(i, current[mother].getState()[i]);
					}
					for (int i = divisionPoint; i < n; i++) {
						child.setState(i, current[father].getState()[i]);
					}
					// 가장 좋은 division point = 점수가 가장 큰 division point
					if (child.calculateScore() > max) {
						max = child.calculateScore();
						index = k;
					}
				}
				// 가장 좋은 division point를 찾은 경우.
				divisionPoint = index;

				// 왼쪽 = 어머니
				for (int i = 0; i < divisionPoint; i++) {
					child.setState(i, current[mother].getState()[i]);
				}
				// 오른쪽 = 아버지
				for (int i = divisionPoint; i < n; i++) {
					child.setState(i, current[father].getState()[i]);
				}

				// 중복 제거를 위해 중복되는 숫자 검색
				int[] number = new int[n];
				for (int i = 0; i < n; i++) {
					number[child.getState()[i]]++;
				}

				// 하나도 들어있지 않은 숫자를 찾는다.(i)
				// 중복 숫자를 찾는다.(k)
				// child의 column중에서 중복 숫자를 가진 column을 찾는다.(l)
				// l자리에 하나도 들어있지 않은 숫자 i를 넣는다.
				// i 숫자를 증가시켜주고, k숫자를 감소시킨다.
				for (int i = 0; i < n; i++) {
					if (number[i] == 0) {
						for (int k = 0; k < n; k++) {
							if (number[k] > 1) {
								number[i]++;
								number[k]--;
								for (int l = 0; l < n; l++) {
									if (child.getState()[l] == k) {
										child.setState(l, i);
									}
								}
							}
						}
					}
				}

				// 돌연변이를 일으켜본다 (확률 10%)
				// 만약 돌연변이가 일어나면 해당 유전자 child의 2개의 column값이 서로 바뀐다.
				mutation(child, n);
				// Current에 저장하기 위해 임시로 만든 배열
				tempCurrent[j] = child;
			}
		}

		// current에 현재까지 만든 모든 자식들 저장시킴. (다음세대)
		for (int i = 0; i < POPULATION; i++) {
			current[i] = tempCurrent[i];
		}
		// 휠 다시 만들기 (자식들이 바뀌었으므로)
		makeWheel();
		// 세대 이동.
		generation++;
	}

	// crossOver를 division point가 아닌 다른방식으로 진행한 함수입니다.
	// 처음엔 이걸로 진행하다가 성능이 더 좋은걸 발견해서 그냥 냅뒀습니다. (해당 과제에서는 사용하지 않음)
	// 아이디어 : 어머니 / 아버지에서 겹치는 column은 채택하고
	// 나머지 겹치지 않는 column은 중복을 제거한 채로 랜덤으로 수를 넣어준다.
	public void crossOver(int n) {
		Chromosome[] tempCurrent = new Chromosome[POPULATION];
		Random generator = new Random();

		for (int j = 0; j < POPULATION; j++) {
			int mother = this.selectChromosome();
			int father = this.selectChromosome();
			while (father == mother) {
				father = this.selectChromosome();
			}

			// 1-crossover_rate * 100 의 확률 그대로 부모를 줌
			if (Math.random() > CROSSOVER_RATE) {
				if (Math.random() < 0.5) {
					tempCurrent[j] = current[mother];
				} else {
					tempCurrent[j] = current[father];
				}
			}

			// 이외의 경우 부모가 아닌 자식을 준다.
			else {
				Chromosome child = new Chromosome(n);

				int[] same = new int[n];
				int[] number = new int[n];
				// 같은열 찾기
				for (int i = 0; i < n; i++) {
					if (current[mother].getState()[i] == current[father].getState()[i]) {
						same[i] = 1;
					} else {
						number[current[mother].getState()[i]] = 1;
					}
				}
				// 같은건 그대로
				for (int i = 0; i < n; i++) {
					if (same[i] == 1) {
						child.setState(i, current[mother].getState()[i]);
					}
				}

				// 다른건 랜덤으로.
				for (int i = 0; i < n; i++) {
					if (same[i] == 0) {
						while (true) {
							int k = generator.nextInt(n);
							if (number[k] == 1) {
								child.setState(i, k);
								number[k] = 0;
								break;
							}
						}
					}
				}

				mutation(child, n);
				tempCurrent[j] = child;
				System.out.print("calculate: " + child.calculateScore() + " ");
				child.printState();
			}
		}

		for (int i = 0; i < POPULATION; i++) {
			current[i] = tempCurrent[i];
		}
		makeWheel();
		generation++;
	}

	// 돌연변이를 일으키는 함수 (확률 10%)
	// 랜덤한 두 column의 값을 교환한다.
	public void mutation(Chromosome child, int n) {
		Random generator = new Random();
		if (Math.random() < MUTATION_RATE) {
			// 랜덤한 두 칼럼 찾기
			int first = generator.nextInt(n);
			int second = generator.nextInt(n);
			while (second == first) {
				second = generator.nextInt(n);
			}
			// column교환
			int temp = child.getState()[first];
			child.getState()[first] = child.getState()[second];
			child.getState()[second] = temp;
		}
	}

	// 답을 찾기 위한 함수.
	// current배열을 돌면서 maxScore에 해당하는 녀석이 있는지 본다.
	// 있으면 반환. 없으면 null
	public Chromosome findSolution(int maxScore) {
		for (int i = 0; i < POPULATION; i++) {
			if (current[i].calculateScore() == maxScore) {
				return current[i];
			}
		}
		return null;
	}
}
