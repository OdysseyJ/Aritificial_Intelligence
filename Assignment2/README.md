Artificial Intelligence
======
Programming Assignment2
------

###### professor. 백은옥


###### NQueens 구현하기 - Hill Climbing



**1. 개요** 


이번 과제는 **NQueens**문제를 Hill Climbing를 이용해서 해결하는 과제입니다.


* Hill Climbing : 내 현재 이웃들 중에서 값이 최고점에 도달할 때 까지 가장 높은 곳으로 이동하는 알고리즘.

**2. 과제 설명**


* N-Queens문제란?


 * N*N의 체스판이 있다.


 * N개의 Queen이 서로 공격하지 않아야 한다. (Queen은 가로, 세로, 대각선에 있는 말들을 모두 공격가능)


 * N=7일 때의 답 예시.


> 6 4 2 0 5 3 1


* N-Queens문제 해결


DFS, BFS, DFID Search 방법을 각각 사용하여 해결. 이때 각각 search에서 첫번째로 찾은 답을 출력한다.


답을 출력할 때는 0~(N-1)까지의 Column에 있는 각 Queen의 row 위치를 출력한다.


* 주의할 점

 * 각 Column에 있는 Queens의 초기 위치는 '랜덤'하게 지정한다.
 * objective function을 각자 정의 한 후 구현한다.
 * **Local optimium에 갇힐 경우 restart 하거나 random walk를 통해서 빠져 나올 것.**


* 입력 형식


Argument형태로 주어집니다. 첫번째는 Argument로 N을 입력받고


두번째는 결과 출력 파일의 '절대경로'를 입력 받는다.


> java -jar 7 /Users/macbook/Documents/Homework#1


* 출력 형식


출력 파일의 이름은 'resultN.txt'로 한다.  예를들어 N=5일때, 파일의 이름은 result5.txt이다.


답이 없다면 Location 대신 No Solution을 출력한다.


**3. 사용언어**


* **java**


---
**[실행 결과]**

![스크린샷_2019-09-30_오후_11.39.52](/uploads/2a3b7c276c14b9c602228a5c5ed374ce/스크린샷_2019-09-30_오후_11.39.52.png)


![스크린샷_2019-09-30_오후_11.40.00](/uploads/f580d66d60424f43261206b0b69b8bd5/스크린샷_2019-09-30_오후_11.40.00.png)


**[HillClimbing.java]**

```java
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
```
**[State.java]**
```java
import java.lang.Math;

// 현재 queen이 놓여있는 상태를 표현하는 클래스.
public class State {
	
	// 퀸을 실제로 놓는 배열.
	private int[] queens;
	// n값이 얼마인지 들고있음 (queens.length 호출이 너무 잦아서 만들었습니다.)
	private int index;
	
	// 생성자
	public State(int n) {
		queens = new int[n];
		index = n;
	}
	
	public int[] getState() {
		return queens;
	}
	
	public void setState(int location, int value) {
		if (index == 0 || location >= index) {
			return ;
		}
		queens[location] = value;
	}
	
	public int getIndex() {
		return index;
	}
	
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
	
	// 현재 들어있는 
	public void init() {
		for (int i = 0; i < index; i++) {
			// 0~9 난수 발생시키기.
			int rNumber = (int)(Math.random()*index);
			queens[i] = rNumber;
		}
	}
	
	// 현재 상태의 점수를 계산해주는 함수
	// n*n - count(서로 겹치는 queen의 개수 * 2 - 모든퀸끼리 서로 비교하므로.)
	public int calculateScore() {
		if (queens.length==0) {
			System.out.println("c초기값 설정이 안되어있어 score를 계산할 수 없음.");
			return -1;
		}
		// 모든 퀸끼리 서로 비교해줌.
		int count = 0;
		for (int i = 0; i < index; i++) {
			for (int j = i+1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if(queens[i]==queens[j] || Math.abs(j-i)== Math.abs(queens[j]-queens[i])) {
					count++;
				}
			}
		}
		return index*index - count;
	}
	
}
```

---
**[소스코드에 대한 설명]**
* State.java - init()

```java
public void init() {
		for (int i = 0; i < index; i++) {
			// 0~9 난수 발생시키기.
			int rNumber = (int)(Math.random()*index);
			queens[i] = rNumber;
		}
	}
```
현재 상태의 queens 배열을 0~index-1까지의 숫자를 발생시켜 랜덤하게 초기화시킵니다.


* State.java - calculateScore()

```java
// 현재 상태의 점수를 계산해주는 함수
	// n*n - count(서로 겹치는 queen의 개수 * 2 - 모든퀸끼리 서로 비교하므로.)
	public int calculateScore() {
		if (queens.length==0) {
			System.out.println("c초기값 설정이 안되어있어 score를 계산할 수 없음.");
			return -1;
		}
		// 모든 퀸끼리 서로 비교해줌.
		int count = 0;
		for (int i = 0; i < index; i++) {
			for (int j = i+1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if(queens[i]==queens[j] || Math.abs(j-i)== Math.abs(queens[j]-queens[i])) {
					count++;
				}
			}
		}
		return index*index - count;
	}
```
현재 상태의 점수를 계산해줍니다. 각 퀸이 공격 가능한 경우 count를 증가시켜줍니다.


이후 인덱스의 제곱에서 count값을 빼서 현재 상태가 가지는 점수를 반환해줍니다.


* HillClimbing.java - nextState()

```java
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
```
actionRule에 의해서 만들어진 여러가지 이웃들을 검사해서 가장 점수가 높은 이웃을 찾습니다.
이후 나보다 더 높은 점수를 가진 이웃이 없다면 local Optimal에 빠졌다는 뜻이므로
score를 다시 초기화시켜줍니다.

* HillClimbing.java - actionRule()

```java
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
```
이웃을 생성하는 방법으로, 각 열에 있는 queen을 한칸씩 움직인 뒤에, 그 중 best state를 반환합니다.

* HillClimbing.java - main()

```java
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
```
while문을 통해 maxScore를 찾을때까지 current->next를 넣어가면서 HillClimbing을 진행합니다.
