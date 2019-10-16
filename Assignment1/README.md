Artificial Intelligence
======
Programming Assignment1
------

###### professor. 백은옥


###### NQueens 구현하기



**1. 개요** 


이번 과제는 **NQueens**문제를 DFS, BFS, DFID를 이용해서 해결하는 과제입니다.


* DFS : Depth First Search
* BFS : Breadth First Search
* DFID : Depth First Iterative Deepning


**2. 과제 설명**


* N-Queens문제란?


i. N*N의 체스판이 있다.


ii. N개의 Queen이 서로 공격하지 않아야 한다. (Queen은 가로, 세로, 대각선에 있는 말들을 모두 공격가능)


iii. N=8일 때의 답 예시.


> 6 4 2 0 5 3 1


* N-Queens문제 해결


DFS, BFS, DFID Search 방법을 각각 사용하여 해결. 이때 각각 search에서 첫번째로 찾은 답을 출력한다.


답을 출력할 때는 0~(N-1)까지의 Column에 있는 각 Queen의 row 위치를 출력한다.


* 주의할 점


Search를 할 때 Pruning(안되는 경우를 제외)하지 않고, 모두 expanding해 준다.


* 입력 형식


Argument형태로 주어집니다. 첫번째는 Argument로 N을 입력받고


두번째는 결과 출력 파일의 '절대경로'를 입력 받는다.


[example]


java -jar 7 /Users/macbook/Documents/Homework#1


* 출력 형식


출력 파일의 이름은 'resultN.txt'로 한다.  예를들어 N=5일때, 파일의 이름은 result5.txt이다.


답이 없다면 Location 대신 No Solution을 출력한다.


**3. 사용언어**


**java**


[2015004239_report.docx](/uploads/dc6297f73e2e9749231cbee45bae9f1d/2015004239_report.docx)


<실행 결과>
![image](/uploads/f941420453cc0d6f0865108b1015d49e/image.png)


![image](/uploads/e2873dfda48f2e7ef986fccc7e5d9cea/image.png)


![image](/uploads/2f89120de5c7e3feda54713c893a6be6/image.png)


![image](/uploads/671567e35057fd2b171942e80ba8d883/image.png)


[main.java]


![image](/uploads/a0c571c42e77101c989b8d62ad655850/image.png)


파일 입출력을 위해 File, FileWriter 클래스를 사용했습니다.


[Result.java]


![image](/uploads/48ff0d381e9b5ec91214a81ef049e5cf/image.png)


결과값과 걸린 시간을 함께 반환하게끔 했습니다.


[State.java]


![image](/uploads/5941dd62b0e5ed8b0ea4ee837db36b25/image.png)


멤버변수로 int[]타입의 queens를 가지고, index를 가집니다.
index = 퀸이 몇개 놓여있나?를 의미하고,
queens[i] = i번째 열에 놓여있는 퀸의 위치를 의미합니다.


![image](/uploads/5b3c24ff4d04d5ce1fe5cb1ba8c88226/image.png)


isPossible() 메서드는 현재 state가 가지는 queens 배열에 퀸이 올바르게 놓여 있는지를 판별하는 메서드입니다.
반복문을 통해 놓여있는 모든 퀸을 순차적으로 비교하며, queens[i] == queens[j]는 같은 줄에 퀸이 놓여 있는 경우를, Math.abs(j-i)(밑변) == Math.abs(queens[j]-queens[i]))(높이)가 같은 경우에는 대각선상에 위치하기 때문에 false를 반환하고,
이 두가지 경우가 아닌 경우에는 nqueens 조건에 만족하는 경우이므로 true를 반환하게 됩니다.


[Tree.java]


![image](/uploads/b1824e6dded5c869b2127f8ab6ace32a/image.png)


queue = bfs를 위한 queue입니다.
 stack = dfs, dfid를 위한 stack입니다.
 n = 입력받은 n을 저장해두는 멤버변수입니다.


![image](/uploads/3fdbf350eb92e57cf7d594777a09e3b4/image.png)


bfs()메서드는 실행 결과로 Result타입을 반환하는 메서드입니다.
1. 처음에 root를 queue에 넣고,
2. 큐가 빌때까지 큐에서 하나씩 꺼내면서 판별합니다.
만약, 현재 꺼낸 상태가 n개의 퀸을 모두 놓았다면 다시 한번 n queen을 만족하는지 판별하여
result를 반환합니다.
n개의 퀸을 모두 놓지 않았다면,
현재 state의 상태를 복사해서 새로운 state를 만든 뒤,
새로운 state에서 놓을 수 있는 queen 경우의 수를 모두 queue에 넣습니다.
만약 큐가 비었는데도 결과를 반환하지 않았다면, 그것은 모든 경우에 nqueen을 만족하는
경우가 없으므로, null을 반환하게 됩니다.


dfs 또한 비슷한 형식으로 구현했고, queue가 아닌 stack 을 사용해서 구현했습니다.


dfid 는 depth라는 변수를 통해서 index(퀸이 놓인 개수)와 비교해가면서 dfs를 실행하게끔 구현했습니다. 이때도 stack을 사용해서 구현했습니다.


![image](/uploads/4bda5a070b46a97f9b26ba82d61aca7d/image.png)
