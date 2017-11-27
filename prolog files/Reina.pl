[tablero].

correcto(Color,X, Y) :- 0 =< X, X =< 7,
                  0 =< Y, Y =< 7,
                  \+pieza(Color,X,Y).



queen(_,_, Ix, Iy, Fx, Fy) :-
    Ix = Fx,
    Iy = Fy.

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 1,
    X is Ix - 1,
    Y is Iy - 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 2,
    X is Ix + 1,
    Y is Iy + 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 3,
    X is Ix + 1,
    Y is Iy - 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 4,
    X is Ix - 1,
    Y is Iy + 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 5,
    X is Ix + 1,
    Y is Iy ,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 6,
    X is Ix ,
    Y is Iy + 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 7,
    X is Ix - 1,
    Y is Iy ,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).

queen(Color,C, Ix, Iy, Fx, Fy) :-
    C = 8,
    X is Ix ,
    Y is Iy - 1,
    correcto(Color,X, Y),
    queen(Color,C, X, Y, Fx, Fy).




