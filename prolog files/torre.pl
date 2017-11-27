dentroTablero(I, J) :- I >= 0, I =< 7, J >= 0, J =< 7.

torre(_, AX, MX, AY, MY) :- AX = MX, AY = MY.
torre(C, AX, MX, AY, MY) :- C = 1, X is AX + 1, Y is AY, dentroTablero(X, Y), torre(C, X, MX, Y, MY).
torre(C, AX, MX, AY, MY) :- C = 2, X is AX, Y is AY + 1, dentroTablero(X, Y), torre(C, X, MX, Y, MY).
torre(C, AX, MX, AY, MY) :- C = 3, X is AX - 1, Y is AY, dentroTablero(X, Y), torre(C, X, MX, Y, MY).
torre(C, AX, MX, AY, MY) :- C = 4, X is AX, Y is AY - 1, dentroTablero(X, Y), torre(C, X, MX, Y, MY).
