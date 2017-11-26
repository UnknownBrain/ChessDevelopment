menormayor(A, B):-  A  > B; A < B.

torre_1(A, B, C, D):- menormayor(A,B),  C = D.
torre_2(A, B, C, D):- menormayor(C,D),  A = B.
torre(A, B, C, D):- torre_1(A, B, C, D); torre_2(A, B, C, D).

masmenos1(A, B):- X is B + 1, A = X; X is B - 1, A = X.
masmenos2(A, B):- X is B + 2, A = X; X is B - 2, A = X.

caballo_1(A, B, C, D):- masmenos2(A, B), masmenos1(C, D).
caballo_2(A, B, C, D):- masmenos1(A, B), masmenos2(C, D).
caballo(A, B, C, D):- caballo_1(A, B, C, D); caballo_2(A, B, C, D).

noIgual(X1, X2, Y1, Y2):-
	X1 \= X2;
	Y1 \= Y2.

correcto(A, B):-
	A = B;
	X is B + 1,
	A = X;
	X is B - 1,
	A = X.

libre(X, Y) :-
	\+pieza(1, X, Y).

rey(X1, X2, Y1, Y2):- 
	noIgual(X1, X2, Y1, Y2),
	correcto(X1, X2),
	correcto(Y1, Y2),
	libre(X2, Y2).

peon(FM, A, B, _, _):- B < A, C is A - B, C =< FM.

dentroTablero(I, J) :- I >= 0, I =< 7, J >= 0, J =< 7.

alfil(_, AX, MX, AY, MY) :- AX = MX, AY = MY.
alfil(C, AX, MX, AY, MY) :- C = 1, X is AX - 1, Y is AY - 1, dentroTablero(X, Y), alfil(C, X, MX, Y, MY).
alfil(C, AX, MX, AY, MY) :- C = 2, X is AX + 1, Y is AY + 1, dentroTablero(X, Y), alfil(C, X, MX, Y, MY).
alfil(C, AX, MX, AY, MY) :- C = 3, X is AX - 1, Y is AY + 1, dentroTablero(X, Y), alfil(C, X, MX, Y, MY).
alfil(C, AX, MX, AY, MY) :- C = 4, X is AX + 1, Y is AY - 1, dentroTablero(X, Y), alfil(C, X, MX, Y, MY).