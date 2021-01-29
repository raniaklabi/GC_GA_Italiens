FOR %%s IN (22 23 24 29 30 31 32 37 38 39 40) DO (
FOR %%t IN (21 26 30) DO (
java -jar MCSC.jar CMLP_%%s.dat %%t
)
)