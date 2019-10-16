package assignment1;

import java.lang.Math;

// 각 노드가 가지는 상태를 나타내는 클래스.
public class State {
	// 각 상태가 가지는 퀸 배열
	// queens[i] = i번째 열에 놓여있는 퀸의 위치
	// index = queens[]배열의 몇번째 열까지 퀸이 놓여있나?
	private int[] queens;
	private int index;
	
	// 생성자
	public State(int n) {
		queens = new int[n];
		index = 0;
	}
	
	public int[] getState() {
		return queens;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setState(int location) {
		queens[index] = location;
		index++;
	}
	
	// nqueens 규칙에 맞게 퀸이 놓여있는지 판별하는 함수.
	public boolean isPossible() {
		// 모든 퀸끼리 서로 비교해줌.
		for (int i = 0; i < index; i++) {
			for (int j = i+1; j < index; j++) {
				// nqueens조건에 맞지 않는 경우.
				// 같은 줄에 놓여 있는 경우 || 대각선상에 있는 경우 (밑변과 높이가 같은 경우).
				if(queens[i]==queens[j] || Math.abs(j-i)== Math.abs(queens[j]-queens[i])) {
					return false;
				}
			}
		}
		// nqueens조건에 맞는 경우
		return true;
	}
}
