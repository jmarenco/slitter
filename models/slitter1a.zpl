
# Cantidad de combinaciones e items por combinacion
param combinaciones := read archivo as "1n" use 1;
param elementos := read archivo as "2n" use 1;

# Combinaciones
set C := { 1 .. combinaciones };

# Items
set I := { 1 .. elementos };

# Posiciones de combinaciones e items
set NC := C;
set NI := I;

# Ancho de cada item
param a[C*I] := read archivo as "n+" skip 1;
            
# Cantidad de items de cada combinacion
param items[<c> in C] := sum <i> in I with a[c,i] > 0: 1;

# Ancho del papel
param ancho := max <c> in C: sum <i> in I: a[c,i];

# Variables
var x[C*NC] binary;
var y[C*I*NI] binary;
var z[C*NI*NI] binary;

# Funcion objetivo
maximize fobj: sum <c,p,pp> in C*NI*NI: z[c,p,pp];

# Asignacion de combinaciones a posiciones
subto combpos: forall <i> in C:
    sum <j> in NC: x[i,j] == 1;
    
subto poscomb: forall <j> in NC:
    sum <i> in C: x[i,j] == 1;

# Asignacion de items a posiciones
subto itempos: forall <c,i> in C*I with i <= items[c]:
    sum <p> in NI with p <= items[c]: y[c,i,p] == 1;

subto positem: forall <c,p> in C*NI with p <= items[c]:
    sum <i> in I with i <= items[c]: y[c,i,p] == 1;

# Definicion de las variables z
subto defzgt: forall <c,cp,j,p,pp> in C*C*NC*NI*NI with j > 1 and p <= items[c] and pp <= items[cp]:
    sum <i,r> in I*NI with i <= items[c] and r <= p: a[c,i] * y[c,i,r] <=
    sum <i,r> in I*NI with i <= items[cp] and r <= pp: a[cp,i] * y[cp,i,r] + ancho * (3 - x[cp,j-1] - x[c,j] - z[c,p,pp]);
    
subto defzlt: forall <c,cp,j,p,pp> in C*C*NC*NI*NI with j > 1 and p <= items[c] and pp <= items[cp]:
    sum <i,r> in I*NI with i <= items[c] and r <= p: a[c,i] * y[c,i,r] >=
    sum <i,r> in I*NI with i <= items[cp] and r <= pp: a[cp,i] * y[cp,i,r] - ancho * (3 - x[cp,j-1] - x[c,j] - z[c,p,pp]);

# Variables z compatibles con las variables x
subto compatzxm1: forall <c,cp,j,pp> in C*C*NC*NI with pp > items[cp] and j > 1:
    sum <p> in NI: z[c,p,pp] <= 2 - x[c,j] - x[cp,j-1];

# Variables que no tienen sentido
subto nullz: forall <c,p,pp> in C*NI*NI with p > items[c]:
    z[c,p,pp] == 0;

subto nully: forall <c,i,p> in C*I*NI with i > items[c] or p > items[c]:
    y[c,i,p] == 0;
    
subto nullz1: forall <c,p,pp> in C*NI*NI:
    z[c,p,pp] <= 1 - x[c,1];
    
# Refuerzo
# subto ref1: forall <c,p> in C*NI:
#     sum <pp> in NI: z[c,p,pp] <= 1;

# subto ref2: forall <c,p> in C*NI:
#     sum <pp> in NI: z[c,pp,p] <= 1;

# Suma de los r items mas anchos/menos anchos
# defnumb mayores(c,p) := sum <r> in I with r <= p: a[c,items[c]-r+1];
# defnumb menores(c,p) := sum <r> in I with r <= p: a[c,r];

# do forall <c,p> in C*NI with p <= items[c] do print c, " ", p, " ", mayores(c,p), " ", menores(c,p);

# Refuerzo
# subto ref3: forall <c,p,pp,j> in C*NI*NI*NC with j > 1 and p <= items[c]:
#     z[j,p,pp] <= (1 - x[c,j]) + sum <cp> in C with c != cp and mayores(c,p) >= menores(cp,pp): x[cp,j-1];
    

