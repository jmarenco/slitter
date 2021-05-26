
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

# Variables
var x[C0*C0] binary;
var y[C*I0*I0] binary;
var p[C] >= 1 <= card(C);
var s[C*I0] >= 0;
var z[C*I0*C*I0] binary;

# Funcion objetivo
maximize fobj: sum <c,i,cp,ip> in C*I0*C*I0: (1 - z[c,i,cp,ip]) - (card(C) - 1);
    
# MTZ para combinaciones
subto incomb: forall <c> in C0:
    sum <cp> in C0 with c != cp: x[cp,c] == 1;
    
subto outcomb: forall <c> in C0:
    sum <cp> in C0 with c != cp: x[c,cp] == 1;
    
subto ligarcomb: forall <c,cp> in C*C with c != cp:
    p[c] + 1 <= p[cp] + card(C) * (1 - x[c,cp]);
    
# MTZ para items
subto initem: forall <c,k> in C*I0 with k <= items[c]:
    sum <l> in I0 with k != l: y[c,k,l] == 1;
    
subto outitem: forall <c,k> in C*I0 with k <= items[c]:
    sum <l> in I0 with k != l: y[c,l,k] == 1;

subto ligaritem: forall <c,k,l> in C*I*I0 with k <= items[c] and l <= items[c]:
    s[c,k] + a[c,k] <= s[c,l] + ancho * (1 - y[c,k,l]);
    
subto cotaitem: forall <c,k> in C*I0:
    s[c,k] <= trim[c];
    
# Las combinaciones empiezan en cero
subto empezarcero: forall <c,k> in C*I:
    s[c,k] <= ancho * (1 - y[c,0,k]);
    
# Define las variables z
subto defzlt: forall <i,j,k,l> in C*C*I0*I0 with k <= items[i] and l <= items[j]:
    s[i,k] - s[j,l] + 1 - x[i,j] <= (ancho + 1) * z[i,k,j,l];

subto defzgt: forall <i,j,k,l> in C*C*I0*I0 with k <= items[i] and l <= items[j]:
    s[j,l] - s[i,k] + 1 - x[i,j] <= (ancho + 1) * z[i,k,j,l];

# Variables que no tienen sentido
subto nullx: forall <c> in C0:
    x[c,c] == 0;

subto nully: forall <c,i> in C*I0:
    y[c,i,i] == 0;

subto nullyexc: forall <c,k,l> in C*I0*I0 with k > items[c] or l > items[c]:
    y[c,k,l] == 0;

subto nullz: forall <i,k,j,l> in C*I0*C*I0 with i == j or k > items[i] or l > items[j]:
    z[i,k,j,l] == 1;
    
