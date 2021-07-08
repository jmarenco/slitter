
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
           
# Combinaciones/items con la combinacion/item 0 
set C0 := C union { 0 };
set I0 := I union { 0 };

# Cantidad de items y trim de cada combinacion
param items[<c> in C] := sum <i> in I with a[c,i] > 0: 1;
param trim[<c> in C] := sum <i> in I: a[c,i];

# Ancho del papel
param ancho := max <c> in C: sum <i> in I: a[c,i];

# Pares de items que pueden coincidir
set posibles := { read archivo + ".pos" as "<1n,2n,3n,4n>" };
set posiblessinultimo := { read archivo + ".pol" as "<1n,2n,3n,4n>" };

# Manor y mayor item de cada combinacion
defnumb mayor(c) := a[c,items[c]];
defnumb menor(c) := a[c,1];

# Variables
var x[C0*C0] binary;
var y[C*I0*I0] binary;
var p[C] >= 1 <= card(C);
var s[C*I0] >= 0;
var z[C*I*I] binary;

# Funcion objetivo
maximize fobj: sum <c,i,j> in C*I*I: z[c,i,j];

# MTZ para combinaciones
subto incomb: forall <c> in C0:
    sum <cp> in C0 with c != cp: x[cp,c] == 1;
    
subto outcomb: forall <c> in C0:
    sum <cp> in C0 with c != cp: x[c,cp] == 1;
    
subto ligarcomb: forall <i,j> in C*C with i != j:
    p[j] >= p[i] + 1 - card(C) * (1 - x[i,j]) + (card(C)-2) * x[j,i];
    
subto acotarcombinf: forall <j> in C:
    p[j] >= 1 + (1 - x[0,j]) + (card(C)-2) * x[j,0];
    
subto acotarcombsup: forall <j> in C:
    p[j] <= card(C) - (card(C)-2) * x[0,j] - (1 - x[j,0]);

# MTZ para items
subto initem: forall <c,k> in C*I0 with k <= items[c]:
    sum <l> in I0 with k != l: y[c,k,l] == 1;
    
subto outitem: forall <c,k> in C*I0 with k <= items[c]:
    sum <l> in I0 with k != l: y[c,l,k] == 1;

subto ligaritem: forall <c,i,j> in C*I*I with i <= items[c] and j <= items[c] and i != j:
    s[c,j] >= s[c,i] + a[c,j] - trim[c] * (1 - y[c,i,j]) + (trim[c] - a[c,j] - a[c,i]) * y[c,j,i];
    
subto cotainfitem: forall <c,j> in C*I with j <= items[c]:
    s[c,j] >= a[c,j] + menor(c) * (1 - y[c,0,j]) + (trim[c] - a[c,j] - menor(c)) * y[c,j,0];

subto cotasupitem: forall <c,j> in C*I with j <= items[c]:
    s[c,j] <= trim[c] - (trim[c] - menor(c) - a[c,j]) * y[c,0,j] - menor(c) * (1 - y[c,j,0]);
    
# Las combinaciones empiezan en cero
subto empezarcero: forall <c> in C:
    s[c,0] == 0;
    
# Define las variables z
subto defzlt: forall <c,cp,i,ip> in C*C*I*I with i <= items[c] and ip <= items[cp]:
    s[c,i] - s[cp,ip] <= ancho * (2 - x[c,cp] - z[c,i,ip]);

subto defzgt: forall <c,cp,i,ip> in C*C*I*I with i <= items[c] and ip <= items[cp]:
    s[cp,ip] - s[c,i] <= ancho * (2 - x[c,cp] - z[c,i,ip]);

# Compatibilidad entre z y x
subto compatzx: forall <c,cp,i,j> in C*C*I*I with j > items[cp]:
    z[c,i,j] <= 1 - x[c,cp];
    
subto lastx: forall <c,i,j> in C*I*I:
    z[c,i,j] <= 1 - x[c,0];

# Variables que no tienen sentido
subto nullx: forall <c> in C0:
    x[c,c] == 0;

subto nully: forall <c,i> in C*I:
    y[c,i,i] == 0;

subto nullyexc: forall <c,k,l> in C*I*I with k > items[c] or l > items[c]:
    y[c,k,l] == 0;

subto nullz: forall <c,i,j> in C*I*I with i > items[c]:
    z[c,i,j] == 0;

# Refuerzo
subto ref1: forall <c,i> in C*I:
    sum <ip> in I: z[c,i,ip] <= 1;

subto ref2: forall <c,i> in C*I:
    sum <ip> in I: z[c,ip,i] <= 1;
    
subto ref3: forall <c,i,j> in C*I*I with i <= items[c]:
    z[c,i,j] <= sum <cp> in C with <c-1,cp-1,i-1,j-1> in posibles: x[c,cp];

subto ref4: forall <c,i,j> in C*I*I with i <= items[c]:
    z[c,i,j] <= y[c,i,0] + sum <cp> in C with <c-1,cp-1,i-1,j-1> in posiblessinultimo: x[c,cp];

subto ref5: forall <c,i,j> in C*I*I with i <= items[c]:
    z[c,i,j] <= y[c,i,0] + sum <cp> in C with <c-1,cp-1,i-1,j-1> in posiblessinultimo: x[c,cp];

subto ref6: forall <i,j> in C0*C0 with i != j:
    x[i,j] + x[j,i] <= 1;

subto ref7: forall <i,j,k> in C0*C0*C0 with i != j and i != k and j != k:
    x[i,j] + x[j,k] + x[k,i] <= 2;

subto ref8: forall <c,i,j> in C*I0*I0 with i != j and i <= items[c] and j <= items[c]:
    y[c,i,j] + y[c,j,i] <= 1;

subto ref9: forall <c,i,j,k> in C*I0*I0*I0 with items[c] > 2 and i != j and i != k and j != k
                                           and i <= items[c] and j <= items[c] and k <= items[c]:
    y[c,i,j] + y[c,j,k] + y[c,k,i] <= 2;

