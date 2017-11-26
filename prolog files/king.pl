:- [tablero].

dxy(1,0).
dxy(-1,0).
dxy(0,1).
dxy(0,-1).
dxy(1,1).
dxy(1,-1).
dxy(-1,1).
dxy(-1,-1).

libre(C, X, Y) :-
	\+pieza(C, X, Y).

mover([X,Y], [X1, Y1]) :-
	dxy(Dx, Dy),
	X1 is X+Dx,
	correcto(X1),
	Y1 is Y+Dy,
	correcto(Y1),
	libre(0, X1, Y1).

correcto(X) :-
	0 =< X,
	X =< 7.
	
noIguales(X1, X2, Y1, Y2):-
	X1 \= X2;
	Y1 \= Y2.

correcto(A, B):-
	A = B;
	X is B + 1,
	A = X;
	X is B - 1,
	A = X.
	
mover(X1, Y1, X2, Y2):- 
	noIguales(X1, X2, Y1, Y2),
	correcto(X1, X2),
	correcto(Y1, Y2),
	libre(1, X2, Y2).
	

