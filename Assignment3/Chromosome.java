package assignment3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chromosome {
	private int[] queens;
	// n값이 얼마인지 들고있음 (queens.length 호출이 너무 잦아서 만들었습니다.)
	private int index;
	
	// 생성자
	public Chromosome(int n) {
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
	
	public void shuffle(){
	    for(int x=0;x<index;x++){
	      int i = (int)(Math.random()*index);
	      int j = (int)(Math.random()*index);
	            
	      int tmp = queens[i];
	      queens[i] = queens[j];
	      queens[j] = tmp;
	    }
	}
	
	// 현재 들어있는 
	public void init() {
		for (int i = 0; i < index; i++) {
			queens[i] = i;
		}
		shuffle();
	}
	
	// 현재 상태의 점수를 계산해주는 함수
	// n*n - count(서로 겹치는 queen의 개수 * 2 - 모든퀸끼리 서로 비교하므로.)
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
					countarr[i] = 1;
				}
			}
		}
		for (int i = 0; i < index; i++) {
			if (countarr[i]==1) {
				count++;
			}
		}
		return index*index - count*index;
	}

}
