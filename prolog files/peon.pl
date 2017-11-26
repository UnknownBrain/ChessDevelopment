[tablero].

dxyc(0,1,1).
dxyc(0,1,-1).
dxy(0,1,0).
dxyi(0,2,0).
dxycb(1,-1,1).
dxycb(1,-1,-1).
dxyb(1,-1,0).
dxyib(1,-2,0).


correcto(X):-
    0 =< X,
    X =< 7.

diferente(X,Y,X1,Y1):- X \= X1;Y \= Y1.

aliado(pieza(C,X,Y),pieza(C2,X1,Y1)):- C == C2,pieza(C,X,Y),pieza(C2,X1,Y1).

pmov(true).

ocupado(X,Y):- pieza(_,X,Y).

salta_n(C,M,X,Y,X1,Y1):-
    diferente(X,Y,X1,Y1),
    (pmov(M) -> (not(ocupado(X1,Y1)),(dxy(C,Dx,Dy);S is X - 1,(not(ocupado(S,Y)),dxyi(C,Dx,Dy)));(ocupado(X1,Y1),dxyc(C,Dx,Dy))) ;
    ((not(ocupado(X1,Y1)),dxy(C,Dx,Dy));(ocupado(X1,Y1),dxyc(C,Dx,Dy)))),
    X1 is X+Dx,
    correcto(X1),
    Y1 is Y+Dy,
    correcto(Y1),
    not(aliado(pieza(C,X,Y),pieza(C,X1,Y1))).

salta_b(C,M,X,Y,X1,Y1):-
    diferente(X,Y,X1,Y1),
    (pmov(M) -> (not(ocupado(X1,Y1)),(dxyb(C,Dx,Dy);S is X - 1,(not(ocupado(S,Y)),dxyib(C,Dx,Dy)));(ocupado(X1,Y1),dxycb(C,Dx,Dy))) ;
    ((not(ocupado(X1,Y1)),dxyb(C,Dx,Dy));(ocupado(X1,Y1),dxycb(C,Dx,Dy)))),
    X1 is X+Dx,
    correcto(X1),
    Y1 is Y+Dy,
    correcto(Y1),
    not(aliado(pieza(C,X,Y),pieza(C,X1,Y1))).



