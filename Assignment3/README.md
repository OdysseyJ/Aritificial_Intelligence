Artificial Intelligence
======
Programming Assignment3
------
###### professor. 백은옥</br>
###### {+ NQueens+} 구현하기 - {+ Genetic Algorithm +}


**1. 개요**


* 이번 과제는 NQueens문제를 Genetic Algorithm를 이용해서 해결하는 과제입니다.</br>
* Genetic Algorithm : 유전자의 진화 방식을 본따 만든 알고리즘으로, 이전 세대의 정보를 바탕으로 다음 세대를 만들어 점점 원하는 정답을 찾아가는 알고리즘.


**2. 과제 설명**
* __N-Queens문제란?__
 * N*N의 체스판이 있다.
 * N개의 Queen이 서로 공격하지 않아야 한다. (Queen은 가로, 세로, 대각선에 있는 말들을 모두 공격가능)
 * N=7일 때의 답 예시.


> 6 4 2 0 5 3 1

* __N-Queens문제 해결__
 * Genetic Algorithm을 사용하여 해결한다.
 * 답을 출력할 때는 0~(N-1)까지의 Column에 있는 각 Queen의 row 위치를 출력한다.

* __주의할 점__
 * Population, parent selection 방법, crossover rate, mutation rate
 * gene, fitness, cross-over/mutation operator등 모델링에 대한 상세한 설명을 곁들일 것.

* __입력 형식__
 * Argument형태로 주어집니다. 첫번째는 Argument로 N을 입력받고
 * 두번째는 결과 출력 파일의 '절대경로'를 입력 받는다.


> java -jar 7 /Users/macbook/Documents/Homework#1


* __출력 형식__
 * 출력 파일의 이름은 'resultN.txt'로 한다.  예를들어 N=5일때, 파일의 이름은 result5.txt이다.
 * 답이 없다면 Location 대신 No Solution을 출력한다.


**3. 사용언어 / 환경**


* {+ JAVA/MAC +}


---
**[실행 결과]**


<img width="490" alt="스크린샷 2019-10-16 오후 10 09 22" src="https://user-images.githubusercontent.com/23691933/66936143-2ccec180-f078-11e9-9ec0-d5a8eb61b47a.png">


**[결과에 대한 분석]**


나는 현재 세대의 인구 population = 100, crossover rate = 90%, mutation rate = 10%를 사용했다.


population이 굉장히 적으면 다양한 유전종을 확보하지 못해서 maxScore로 올라가는 계산이 느린 듯 했고,


population이 굉장히 많으면 유전적 다양성은 확보가 가능하지만 계산량이 너무많아서 maxScore는 천천히 올라갔다. 하지만 maxScore근처에 가게 되면 다양성이 확보되어 금방 답을 계산해내는 듯 했다.


제일 적절하게 시간을 비교해본 결과 100~500수준이 가장 준수한 시간성능을 보였다.


내 모델링은 parent selection에서 rullet wheel 방법을 사용했는데, rullet wheel을 사용해 우수한 유전자를 뽑아냈는데 특이한점이라고 하면 좀 더 우수한 유전자가 뽑힐 확률을 늘려주려고 n*n - count*n의 점수 계산을 해서 사용했다. 사실 fitness로 세제곱도 고려했는데 성능이 크게 나아지는것 같지는 않았다.


cross-over는 모든 점을 division point로 잡아 그중 가장 점수가 높은 곳을 선택하고 이후 중복제거를 해줬다.
해당 크로스오버는 성능이 어느정도 보장된 듯 하다.


__장점__ : 굉장히 큰 n에 대해서도 얼마든지 계산이 가능하다. rullet wheel을 통해 좋은 유전자와 열성 유전자의 차이를 크게 줘서 값이 증가하는 속도가 빠르다.


__단점__ : mutation연산이 단순하여 생각보다 score n*n 근처에서 답을 쉽게 구해내지 못한다. 아마 n이 진짜 커지게 되면 돌연변이 연산을 재정의해야하지 않을까 싶다.


**[전체 소스코드]**


{- Chromosome.java -}


| 멤버변수 | 설명 |
| -------- | -------- |
| (int[]) queens  | 유전자가 보유하고 있는 퀸 배열  |
| (int) index   | 퀸 배열의 개수   |


```java
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
			return ;
		}
		queens[location] = value;
	}
	
	public int getIndex() {
		return index;
	}
	
	// 현재 유전자가 가지고있는 퀸 배열을 출력.
	public void printState() {
		if (queens.length==0) {
			System.out.println("초기값 설정이 안되어있어 print할 수 없음.");
			return;
		}
	
		for (int i = 0; i < index; i++) {
			System.out.print(queens[i]+ " ");
		}
		System.out.println();
	}
	
	// current배열을 램덤하게 섞어주는 함수.
	public void shuffle(){
	    for(int x=0;x<index;x++){
	      int i = (int)(Math.random()*index);
	      int j = (int)(Math.random()*index);
	            
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
		if (queens.length==0) {
			System.out.println("c초기값 설정이 안되어있어 score를 계산할 수 없음.");
			return -1;
		}
		// 모든 퀸끼리 서로 비교해줌.
		int[] countarr = new int[index];
		int count = 0;
		for (int i = 0; i < index; i++) {
			for (int j = i+1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if(queens[i]==queens[j] || Math.abs(j-i)== Math.abs(queens[j]-queens[i])) {
					// 칼럼의 개수를 센다.
					countarr[i] = 1;
				}
			}
		}
		// count = 공격가능한 칼럼의 개수 
		for (int i = 0; i < index; i++) {
			if (countarr[i]==1) {
				count++;
			}
		}
		return index*index - count*index;
	}

}
```


