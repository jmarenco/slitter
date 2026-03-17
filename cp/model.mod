using CP;

int n = 4;
int m = 3;

range C = 0..(n-1);
range I = 0..(m-1);

int width[C][I] = [
 [200, 200, 0],
 [100, 200, 300],
 [150, 250, 0],
 [250, 250, 0] ];

dvar int x[C] in C;
dvar int y[C][I] in I;
dvar int w[C][I];
dvar int cw[C][I];
dvar int cc[C][I];

maximize sum(i in C, j in I: i > 0 && width[i][j] > 0) cc[i][j];
 
subject to
{
  allDifferent(x);
  forall(i in C) allDifferent(all(j in I) y[i][j]);
  forall(i in C, j in I: width[i][j] <= 0) y[i][j] == j;
  forall(i in C, j in I) w[i][j] == width[x[i]][y[x[i]][j]];
  forall(i in C) cw[i][0] == w[i][0];
  forall(i in C, j in I: j > 0) cw[i][j] == (w[i][j] > 0 ? cw[i][j-1] + w[i][j] : 0);
  forall(i in C, j in I: i > 0) cc[i][j] == (cw[i][j] > 0 ? sum(jp in I)(cw[i][j] == cw[i-1][jp]) : 0);
}

execute
{
  write("x = ");
  for(i=0; i<n; ++i)
    write(x[i] + " ");
  writeln();

  for(i=0; i<n; ++i)
  {
    write("y(" + i + ") = ");
    for(j=0; j<m; ++j)
      write(y[i][j] + " ");
    writeln();
  }

  for(i=0; i<n; ++i)
  {
    write("w(" + i + ") = ");
    for(j=0; j<m; ++j)
      write(w[i][j] + " ");
    writeln();
  }

  for(i=0; i<n; ++i)
  {
    write("cw(" + i + ") = ");
    for(j=0; j<m; ++j)
      write(cw[i][j] + (cc[i][j] > 0 ? "* " : " "));
    writeln();
  }

  for(i=0; i<n; ++i)
  {
    write("cw(" + i + ") = ");
    for(j=0; j<m; ++j)
      write(cc[i][j] + " ");
    writeln();
  }

}
