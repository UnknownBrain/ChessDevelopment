dentroTableroTorre(CL, I, J) :- I >= 0, I =< 7, J >= 0, J =< 7, \+pieza(CL, I, J).

diferenteTorre(X,Y,X1,Y1):- X \= X1;Y \= Y1.

torre(_, _, AX, MX, AY, MY) :- AX = MX, AY = MY.
torre(CL, C, AX, MX, AY, MY) :-
    diferenteTorre(AX, AY, MX, MY),
    C = 1, X is AX + 1, Y is AY,
    dentroTableroTorre(CL, X, Y),
    torre(CL, C, X, MX, Y, MY).
torre(CL, C, AX, MX, AY, MY) :-
    diferenteTorre(AX, AY, MX, MY),
    C = 2, X is AX, Y is AY + 1,
    dentroTableroTorre(CL, X, Y),
    torre(CL, C, X, MX, Y, MY).
torre(CL, C, AX, MX, AY, MY) :-
    diferenteTorre(AX, AY, MX, MY),
    C = 3, X is AX - 1, Y is AY,
    dentroTableroTorre(CL, X, Y),
    torre(CL, C, X, MX, Y, MY).
torre(CL, C, AX, MX, AY, MY) :-
    diferenteTorre(AX, AY, MX, MY),
    C = 4, X is AX, Y is AY - 1,
    dentroTableroTorre(CL, X, Y),
    torre(CL, C, X, MX, Y, MY).