{- Genetic.java -}



| 멤버변수 | 설명 |
| -------- | -------- |
| (int) POPULATION  | 한 세대가 가지는 유전자의 개수  |
| (double) MUTATION_RATE   | 돌연변이가 나타날 확률   |
| (double) CROSSOVER_RATE  | crossover가 일어날 확률   |
| (Chromosome[]) current   | 현재 세대의 유전자 배열   |
| (float[]) wheel  | Rullet Wheel   |
| (int) generation   | 현재까지 나타난 세대  |



```java
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
			System.out.print(wheel[i]+ " ");
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
			wheelScore = (float)current[i].calculateScore()/(float)sum;
			
			// 휠 만들기
			if (i==0) {
				wheel[i] = wheelScore;
			}
			else {
				wheel[i] = wheel[i-1]+wheelScore;
			}
		}
	}
	
	// 유전자 선택을 도와주는 함수
	// picker = 0~1사이의 랜덤 수
	// picker 를 가지고 rullet wheel을 돌면서 해당 범위에 해당하는 유전자를 들고옴.
	// rullet wheel이 해당 유전자가 선택될 확률을 반영하고 있다.(유전자가 좋을수록 선택확률 높아짐)
	public int selectChromosome() {
		// picker = 0~1사이 랜덤 수
		float picker = (float)(Math.random());
		
		
		// 휠에서 선택하기
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
		if (Math.random()>CROSSOVER_RATE) {
			if (Math.random()<0.5) {
				tempCurrent[j] = current[mother];
			}
			else {
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
				if (number[i]==0) {
					for (int k = 0; k < n; k++) {
						if( number[k]>1) {
							number[i]++;
							number[k]--;
							for (int l = 0; l < n; l++) {
								if(child.getState()[l]==k) {
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
		if (Math.random()<MUTATION_RATE) {
			// 랜덤한 두 칼럼 찾기
			int first = generator.nextInt(n);
			int second = generator.nextInt(n);
			while (second==first) {
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
			if(current[i].calculateScore() == maxScore) {
				return current[i];
			}
		}
		return null;
	}
}
```


{- main.java -}
```java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
			
			// genetic algorithm을 위한 클래스 선언.
			Genetic genetic = new Genetic();
			
			// genetic algorithm 초기화 (랜덤한 부모 POPULATION만큼 만듬.).
			genetic.init(n);
			
			// 최고 점수 = n*n, 점수 계산법 = (n*n) - (attack가능한column의수*n)
			int maxScore = n*n;
			
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
			double totalTime = (afterTime-beforeTime)/1000.0;

			// 버퍼에 쓰기.
			fw.write(">Genetic Algorithm\n");
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
```
---
**[소스코드에 대한 설명]**


{- * Chromosome.java - init() -}

```java
// 현재 퀸 배열을 겹치지 않게 정의한 뒤에, 랜덤으로 섞어줍니다.
	public void init() {
		// 겹치지 않게 정의 
		for (int i = 0; i < index; i++) {
			queens[i] = i;
		}
		// 랜덤 섞기
		shuffle();
	}
```
-> 현재 유전자의 퀸 배열을 겹치지 않게 정의하고, shuffle()함수를 통해 랜덤으로 섞어줍니다.


{- * Chromosome.java - shuffle() -}

```java
// current배열을 램덤하게 섞어주는 함수.
	public void shuffle(){
	    for(int x=0;x<index;x++){
	      int i = (int)(Math.random()*index);
	      int j = (int)(Math.random()*index);
	            
	      int tmp = queens[i];
	      queens[i] = queens[j];
	      queens[j] = tmp;
	    }
	}
```
-> 현재 current 배열을 랜덤하게 섞어줍니다 (중복 발생 x)


{- * Chromosome.java  - calculateScore() -}

