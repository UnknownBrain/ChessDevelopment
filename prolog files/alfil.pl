dentroTablero(Color, I, J) :- I >= 0, I =< 7, J >= 0, J =< 7, \+pieza(Color,X,Y).

alfil(_,_, AX, MX, AY, MY) :- AX = MX, AY = MY.
alfil(Color,C, AX, MX, AY, MY) :- C = 1, X is AX - 1, Y is AY - 1, dentroTablero(Color,X, Y), alfil(Color,C, X, MX, Y, MY).
alfil(Color,C, AX, MX, AY, MY) :- C = 2, X is AX + 1, Y is AY + 1, dentroTablero(Color,X, Y), alfil(Color,C, X, MX, Y, MY).
alfil(Color,C, AX, MX, AY, MY) :- C = 3, X is AX - 1, Y is AY + 1, dentroTablero(Color,X, Y), alfil(Color,C, X, MX, Y, MY).
alfil(Color,C, AX, MX, AY, MY) :- C = 4, X is AX + 1, Y is AY - 1, dentroTablero(Color,X, Y), alfil(Color,C, X, MX, Y, MY).
