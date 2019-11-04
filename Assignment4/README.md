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

<img width="55" alt="스크린샷 2019-11-03 오후 12 04 11" src="https://user-images.githubusercontent.com/23691933/68103132-f10d7600-ff18-11e9-9a33-d8da89d4669e.png">


* __출력 형식__
 * 1차원 배열로 queen의 위치를 표시하고 위치는 1부터 N으로 표시
 * 마지막에 elapsed time을 적어준다.
 
<img width="514" alt="스크린샷 2019-11-04 오후 2 39 11" src="https://user-images.githubusercontent.com/23691933/68103138-f23ea300-ff18-11e9-910a-4f8a7dc13717.png">

**3. 사용언어 / 환경**


* {+ Python3/MAC +}


---
**[실행 결과]**

{+ nQueens.py +}

<img width="514" alt="스크린샷 2019-11-04 오후 2 39 11" src="https://user-images.githubusercontent.com/23691933/68103138-f23ea300-ff18-11e9-910a-4f8a7dc13717.png">

{+ nQueensnative.py +}

<img width="55" alt="스크린샷 2019-11-03 오후 12 04 11" src="https://user-images.githubusercontent.com/23691933/68103132-f10d7600-ff18-11e9-9a33-d8da89d4669e.png">


<img width="302" alt="스크린샷 2019-11-03 오후 12 04 15" src="https://user-images.githubusercontent.com/23691933/68103133-f10d7600-ff18-11e9-9f44-b4c78da21996.png">

**[결과에 대한 분석]**


내가 계산한 결과가 맞는지에 대한 분석은 Assignment1 과제의 nqueen 만족  여부를 구하는 function을 이용했다.

<img width="565" alt="스크린샷 2019-11-04 오후 3 05 44" src="https://user-images.githubusercontent.com/23691933/68103141-f2d73980-ff18-11e9-9d68-26729a18d4df.png">


<img width="454" alt="스크린샷 2019-11-04 오후 3 05 03" src="https://user-images.githubusercontent.com/23691933/68103140-f2d73980-ff18-11e9-9672-ca17d0edfbe3.png">


내가 만든 코드는 n=20에 대해 0.18초정도만에 계산이 가능하지만,


nQueensnative는 n=20에대해 314초가 걸렸으므로, n=20에서 5000배정도 성능이 더 좋다.


__Optimization방법__ : 이중 배열에 대해 queen을 정의하면 모델을 구하는 제약 조건이 굉장히 많아질거라고 생각해서 queen배열을 *일차원 배열*로 바꾸었다. 또한 *1~N까지의 모든 숫자가 나타나야 하고*, *겹치지 않아야 하며*, *대각선에 차가 1인 수가 놓이면 안되게끔* 최적화했다. 대각선에 차가 1인수가 놓이면 안된다는 constraint를 만들 때, 1칸 떨어진 곳에는 1차이가 나면 안되고, 2칸 떨어진 곳에는 2차이가 나면 안되고, 3칸 떨어진 곳에는 3차이가 나면 안되고 이런식으로 했다.


__N에따른성능증가__ : nQueensnative는 n이 낮은 숫자일때는 비슷한 성능을 보이지만, n이 증가함에 따라 성능이 엄청나게 감소했다. 그에 비해 내가 구현한 nQueens는 n=100에서도 실행이 될만큼 증가 폭이 많이 크지 않았다. 아마 1차원 배열로 선언했고, 중복을 제거했기 때문에 그런듯 하다.

[n==5]
<img width="390" alt="스크린샷 2019-11-04 오전 1 30 05" src="https://user-images.githubusercontent.com/23691933/68103135-f1a60c80-ff18-11e9-859d-aa567ad46bfb.png">

[n==10]
<img width="385" alt="스크린샷 2019-11-04 오전 1 30 30" src="https://user-images.githubusercontent.com/23691933/68103136-f1a60c80-ff18-11e9-83bd-5dbe9e7cec8e.png">

[n==15]
<img width="407" alt="스크린샷 2019-11-04 오전 1 31 17" src="https://user-images.githubusercontent.com/23691933/68103137-f23ea300-ff18-11e9-80cb-33baa7ece6a1.png">

[n==100]
<img width="555" alt="스크린샷 2019-11-04 오후 3 02 39" src="https://user-images.githubusercontent.com/23691933/68103139-f23ea300-ff18-11e9-8508-389f3416abf6.png">


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