```java
// 현재 상태의 점수를 계산해주는 함수
	// n*n - 공격가능한columnm의 개수 * n
	// 저번 과제에서는 n*n - count로 정의했으나 
	// 이번 과제에서는 Rullet wheel을 사용할때 점수차이가 클수록 좋은 유전자와 안좋은 유전자가 선택될 확률의 차이가 크기 때문에
	// 빠른 속도로 찾을 수 있어서 n*n - count*n으로 정의했습니다.
	public int calculateScore() {
		if (queens.length==0) {
			System.out.println("c초기값 설정이 안되어있어 score를 계산할 수 없음.");
			return -1;
		}
		// 모든 퀸끼리 서로 비교해줌.
		int[] countarr = new int[index];
		int count = 0;
		for (int i = 0; i < index; i++) {
			for (int j = i+1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if(queens[i]==queens[j] || Math.abs(j-i)== Math.abs(queens[j]-queens[i])) {
					// 칼럼의 개수를 센다.
					countarr[i] = 1;
				}
			}
		}
		// count = 공격가능한 칼럼의 개수 
		for (int i = 0; i < index; i++) {
			if (countarr[i]==1) {
				count++;
			}
		}
		return index*index - count*index;
	}
```
-> 현재 유전자의 점수를 계산해주는 함수입니다.</br>
저번 과제에서는 n*n - count로 정의했으나 이번 과제에서는 Rullet Wheel을 사용할 때 점수차이가 클 수록 좋은 유전자가 선택될 확률이 크기 때문에 count(겹치는 칼럼의 개수) * index만큼 빼줬습니다.

{- * Genetic.java - init() -}

```java
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
```
-> genetic 알고리즘을 위해 상단에 정의된 population만큼의 부모를 만들어내는 함수입니다.


이후 makeWheel()메서드를 통해서 초기 룰렛 휠을 제작합니다.


{- * Genetic.java - makeWheel() -}

```java
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
			wheelScore = (float)current[i].calculateScore()/(float)sum;
			
			// 휠 만들기
			if (i==0) {
				wheel[i] = wheelScore;
			}
			else {
				wheel[i] = wheel[i-1]+wheelScore;
			}
		}
	}
```
-> Rullet wheel을 만들어주는 함수입니다.


n번째 유전자가 선택될 확률 = n번째 유전자의 점수 / 전체 유전자의 점수로 계산하며,


유전자의 점수가 높을수록 해당 룰렛 휠에서의 비율이 높아 선택 확률이 높아집니다.


{- * Genetic.java - selectChromosome() -}


```java
// 유전자 선택을 도와주는 함수
	// picker = 0~1사이의 랜덤 수
	// picker 를 가지고 rullet wheel을 돌면서 해당 범위에 해당하는 유전자를 들고옴.
	// rullet wheel이 해당 유전자가 선택될 확률을 반영하고 있다.(유전자가 좋을수록 선택확률 높아짐)
	public int selectChromosome() {
		// picker = 0~1사이 랜덤 수
		float picker = (float)(Math.random());
		
		
		// 휠에서 선택하기
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
```
-> 유전자 선택을 도와주는 함수입니다. picker = 0~1사이의 랜덤한 수로, picker를 가지고 rullet wheel을 돌면서 해당 범위에 해당하는 유전자를 들고옵니다.


{- * Genetic.java - crossOverByDivisionPoint() -}

```java
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
		if (Math.random()>CROSSOVER_RATE) {
			if (Math.random()<0.5) {
				tempCurrent[j] = current[mother];
			}
			else {
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
				if (number[i]==0) {
					for (int k = 0; k < n; k++) {
						if( number[k]>1) {
							number[i]++;
							number[k]--;
							for (int l = 0; l < n; l++) {
								if(child.getState()[l]==k) {
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
```
-> Division Point를 통한 crossOver를 진행하는 함수입니다. 


crossOver = 부모의 결합을 통해 자식을 만들어내는 방법.


Division Point를 기준으로 왼쪽 = 어머니, 오른쪽 = 아버지의 유전자를 받아서 crossOver를 진행하며
0~n-1까지의 division point를 돌면서 가장 좋은 division point를 찾습니다. (제일 점수가 높은 지점.)
해당 지점을 기준으로 child를 만들어내고, child내에서 중복되는 부분이 생기면 중복 제거를 통해서 제거 해줍니다.


{- * Genetic.java - mutation() -}

```java
// 돌연변이를 일으키는 함수 (확률 10%)
	// 랜덤한 두 column의 값을 교환한다.
	public void mutation(Chromosome child, int n) {
		Random generator = new Random();
		if (Math.random()<MUTATION_RATE) {
			// 랜덤한 두 칼럼 찾기
			int first = generator.nextInt(n);
			int second = generator.nextInt(n);
			while (second==first) {
				second = generator.nextInt(n);
			}
			// column교환
			int temp = child.getState()[first];
			child.getState()[first] = child.getState()[second];
			child.getState()[second] = temp;
		}
	}
```
-> 돌연변이를 발생시키는 함수입니다. 특정 child를 받아서 일정 확률에 따라 child의 퀸 배열중 랜덤한 2개를 골라
두개의 순서를 뒤바꿉니다.



{- * Genetic.java - findSolution() -}

```java
// 답을 찾기 위한 함수.
	// current배열을 돌면서 maxScore에 해당하는 녀석이 있는지 본다.
	// 있으면 반환. 없으면 null
	public Chromosome findSolution(int maxScore) {
		for (int i = 0; i < POPULATION; i++) {
			if(current[i].calculateScore() == maxScore) {
				return current[i];
			}
		}
		return null;
	}
```
-> current배열을 돌면서 maxScore에 해당하는 녀석이 있는지 답을 찾아내는 함수입니다.
있으면 해당 maxScore를 가진 유전자를 반환하고, 없으면 null을 반환합니다.
