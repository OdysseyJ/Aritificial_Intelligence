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
# X[row]를 기준으로 뒤쪽은 보지 않음.
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
