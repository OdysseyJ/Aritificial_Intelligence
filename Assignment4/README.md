Artificial Intelligence
======
Programming Assignment4
------
###### professor. 백은옥</br>
###### {+ NQueens+} 구현하기 - {+ propositional logic +}


**1. 개요**


* 이번 과제는 NQueens문제를 수업시간에 배운 propositional logic 문제를 다루는 SAT solver z3를 활용하여 푸는 문제이다.</br>
* propositional logic : 논리 연산들의 결합을 활용해 다양한 명제논리를 해결하는 방법


**2. 과제 설명**
* __N-Queens문제란?__
 * N*N의 체스판이 있다.
 * N개의 Queen이 서로 공격하지 않아야 한다. (Queen은 가로, 세로, 대각선에 있는 말들을 모두 공격가능)
 * N=7일 때의 답 예시.


> 6 4 2 0 5 3 1

* __N-Queens문제 해결__
 * SAT solver z3를 활용하여 N-Queens를 효율적으로 풀어본다.

* __주의할 점__
 * 본인 컴퓨터에서 N=20을 기준으로 nQueens native보다 X배 빠른만큼 구현 점수에 X점을 부여한다.
 * 예를 들어, 30배 빨라지면 구현점수 30점, 100배 이상 빨라지면 구현 점수를 모두 받는다.

* __입력 형식__
 * console에 특정 N값을 입력한다.


![스크린샷_2019-11-03_오후_12.04.11](/uploads/2322e1f5f02c453d576d4875e8666719/스크린샷_2019-11-03_오후_12.04.11.png)


* __출력 형식__
 * 1차원 배열로 queen의 위치를 표시하고 위치는 1부터 N으로 표시
 * 마지막에 elapsed time을 적어준다.

![스크린샷_2019-11-04_오후_2.39.11](/uploads/2decf9716a96a645c34a5d4a8b21e31f/스크린샷_2019-11-04_오후_2.39.11.png)

**3. 사용언어 / 환경**


* {+ Python3/MAC +}


---
**[실행 결과]**

{+ nQueens.py +}

![스크린샷_2019-11-04_오후_2.39.11](/uploads/670e90a64fb602f3e2670f8fff1b0c7c/스크린샷_2019-11-04_오후_2.39.11.png)

{+ nQueensnative.py +}

![스크린샷_2019-11-03_오후_12.04.11](/uploads/c8578ccfb528e882c1a986d4df3db0fd/스크린샷_2019-11-03_오후_12.04.11.png)


![스크린샷_2019-11-03_오후_12.04.15](/uploads/120ffcbb411859b5013048d844132ae7/스크린샷_2019-11-03_오후_12.04.15.png)

**[결과에 대한 분석]**


내가 계산한 결과가 맞는지에 대한 분석은 Assignment1 과제의 nqueen 만족  여부를 구하는 function을 이용했다.


![스크린샷_2019-11-04_오후_3.05.44](/uploads/f642254928cd4ddab9e72afdf76f8ba7/스크린샷_2019-11-04_오후_3.05.44.png)


![스크린샷_2019-11-04_오후_3.05.03](/uploads/b8769adc6e107fe6962ed43b73713990/스크린샷_2019-11-04_오후_3.05.03.png)


내가 만든 코드는 n=20에 대해 0.18초정도만에 계산이 가능하지만,


nQueensnative는 n=20에대해 314초가 걸렸으므로, n=20에서 5000배정도 성능이 더 좋다.


__Optimization방법__ : 이중 배열에 대해 queen을 정의하면 모델을 구하는 제약 조건이 굉장히 많아질거라고 생각해서 queen배열을 *일차원 배열*로 바꾸었다. 또한 *1~N까지의 모든 숫자가 나타나야 하고*, *겹치지 않아야 하며*, *대각선에 차가 1인 수가 놓이면 안되게끔* 최적화했다. 대각선에 차가 1인수가 놓이면 안된다는 constraint를 만들 때, 1칸 떨어진 곳에는 1차이가 나면 안되고, 2칸 떨어진 곳에는 2차이가 나면 안되고, 3칸 떨어진 곳에는 3차이가 나면 안되고 이런식으로 했다.


__N에따른성능증가__ : nQueensnative는 n이 낮은 숫자일때는 비슷한 성능을 보이지만, n이 증가함에 따라 성능이 엄청나게 감소했다. 그에 비해 내가 구현한 nQueens는 n=100에서도 실행이 될만큼 증가 폭이 많이 크지 않았다. 아마 1차원 배열로 선언했고, 중복을 제거했기 때문에 그런듯 하다.

[n==5]
![스크린샷_2019-11-04_오전_1.30.05](/uploads/99497f44fb5b467ceb2607f8a2a32bc9/스크린샷_2019-11-04_오전_1.30.05.png)

[n==10]
![스크린샷_2019-11-04_오전_1.30.30](/uploads/4e4a9d490f3e6929dcbd92346a225cde/스크린샷_2019-11-04_오전_1.30.30.png)

[n==15]
![스크린샷_2019-11-04_오전_1.31.17](/uploads/9469aa15bc41e7599920412f52199cd9/스크린샷_2019-11-04_오전_1.31.17.png)

[n==100]
![스크린샷_2019-11-04_오후_3.02.39](/uploads/ec87183e2f9319bd719c2eeb9c643914/스크린샷_2019-11-04_오후_3.02.39.png)


**[전체 소스코드]**


{- nQueens.py -}

```python
from z3 import *
import time

# Number of Queens
print("N: ")
N = int(input())

start = time.time()
# Variables
# 1줄에 하나만 놓이게끔 배열을 만듬.
X = [Int("c_%s" % (row)) for row in range(N)]

# Constraints

# 같은 숫자가 나타나지 않도록 중복을 허용하지 않는 constraint
nosameConst = [X[row1] != X[row2] for row1 in range(N)
               for row2 in range(row1+1, N)]

# 1~N까지의 숫자 이외에는 나타나지 않게끔 하는 constraint
innumberConst = [Or([X[row] == ("%s" % num)
                     for num in range(1, N+1)]) for row in range(N)]

# 대각선상에 퀸이 놓이지 않게 하는 constraint
# X[row]를 기준으로
# 1칸 떨어진곳에 +1, -1가 나타나지 않고
# 2칸 떨어진곳에 +2, -2가 나타나지 않게끔
nodiagonalConst = [And(X[row] != X[row+col]+col, X[row] != X[row+col]-col)
                   for row in range(N-1) for col in range(1, N-row)]

constraints = nosameConst + innumberConst + nodiagonalConst

s = Solver()
s.add(constraints)

if s.check() == sat:
    m = s.model()
    r = [m.evaluate(X[j]) for j in range(N)]
    print(r)

print("elapsed time: ", time.time() - start, " sec")
```

nosameConst = 같은 숫자가 나오지 않게 하는 constraint


innumberConst = 1~N까지의 숫자만 나타나게끔 하는 constraint


nodiagonalConst = 대각선에 퀸이 놓이지 않게 하는 constraint


최종 constraint = nosameConst + innumberConst + nodiagonalConst들의 합.
