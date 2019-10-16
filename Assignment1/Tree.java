package assignment1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree {
	State rootNode;
	Queue<State> queue = new LinkedList<State>();
	Stack<State> stack = new Stack<State>();
	
	int n;

	public Tree(int n) {
		this.n = n;
		rootNode = new State(n);
	}
	
	// bfs는 큐를 이용해서 구현했습니다.
	public Result bfs() {
		// 함수 실행 시간 측정을 위해서. 측정 시작.
		long beforeTime = System.currentTimeMillis();
		queue.clear();
		
		//root queue에 넣기.
		State tempState = new State(n);
		queue.offer(tempState);
		
		while(!queue.isEmpty()) {
			// 큐에서 하나를 뺀다.
			State pollState = queue.poll();
			int index = pollState.getIndex();
			// 끝까지 도달한 경우 (퀸을 모두 놓은 경우)
			if(index==n) {
				// nqueen문제를 풀 수 있게 퀸을 올바르게 놓은 경우
				if(pollState.isPossible()) {
					// 시간 측정 종료
					long afterTime = System.currentTimeMillis();
					// 반환할 결과값을 들고있는 인스턴스.
					Result result = new Result();
					result.queens = pollState.getState();
					result.time = (afterTime-beforeTime)/1000.0;
					return result;
				}
			}
			// 끝까지 도달하지 않은 경우 (놓을 퀸이 아직 남은 경우) (expand)
			else {
				for (int i = 0; i < n; i++) {
					// 큐에서 뺀 state 복사해서 새로운 state로 만듬.
					State newState = new State(n);
					for (int j = 0; j < index; j++) {
						newState.setState(pollState.getState()[j]);
					}
					// 새로운 state를 바탕으로 다음 경우들 모두 큐에 넣기.
					newState.setState(i);
					queue.offer(newState);
				}
			}
		}
		// bfs에서 solution을 찾지 못한 경우.
		return null;
	}
	
	// dfs는 스택을 이용해서 구현했습니다.
	public Result dfs() {
		// 함수 실행 시간 측정을 위해서. 측정 시작.
		long beforeTime = System.currentTimeMillis();
		stack.clear();
		
		// rootnode 큐에 넣기.
		State tempState = new State(n);
		stack.push(tempState);
		
		while(!stack.isEmpty()) {
			// 스택에서 하나를 뺀다.
			State popState = stack.pop();
			int index = popState.getIndex();
			// 끝까지 도달한 경우 (퀸을 모두 놓은 경우)
			if (index == n) {
				// nqueen문제를 풀 수 있게 퀸을 올바르게 놓은 경우.
				if(popState.isPossible()) {
					// 시간 측정 종료
					long afterTime = System.currentTimeMillis();
					// 결과값을 가지고 있는 인스턴스
					Result result = new Result();
					result.queens = popState.getState();
					result.time = (afterTime-beforeTime)/1000.0;
					return result;
				}
			}
			// 끝까지 도달하지 않은 경우 (놓을 퀸이 아직 남은 경우) (expand)
			else {
				for (int i = 0; i < n; i++) {
					// 큐에서 뺀 state 복사해서 새로운 state로 만듬.
					State newState = new State(n);
					for (int j = 0; j < index; j++) {
						newState.setState(popState.getState()[j]);
					}
					// 새로운 state를 바탕으로 다음 경우들 모두 큐에 넣기.
					newState.setState(i);
					stack.push(newState);
				}
			}
		}
		// dfs에서 solution을 찾지 못한 경우.
		return null;
	}
	
	// dfid는 스택을 이용해서 구현했습니다.
	public Result dfid() {
		// 함수 실행 시간 측정을 위해서. 측정 시작.
		long beforeTime = System.currentTimeMillis();
		
		// depth 설정을 통해서 depth가 0~n-1까지 증가하면서 탐색.
		for (int depth = 0; depth < n ; depth++) {
			stack.clear();
			// rootnode 큐에 넣기.
			State root = new State(n);
			stack.push(root);
			
			while(!stack.isEmpty()) {
				// 스택에서 맨 앞 state 출력 
				State tempState = stack.pop();
				int index = tempState.getIndex();
				
				// index = 퀸을 놓은 개수, depth+1까지 queen을 놓았을 때를 판별. (depth 설정)
				if (index == depth+1) {
					// n개의 퀸을 배치했고, nqueen문제를 해결할 수 있게 퀸을 올바르게 놓은 경우 (정답) 
					if(index == n && tempState.isPossible()) {
						// 시간 측정 종료.
						long afterTime = System.currentTimeMillis();
						// 결과값 반환을 위한 인스턴스.
						Result result = new Result();
						result.queens = tempState.getState();
						result.time = (afterTime-beforeTime)/1000.0;
						return result;
					}
				}
				// 퀸을 depth까지 놓지 못한 경우 depth까지 퀸 놓기. (expand)
				else {
					for (int i = 0; i < n; i++) {
						// 큐에서 뺀 state 복사해서 새로운 state로 만듬.
						State newState = new State(n);
						for (int j = 0; j < index; j++) {
							newState.setState(tempState.getState()[j]);
						}
						// 새로운 state를 바탕으로 다음 경우들 모두 큐에 넣기.
						newState.setState(i);
						stack.push(newState);
					}
				}
			}
		}
		return null;
	}
}