movx(A,B):- X is A + 1, X = B; X is A - 1, X = B.
movy(A,B):- Y is A + 2, Y = B; Y is A - 2, Y = B.
movxy(A,B,C,D):- movx(A,B), movy(C,D); movx(C,D), movy(A,B).


caballo(Color,XI,XF,YI,YF):- movxy(XI,XF,YI,YF), \+pieza(Color,XF,YF).


