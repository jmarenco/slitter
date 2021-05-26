
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
var x[C*NC*I*NI] binary;
var z[NC*NI*NI] binary;

# Funcion objetivo
maximize fobj: sum <j,p,pp> in NC*NI*NI: z[j,p,pp];

# Consistencia de las variables x
subto consist: forall <c,j,i,ip> in C*NC*I*I with i <= items[c] and ip <= items[c]:
    sum <p> in NI with p <= items[c]: (x[c,j,i,p] - x[c,j,ip,p]) == 0;
    
# Asignacion de combinaciones a posiciones
subto combpos: forall <c> in C:
    sum <j,p> in NC*NI: x[c,j,1,p] == 1;
    
subto poscomb: forall <j> in NC:
    sum <c,p> in C*NI: x[c,j,1,p] == 1;

# Asignacion de items a posiciones
subto itempos: forall <c,i> in C*I with i <= items[c]:
    sum <j,p> in NC*NI with p <= items[c]: x[c,j,i,p] == 1;

subto positem: forall <c,p> in C*NI with p <= items[c]:
    sum <j,i> in NC*I with i <= items[c]: x[c,j,i,p] == 1;

# Definicion de las variables z
subto defzgt: forall <j,p,pp> in NC*NI*NI with j > 1:
    sum <c,i,r> in C*I*NI with i <= items[c] and r <= p: a[c,i] * x[c,j,i,r] <=
    sum <c,i,r> in C*I*NI with i <= items[c] and r <= pp: a[c,i] * x[c,j-1,i,r] + ancho * (1 - z[j,p,pp]);
    
subto defzlt: forall <j,p,pp> in NC*NI*NI with j > 1:
    sum <c,i,r> in C*I*NI with i <= items[c] and r <= p: a[c,i] * x[c,j,i,r] >=
    sum <c,i,r> in C*I*NI with i <= items[c] and r <= pp: a[c,i] * x[c,j-1,i,r] - ancho * (1 - z[j,p,pp]);

# Variables z compatibles con las variables x
subto compatzx: forall <c,j,p> in C*NC*NI with p > items[c]:
    sum <pp> in NI: z[j,p,pp] <= 1 - sum <r> in NI: x[c,j,1,r];

subto compatzxm1: forall <cp,j,pp> in C*NC*NI with pp > items[cp] and j > 1:
    sum <p> in NI: z[j,p,pp] <= 1 - sum <r> in NI: x[cp,j-1,1,r];

# Variables que no tienen sentido
subto nullx: forall <c,j,i,p> in C*NC*I*NI with i > items[c] or p > items[c]:
    x[c,j,i,p] == 0;

subto nullz1: forall <p,pp> in NI*NI:
    z[1,p,pp] == 0;
    
