renombrar(C, X1, Y1, X2, Y2) :-
	retract(pieza(C, X1, Y1)),
	assertz(pieza(C, X2, Y2)).