noHayPieza(Pieza):- Pieza = 0.
hayPieza(Pieza, Color):- Pieza =\= 0, Color = 1.
hayPiezaW(Pieza, Color):- Pieza =\= 0, Color = 0.

comerIzqB(Xi, Xf, Yi, Yf, Pieza, Color):- 
			Y is Yi - 1, 
			Yf = Y, 
			X is Xi + 1, 
			Xf = X, 
			hayPieza(Pieza, Color).

comerDereB(Xi, Xf, Yi, Yf, Pieza, Color):-
			Y is Yi + 1, 
			Yf = Y, 
			X is Xi + 1, 
			Xf = X,
			hayPieza(Pieza, Color).

comerB(Xi, Xf, Yi, Yf, Pieza, Color):-
			comerIzqB(Xi, Xf, Yi, Yf, Pieza, Color); 
			comerDereB(Xi, Xf, Yi, Yf, Pieza, Color).

moverB(Xi, Xf, Yi, Yf, Pieza):- 
			Yf = Yi, 
			X is Xi + 1, 
			Xf = X, 
			noHayPieza(Pieza).

black_peon(Xi, Xf, Yi, Yf, Pieza, Color):- 
			comerB(Xi, Xf, Yi, Yf, Pieza, Color); 
			moverB(Xi, Xf, Yi, Yf, Pieza).





comerIzqW(Xi, Xf, Yi, Yf, Pieza, Color):- 
			Y is Yi + 1, 
			Yf = Y, 
			X is Xi - 1, 
			Xf = X, 
			hayPiezaW(Pieza, Color).

comerDereW(Xi, Xf, Yi, Yf, Pieza, Color):-
			Y is Yi - 1, 
			Yf = Y, 
			X is Xi - 1, 
			Xf = X,
			hayPiezaW(Pieza, Color).

comerW(Xi, Xf, Yi, Yf, Pieza, Color):-
			comerIzqW(Xi, Xf, Yi, Yf, Pieza, Color); 
			comerDereW(Xi, Xf, Yi, Yf, Pieza, Color).

moverW(Xi, Xf, Yi, Yf, Pieza):- 
			Yf = Yi, 
			X is Xi - 1, 
			Xf = X, 
			noHayPieza(Pieza).

white_peon(Xi, Xf, Yi, Yf, Pieza, Color):- 
			comerW(Xi, Xf, Yi, Yf, Pieza, Color); 
			moverW(Xi, Xf, Yi, Yf, Pieza).