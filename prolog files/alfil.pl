dentroTableroAlfil(CL, I, J) :- I >= 0, I =< 7, J >= 0, J =< 7, \+pieza(CL, I, J).

alfil(_, _, AX, MX, AY, MY) :- AX = MX, AY = MY.
alfil(CL, C, AX, MX, AY, MY) :- C = 1, X is AX - 1, Y is AY - 1, 
    dentroTableroAlfil(CL, X, Y), alfil(CL, C, X, MX, Y, MY).
alfil(CL, C, AX, MX, AY, MY) :- C = 2, X is AX + 1, Y is AY + 1, 
    dentroTableroAlfil(CL, X, Y), alfil(CL, C, X, MX, Y, MY).
alfil(CL, C, AX, MX, AY, MY) :- C = 3, X is AX - 1, Y is AY + 1, 
    dentroTableroAlfil(CL, X, Y), alfil(CL, C, X, MX, Y, MY).
alfil(CL, C, AX, MX, AY, MY) :- C = 4, X is AX + 1, Y is AY - 1, 
    dentroTableroAlfil(CL, X, Y), alfil(CL, C, X, MX, Y, MY).
