menormayor(A, B):-  A  > B; A < B.

torre_1(A, B, C, D):- menormayor(A,B),  C = D.
torre_2(A, B, C, D):- menormayor(C,D), A = B.
torre(A, B, C, D):- torre_1(A, B, C, D); torre_2(A, B, C, D).

masmenos1(A, B):- X is B + 1, A = X; X is B - 1, A = X.
masmenos2(A, B):- X is B + 2, A = X; X is B - 2, A = X.

caballo_1(A, B, C, D):- masmenos2(A, B), masmenos1(C, D).
caballo_2(A, B, C, D):- masmenos1(A, B), masmenos2(C, D).
caballo(A, B, C, D):- caballo_1(A, B, C, D); caballo_2(A, B, C, D).

rey(A, B, C, D):- masmenos1(A,B); masmenos1(C, D).

peon(A, B, C, D):- B < A, masmenos1(D,C).